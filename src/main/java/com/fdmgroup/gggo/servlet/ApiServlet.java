package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.serializer.GGJson;
import com.google.gson.Gson;

@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
//	private static final long serialVersionUID = -6048565360004251760L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response, new ErrorResponse());
	}
	
	void doGet(HttpServletRequest request, HttpServletResponse response, ErrorResponse errorResponse) 
			throws IOException {
		
		String url = request.getRequestURI().replace("/GGGo/api", "");
		
		switch (url) {
			case "/online-users": 
				GGJson ggjson = new GGJson();
				respondWithOnelineUserList(request, response, ggjson);
				break;
			default:
				errorResponse.respond404(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	void respondWithOnelineUserList(HttpServletRequest request, HttpServletResponse response, GGJson ggjson) 
			throws IOException {
		
		ServletContext context = request.getSession().getServletContext();
		Map<String, User> users = (Map<String, User>) context.getAttribute(Attributes.Context.ONLINE_USERS);
		users = (users == null) ? new HashMap<>(): users;
		
		HttpSession session = request.getSession();
		if (session != null) {
			User currentUser = (User) session.getAttribute(Attributes.Session.CURRENT_USER);
			if (currentUser != null && users.containsKey(currentUser.getUsername())) {
				users.remove(currentUser.getUsername());
			}
		}

		String json = ggjson.toJson(users);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}
}
