package com.fdmgroup.gggo.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

@WebServlet("/send-invite")
public class SendInviteServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String invitee = request.getParameter("invitee"); 
		if (invitee == null || invitee.equals("")) {
			ErrorResponse.respondWithErrorPage(request, response, "Invitee username required");
			return;
		}
		
		request.setAttribute("invitee", invitee);
		request.getRequestDispatcher("WEB-INF/views/send-invite.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (session == null) {
			ErrorResponse.respondWithErrorPage(request, response, "Must have session");
			return;
		}
		
		User invitor = (User) session.getAttribute(SessionAttributes.CURRENT_USER);
		
		if (invitor == null) {
			ErrorResponse.respondWithErrorPage(request, response, "Session must have user");
			return;
		}
		
		UserDAO udao = DAOFactory.getUserDAO();
		String inviteeUsername = request.getParameter("invitee");
		
		if (inviteeUsername == null || inviteeUsername.equals("")) {
			ErrorResponse.respondWithErrorPage(request, response, "Invite field cannot be empty");
			return;
		}
		
		User invitee = udao.getUser(inviteeUsername);
		
		if (invitee == null) {
			ErrorResponse.respondWithErrorPage(request, response, "Invitee doesn't exist");
			return;
		}
		
		InviteDAO idao = DAOFactory.getInviteDAO();
		Invite inv = idao.createInvite(invitor, invitee);
		
//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/view-invite.jsp");
//		rd.forward(request, response);
		new ViewInviteServlet().doGet(request, response);
	}
}
