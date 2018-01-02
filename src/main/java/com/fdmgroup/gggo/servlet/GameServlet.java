package com.fdmgroup.gggo.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(Path.Url.GAME)
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 6649352721475661962L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String gameIdString = request.getParameter("gameId");
		
		ErrorResponse errResponse = new ErrorResponse(); 
		
		if (gameIdString == null || gameIdString.equals("")) {
			errResponse.respondWithErrorPage(request, response, "Must have game Id");
			return;
		}
		
		HttpSession session = request.getSession();
		
		if (session == null || session.getAttribute(Attributes.Session.CURRENT_USER) == null) {
			errResponse.respondWithErrorPage(request, response, "Must have session");
			return;
		}
		
		int gameId = Integer.parseInt(gameIdString);
		
		RequestDispatcher rd = request.getRequestDispatcher(Path.Page.GAME);
		rd.forward(request, response);
	}
}
