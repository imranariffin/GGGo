package com.fdmgroup.gggo.servlet;

import java.io.IOException;

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
import com.fdmgroup.gggo.model.User;

@WebServlet(Path.Url.INVITE_CANCEL)
public class CancelInviteServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		HttpSession session = request.getSession();
		User currentUser = null; 
		
		if (session == null || ((currentUser = (User) session.getAttribute(Attributes.Session.CURRENT_USER)) == null)) {
			errorResponse.respondWithErrorPage(request, response, "Must have session");
			return;
		}
		
		String inviteIdString = request.getParameter("inviteId");

		if (inviteIdString == null || inviteIdString.equals("")) {
			errorResponse.respondWithErrorPage(request, response, "Must have inviteId");
			return;
		}
		
		int inviteId = Integer.parseInt(inviteIdString);
		InviteDAO idao = DAOFactory.getInviteDAO();
		Invite inv = idao.getInvite(inviteId);
		
		if (inv == null) {
			errorResponse.respondWithErrorPage(request, response, "Invite does not exist");
			return;
		}
		
		if (inv.getInvitee().getUserId() == currentUser.getUserId()) {
			errorResponse.respondWithErrorPage(request, response, "Only invitor can cancel");
			return;
		}
		
		try {
			idao.deleteInvite(inv.getInvitor(), inv.getInvitee(), inv);
		} catch (DeleteInviteInvitorInviteeMismatchException e) {
			errorResponse.respondWithErrorPage(request, response, "Something wrong occurred while cancel invite");
			e.printStackTrace();
			return;
		}
		
		response.sendRedirect(Path.Url.ROOT + Path.Url.INVITES);
	}
}
