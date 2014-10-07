package com.external.monitoring.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.external.monitoring.Monitoring;
import com.external.monitoring.Response;

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Index() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			final Response monitor = new Monitoring().monitor();
			response.setContentType("text/html");
			response.getWriter().write("<head><title>Application System Status</title></head><body>");
			response.getWriter().write(monitor.success);
			response.getWriter().write("<br>");
			request.getSession(true).setAttribute("RESPONSE", monitor);
			response.getWriter().write("<a href='index.jsp'>View</a></body>");
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
		}
	}

}
