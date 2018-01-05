package com.fdmgroup.gggo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

@WebServlet(Path.Url.INVITE_ACCEPT)
public class AcceptInviteServlet extends HttpServlet {
	private static final long serialVersionUID = -3969080346703959089L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request, response, new ErrorResponse());
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response, ErrorResponse errorResponse) 
			throws ServletException, IOException {
		
		String inviteId = request.getParameter("inviteId");
		
		if (inviteId == null || inviteId.equals("")) {
			errorResponse.respondWithErrorPage(request, response, "inviteId field must not be empty");
			return;
		}
		
		HttpSession session = request.getSession();
		
		if (session == null) {
			errorResponse.respondWithErrorPage(request, response, "Must have session");
			return;
		}
		
		User invitee = (User) session.getAttribute(Attributes.Session.CURRENT_USER);
		
		if (invitee == null) {
			errorResponse.respondWithErrorPage(request, response, "User must be in session");
			return;
		}
		
		InviteDAO idao = DAOFactory.getInviteDAO();
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		
		Invite inv = idao.getInvite(Integer.parseInt(inviteId));
		
		if (inv == null) {
			errorResponse.respondWithErrorPage(request, response, "No such invite exist");
			return;
		}
		
		/* Create Game and Update Invite */
		Game game = gdao.createGame(inv);
		
		/* Redirect to Game page */	
		response.sendRedirect(Path.Url.ROOT + Path.Url.GAME + "?gameId=" + game.getGameId());
	}
}