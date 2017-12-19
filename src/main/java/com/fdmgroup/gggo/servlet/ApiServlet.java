package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.User;
import com.google.gson.Gson;

@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
//	private static final long serialVersionUID = -6048565360004251760L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String url = request.getRequestURI().replace("/GGGo/api", "");
		System.out.println(url);
				
		switch (url) {
			case "/online-users": 
				respondWithOnelineUserList(request, response);
				break;
			default: ErrorResponse.respond404(request, response);
		}
	}
	
	void respondWithOnelineUserList(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		int n = 10;
		
		UserDAO udao = DAOFactory.getUserDAO();
		List<User> users = udao.getUsers();
		
		String json = new Gson().toJson(users);
		
		System.out.println(json);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}
