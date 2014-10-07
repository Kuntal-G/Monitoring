package com.external.monitoring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Response {
	public final String header;
	public final String snapTime;
	public String success;
	public List<Result> results;

	public Response() {
		header = "External Application Monitoring";
		snapTime = new SimpleDateFormat("EEE, d MMM yyyy '-' HH:mm:ss z")
				.format(new Date(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis()));
	}
}
