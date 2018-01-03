package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.PersistentGameDAO;
import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.serializer.GGJson;

@WebServlet(Path.Url.API)
public class ApiServlet extends HttpServlet {
//	private static final long serialVersionUID = -6048565360004251760L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		setCorsHeaders(response);
		GGJson ggjson = new GGJson();
		doGet(request, response, ggjson, new ErrorResponse());
	}
	
	void doGet(HttpServletRequest request, 
			HttpServletResponse response,
			GGJson ggjson,
			ErrorResponse errorResponse) 
			throws IOException {
		
		String endPoint = request.getRequestURI().replace("/GGGo/api", "");
		Matcher onlineUsersEndPoint = Pattern.compile("/online-users").matcher(endPoint);
		Matcher gameListEndPoint  = Pattern.compile("/game").matcher(endPoint);
		Matcher gameEndPoint  = Pattern.compile("/game/<[0-9]*>/?").matcher(endPoint);
		
		if (onlineUsersEndPoint.matches()) 
		{
			respondWithOnlineUserList(request, response, ggjson);
			
		} else if (gameListEndPoint.matches()) 
		{
			respondWithUserGameList(request, response, ggjson);
			
		} else if (gameEndPoint.matches()) 
		{
			System.out.println("respondWithUserGame");
			
		} else 
		{
			errorResponse.respond404(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	void respondWithOnlineUserList(HttpServletRequest request, HttpServletResponse response, GGJson ggjson) 
			throws IOException {
		
		ServletContext context = request.getSession().getServletContext();
		Map<String, User> userMap = (Map<String, User>) context.getAttribute(Attributes.Context.ONLINE_USERS);
		userMap = (userMap == null) ? new HashMap<>(): userMap;
		
		List<User> users = new ArrayList<>();
		
		HttpSession session = request.getSession();
		if (session != null) {
			User currentUser = (User) session.getAttribute(Attributes.Session.CURRENT_USER);
			for (User user: userMap.values()) {
				if (!user.equals(currentUser)) {
					users.add(user);
				}	
			}
		}

		String json = ggjson.toJsonUserList(users);
		
		setJsonHeaders(response);
		response.getWriter().write(json);
	}
	
	public void respondWithUserGameList(HttpServletRequest request, HttpServletResponse response, GGJson ggjson) 
			throws IOException {

		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute(Attributes.Session.CURRENT_USER);
		
		PersistentGameDAO gdao = DAOFactory.getPersistentGameDAO();
		List<Game> userGameList = gdao.getGames(currentUser.getUsername());
		
		String json = ggjson.toJsonGameList(userGameList);
		
		setJsonHeaders(response);
		response.getWriter().write(json);
	}
	
	private void setCorsHeaders(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Methods", "GET");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");		
	}
	
	private void setJsonHeaders(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
}
