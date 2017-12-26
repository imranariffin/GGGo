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
import com.fdmgroup.gggo.dao.PersistentGameDAO;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

@WebServlet("/invite/accept")
public class AcceptInviteServlet extends HttpServlet {
	private static final long serialVersionUID = -3969080346703959089L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String inviteId = request.getParameter("inviteId");
		if (inviteId == null || inviteId.equals("")) {
			// respondWithError;
			System.out.println("respond with error!!!");
			return;
		}
		
		InviteDAO idao = DAOFactory.getInviteDAO();
		PersistentGameDAO gdao = DAOFactory.getPersistentGameDAO();
		
		Invite inv = idao.getInvite(Integer.parseInt(inviteId));
		
		HttpSession session = request.getSession();
		User invitee = (User) session.getAttribute(Attributes.CURRENT_USER);
		
//		some db stuffs to update invite
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		rd.forward(request, response);
	}
}
