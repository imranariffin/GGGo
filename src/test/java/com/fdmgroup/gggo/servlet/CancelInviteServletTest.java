package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class CancelInviteServletTest {
	
	private static InviteDAO idao;
	private static UserDAO udao;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private PrintWriter out;
	
	private User invitor;
	private User invitee;
	private Invite invite;
	
	@BeforeClass
	public static void setupOnce() {
		idao = DAOFactory.getInviteDAO();
		udao = DAOFactory.getUserDAO();
	}
	
	@Before
	public void setup() throws IOException {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		out = Mockito.mock(PrintWriter.class);
		
		String password = "pazzword";
		invitor = udao.createUser("invitor", password);
		invitee = udao.createUser("invitee", password);
		invite = idao.createInvite(invitor, invitee);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("inviteId")).thenReturn(Integer.toString(invite.getInviteId()));
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
	public void test_DoPost_RespondWithErrorPage_GivenNoInviteId() 
			throws ServletException, IOException {
		
		Mockito.when(request.getParameter("inviteId")).thenReturn(null);
		
		new CancelInviteServlet().doPost(request, response);
		
		Mockito.verify(request, Mockito.times(1)).getParameter("inviteId");
		Mockito.verify(response, Mockito.times(1)).getWriter();
		String errMsg = "Must have inviteId";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorPage_GivenNoCurrentUser() 
			throws ServletException, IOException {
		
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(null);
		
		new CancelInviteServlet().doPost(request, response);
		
		Mockito.verify(response, Mockito.times(1)).getWriter();
		String errMsg = "Must have session";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorPage_GivenCurrentUserIsInvitee() 
			throws ServletException, IOException {
		
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(invitee);
		
		new CancelInviteServlet().doPost(request, response);
		
		Mockito.verify(response, Mockito.times(1)).getWriter();
		String errMsg = "Only invitor can cancel";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorPage_GivenNonExistingInviteId() 
			throws DeleteInviteInvitorInviteeMismatchException, ServletException, IOException {

		idao.deleteInvite(invitor, invitee, invite);
		
		new CancelInviteServlet().doPost(request, response);
		
		Mockito.verify(response, Mockito.times(1)).getWriter();
		String errMsg = "Invite does not exist";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errMsg + "</p>");
		
		invite = idao.createInvite(invitor, invitee);
	}
	
	@Test
	public void test_DoPost_RemovesInviteInDb_GivenInvIteIdAndCurrentUser() 
			throws ServletException, IOException {
		
		new CancelInviteServlet().doPost(request, response);
		
		Mockito.verify(response, Mockito.times(1)).sendRedirect(Path.Url.ROOT + Path.Url.INVITES);
	}
	
	@Test
	public void test_DoPost_RespondWithViewInvitePage_GivenInviteIdAndCurrentUser() 
			throws ServletException, IOException {
		
		new CancelInviteServlet().doPost(request, response);
		
		assertNull(idao.getInvite(invite.getInviteId()));
	}	
	
	@Test
	public void test_DoPost_DoesNotRemoveInviteeFromDb_GivenInviteIdAndCurrentUser() 
			throws ServletException, IOException {
		
		new CancelInviteServlet().doPost(request, response);
		
		assertNotNull(udao.getUser(invitee.getUsername()));
	}
}
