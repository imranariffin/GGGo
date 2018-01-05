package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.GameDAOTest;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class GameServletTest {
	
	private static UserDAO udao;
	private static InviteDAO idao;
	private static GameDAO gdao;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private RequestDispatcher rd;
	private PrintWriter out;
	
	private User invitor;
	private User invitee;
	private Invite invite;
	private Game game;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
		gdao = DAOFactory.getPersistentGameDAO();
	}
	
	@Before
	public void setup() throws IOException {
		
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		rd = Mockito.mock(RequestDispatcher.class);
		out = Mockito.mock(PrintWriter.class);
		
		String password = "pazzword";
		invitor = udao.createUser("invitor", password);
		invitee = udao.createUser("invitee", password);
		invite = idao.createInvite(invitor, invitee);
		game = gdao.createGame(invite);

		Mockito.when(request.getParameter("gameId")).thenReturn(Integer.toString(game.getGameId()));
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/game.jsp")).thenReturn(rd);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(invitor);
		Mockito.when(response.getWriter()).thenReturn(out);
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		idao.deleteInvite(invitor, invitee, invite);
		udao.deleteUser(invitor.getUsername());
		udao.deleteUser(invitee.getUsername());
	}
	
	@Test
	public void test_DoGet_RespondWithGamePage_GivenGameIdAndCurrentUser() 
			throws ServletException, IOException {
		
		new GameServlet().doGet(request, response);
		
		Mockito.verify(request, Mockito.times(1)).getSession();
		Mockito.verify(session, Mockito.times(1)).getAttribute(Attributes.Session.CURRENT_USER);
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/WEB-INF/views/game.jsp");
		Mockito.verify(rd, Mockito.times(1)).forward(request, response);
	}
}
