package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.User;

public class AcceptInviteServletTest {
	
	private static InviteDAO idao;
	private static UserDAO udao;
	
	@BeforeClass
	public static void setupOnce() throws DeleteInviteInvitorInviteeMismatchException {
		idao = DAOFactory.getInviteDAO();
		udao = DAOFactory.getUserDAO();
		
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
	}
	
	@AfterClass
	public static void tearDownOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
	}
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
	}
	
	@Test
	public void test_DoPost_UpdatesInviteAndForwardToPlayPage_GivenAcceptance() {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		Invite inv = idao.createInvite(invitor, invitee);
		
		Mockito.when(request.getParameter("inviteId")).thenReturn(Integer.toString(inv.getInviteId()));
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(invitee);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/play.jsp")).thenReturn(rd);
		
		try {
			new AcceptInviteServlet().doPost(request, response);
			PersistentGame pg = idao.getInvite(inv.getInviteId()).getGame();
			assertNotNull(pg);
			Mockito.verify(response, Mockito.times(1)).sendRedirect("/GGGo/game?gameId=" + pg.getGameId());
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}
