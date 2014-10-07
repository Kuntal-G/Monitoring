package com.external.monitoring;

import java.util.Arrays;
import java.util.StringTokenizer;

public class Result {
	private final boolean success;
	private final String result;
	private final Exception exception;
	private final String name;
	private final String description;

	public Result(String name, String description, String result) {
		this.success = true;
		this.name = name;
		this.description = description;
		this.result = result;
		this.exception = null;

	}

	public Result(String name, String description, Exception exception) {
		this.success = false;
		this.result = null;
		this.name = name;
		this.description = description;
		this.exception = exception;
	}

	public boolean isSuccess() {
		return success;
	}

	public Exception getException() {
		return exception;
	}

	public String getResult() {
		return result;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getError() {
		String excep_string = null;
		if (exception != null) {
			String exception_data = new String();
			exception_data = Arrays.asList(exception.getStackTrace())
					.toString();
			StringBuffer sb = new StringBuffer();
			StringTokenizer st = new StringTokenizer(exception_data, ",");
			while (st.hasMoreTokens()) {
				sb.append(st.nextToken());
				sb.append("\n");
			}
			excep_string = sb.toString();
		}
		return exception != null ? excep_string : null;

	}

}
