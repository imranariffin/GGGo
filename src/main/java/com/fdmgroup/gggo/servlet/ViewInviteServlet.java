package com.fdmgroup.gggo.servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

@WebServlet(Path.Url.INVITES)
public class ViewInviteServlet extends HttpServlet {
	
	public ViewInviteServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (session == null) {
			(new ErrorResponse()).respondWithErrorPage(request, response, "Must have session");
			return;
		}
		
		User invitor = (User) session.getAttribute(Attributes.Session.CURRENT_USER);
		
		if (invitor == null) {
			(new ErrorResponse()).respondWithErrorPage(request, response, "Must have user in session");
			return;
		}
		
		InviteDAO idao = DAOFactory.getInviteDAO();
		GameDAO gdao = DAOFactory.getPersistentGameDAO();

		User invitee = invitor;
		List<Invite> sentInvites = idao.getSentInvites(invitor.getUsername());
		List<Invite> receivedInvites = idao.getReceivedInvites(invitee.getUsername());
		List<Game> activeGames = gdao.getGames(invitor.getUsername());
		
		RequestDispatcher rd = request.getRequestDispatcher(Path.Page.INVITE_VIEW);
		request.setAttribute("sentInvites", sentInvites);
		request.setAttribute("receivedInvites", receivedInvites);
		request.setAttribute("activeGames", activeGames);
		rd.forward(request, response);
	}
}
