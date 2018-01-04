package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class ViewInviteServletTest {
	
	private static UserDAO udao;
	private static InviteDAO idao;
	private static GameDAO gdao;
	
	@BeforeClass
	public static void setupOnce() {
		 udao = DAOFactory.getUserDAO();
		 idao = DAOFactory.getInviteDAO();
		 gdao = DAOFactory.getPersistentGameDAO();
	}
	
	private User invitor;
	private User invitee;
	private List<Invite> sentInvites;
	private List<Invite> receivedInvites;
	private List<Game> activeGames;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private RequestDispatcher rd;
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException {

		String invitorUsername = "invitor";
		String inviteeUsername = "invitee";
		String password = "pazzword";
		invitor = udao.createUser(invitorUsername, password);
		invitee = udao.createUser(inviteeUsername, password);
		sentInvites = idao.getSentInvites(invitor.getUsername());
		receivedInvites = idao.getReceivedInvites(invitee.getUsername());
		activeGames = gdao.getGames(invitor.getUsername());
		
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		rd = Mockito.mock(RequestDispatcher.class);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(invitor);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/view-invite.jsp")).thenReturn(rd);
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser(invitor.getUsername());
		udao.deleteUser(invitee.getUsername());
	}
	
	@Test
	public void test_ViewInvites_RespondsWithPageWithListOfSentAndReceivedInvitesAndActiveGames_GivenUser() 
			throws ServletException, IOException, DeleteInviteInvitorInviteeMismatchException {
		
		try {
			new ViewInviteServlet().doGet(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			fail();
		}
		
		Mockito.verify(session, Mockito.times(1)).getAttribute(Attributes.Session.CURRENT_USER);
		Mockito.verify(request, Mockito.times(1)).setAttribute("sentInvites", sentInvites);
		Mockito.verify(request, Mockito.times(1)).setAttribute("receivedInvites", receivedInvites);
		Mockito.verify(request, Mockito.times(1)).setAttribute("activeGames", activeGames);
		Mockito.verify(rd, Mockito.times(1)).forward(request, response);
	}
}
