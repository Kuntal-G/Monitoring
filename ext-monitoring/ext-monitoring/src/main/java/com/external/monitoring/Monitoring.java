package com.external.monitoring;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.external.monitoring.plugin.BaseMonitorPlugin;
import com.external.monitoring.plugin.XArgument;
import com.external.monitoring.plugin.XMonitoringPlugin;
import com.external.monitoring.plugin.XPlugin;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

public final class Monitoring {
	private static final int THREAD_TIMEOUT = 5;
	private static final int MAX_SIZE = 10;
	private static final int CORE_SIZE = 5;

	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, THREAD_TIMEOUT, TimeUnit.MINUTES,new PriorityBlockingQueue<Runnable>());
	final List<XMonitoringPlugin> plugins = readPlugins();

	public List<Result> monitorPlugins() throws Exception {
		final List<Result> results = new ArrayList<Result>();
		final List<Future<Result>> futures = new ArrayList<Future<Result>>();

		try {
			final CountDownLatch latch = new CountDownLatch(plugins.size());
			for (XMonitoringPlugin plugin : plugins) {
				final Future<Result> submit = executor.submit(convertToTask(latch, plugin));
				futures.add(submit);
			}
			latch.await(THREAD_TIMEOUT, TimeUnit.MINUTES);
			for (Future<Result> future : futures) {
				if (future.isDone()) {
					results.add(future.get());
				} else {
					results.add(new Result("Fail", "Fail", new Exception("Timeout...")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to monitor application: ", e);
		}
		return results;
	}

	public Response monitor() throws Exception {
		final Response response = new Response();
		response.results = monitorPlugins();
		response.success = Boolean.TRUE.toString();
		for (Result result : response.results) {
			if (!result.isSuccess()) {
				response.success = Boolean.FALSE.toString();
				break;
			}
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	private Callable<Result> convertToTask(CountDownLatch latch, XMonitoringPlugin plugin) throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		final Class<BaseMonitorPlugin> clazz = (Class<BaseMonitorPlugin>) Class.forName(plugin.clazz);
		final Constructor<BaseMonitorPlugin> constructor = clazz.getConstructor(new Class<?>[] { String.class, String.class, CountDownLatch.class,
				String[].class });
		final List<XArgument> args = plugin.args;
		if (args == null) {
			return constructor.newInstance(plugin.name, plugin.description, latch, null);
		} else {
			List<String> list = new ArrayList<String>();
			Collections.sort(args, new Comparator<XArgument>() {
				public int compare(XArgument o1, XArgument o2) {
					return (o1.index - o2.index) > 0 ? 1 : -1;
				}
			});
			for (XArgument argument : args) {
				list.add(argument.value);
			}
			return constructor.newInstance(plugin.name, plugin.description, latch, list.toArray(new String[list.size()]));
		}
	}

	private static List<XMonitoringPlugin> readPlugins() {
		final XStream xStream = new XStream();
		xStream.allowTypesByWildcard(new String[] { 
			"com.external.monitoring.plugin.**",
			"com.external.monitoring.plugin.impl.**"
			});
		xStream.processAnnotations(XPlugin.class);
		final XPlugin fromXML = (XPlugin) xStream.fromXML(Monitoring.class.getResourceAsStream("/plugin-configuration.xml"));
		return fromXML.plugins;
	}

}
