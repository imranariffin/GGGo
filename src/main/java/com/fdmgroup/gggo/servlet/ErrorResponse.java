package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorResponse {
	public static final String PLEASE_LOGIN = "Please login";

	public void respondWithErrorPage(HttpServletRequest request, HttpServletResponse response, String errMsg) 
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color: red'>" + errMsg + "</p>");
	}
	
	public void respond404(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		respondWithErrorPage(request, response, "Page not found");
	}
}
