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
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;

@WebServlet("/invite/reject")
public class RejectInviteServlet extends HttpServlet {
	private static final long serialVersionUID = 7850139517917583754L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String inviteIdString = (String) request.getParameter("inviteId");
		
		ErrorResponse errResponse = new ErrorResponse();
		if (session == null || session.getAttribute(Attributes.Session.CURRENT_USER) == null) {
			errResponse.respondWithErrorPage(request, response, "Must have session");
			return;
		}
		if (inviteIdString == null || inviteIdString.equals("")) {
			errResponse.respondWithErrorPage(request, response, "Must have inviteId");
			return;
		}
		
		int inviteId = Integer.parseInt(inviteIdString);
		InviteDAO idao = DAOFactory.getInviteDAO();
		Invite invite = idao.getInvite(inviteId);
		
		if (invite == null) {
			errResponse.respondWithErrorPage(request, response, "Invite does not exist");
			return;
		}
		
		try {
			idao.deleteInvite(invite.getInvitor(), invite.getInvitee(), invite);
		} catch (DeleteInviteInvitorInviteeMismatchException e) {
			errResponse.respondWithErrorPage(request, response, "Error occurred when trying to delete invite");
			e.printStackTrace();
			return;
		}
		
		new ViewInviteServlet().doGet(request, response);
	}
}
