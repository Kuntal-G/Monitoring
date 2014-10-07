package com.external.monitoring.plugin.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.external.monitoring.plugin.BaseMonitorPlugin;

public class HttpFormMonitorPlugin extends BaseMonitorPlugin {
	public HttpFormMonitorPlugin(String name, String description, CountDownLatch latch, String[] parameters) {
		super(name, description, latch, parameters);
	}

	@Override
	protected String execute(String[] parameters) throws Exception {
		final String url = parameters[0];
		final Map<String, String> map = new HashMap<String, String>();
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet request = new HttpGet(url);
		final HttpResponse response;
		try {
		response = client.execute(request);
		BufferedReader rd = new BufferedReader(	new InputStreamReader(response.getEntity().getContent()));
	 
		final StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		map.put("Request", url);
		map.put("Status",String.valueOf(response.getStatusLine().getStatusCode()));
		map.put("Response", result.toString());
		return asHtmlTable(map);
		} catch (Exception e) {
			throw new Exception(asHtmlTable(map));
		}
		
}

}