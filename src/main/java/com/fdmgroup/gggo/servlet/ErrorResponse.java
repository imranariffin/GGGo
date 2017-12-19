package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorResponse {
	public static void respondWithErrorPage(HttpServletRequest request, HttpServletResponse response, String errMsg) 
			throws IOException {
		
		ErrorResponse self = new ErrorResponse();
		self._respondWithErrorPage(request, response, errMsg);
	}
	
	public static void respond404(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		ErrorResponse self = new ErrorResponse();
		self._respondWithErrorPage(request, response, "Page not found");
	}
	
	private void _respondWithErrorPage(HttpServletRequest request, HttpServletResponse response, String errMsg) 
			throws IOException {
		
		PrintWriter out = response.getWriter();
		out.println("<p style='color: red'>" + errMsg + "</p>");
	}
}
