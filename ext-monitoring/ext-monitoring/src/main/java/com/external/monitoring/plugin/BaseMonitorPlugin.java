package com.external.monitoring.plugin;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.external.monitoring.Result;

public abstract class BaseMonitorPlugin implements Callable<Result> {
	private final CountDownLatch latch;
	private final String[] parameters;
	private final String name;
	private final String description;

	public BaseMonitorPlugin(String name, String description, CountDownLatch latch, String... parameters) {
		this.name = name;
		this.description = description;
		this.latch = latch;
		this.parameters = parameters;

	}

	public final Result call() throws Exception {
		Result result = null;
		try {
			result = new Result(name, description, execute(parameters));
		} catch (Exception exception) {
			result = new Result(name, description, exception);
		} finally {
			latch.countDown();
		}
		return result;
	}

	public final String asHtmlTable(Map<String, String> map) {
		final StringBuilder build = new StringBuilder();
		build.append("<table>");
		for (Entry<String, String> entry : map.entrySet()) {
			build.append("<tr><td style='width: 200px'>");
			build.append(entry.getKey());
			build.append("</td><td><b>");
			build.append(entry.getValue());
			build.append("</b></td></tr>");
		}
		build.append("</table>");
		return build.toString();
	}

	protected abstract String execute(String[] parameters) throws Exception;

}
