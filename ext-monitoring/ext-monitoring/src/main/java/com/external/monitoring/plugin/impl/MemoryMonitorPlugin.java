package com.external.monitoring.plugin.impl;

import java.net.Inet4Address;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.external.monitoring.plugin.BaseMonitorPlugin;

public class MemoryMonitorPlugin extends BaseMonitorPlugin {

	private static final int MB = 1024 * 1024;

	public MemoryMonitorPlugin(String name, String description, CountDownLatch latch, String[] parameters) {
		super(name, description, latch, parameters);
	}

	@Override
	protected String execute(String[] parameters) throws Exception {
		final Runtime runtime = Runtime.getRuntime();
		final Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Host Name", Inet4Address.getLocalHost().getCanonicalHostName());
		map.put("IP Address", Inet4Address.getLocalHost().getHostAddress());
		map.put("Used Memory", round((runtime.totalMemory() - runtime.freeMemory()) / MB, 4));
		map.put("Free Memory", round(runtime.freeMemory() / MB, 4));
		map.put("Total Memory", round(runtime.totalMemory() / MB, 4));
		map.put("Max  Memory", round(runtime.maxMemory() / MB, 4));
		return asHtmlTable(map);
	}

	private String round(final long l, int i) {
		String s = String.valueOf(l);
		for (int index = s.length(); index < i; index++) {
			s = "0" + s;
		}
		return s + " MB.";
	}
}
