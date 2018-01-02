package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class RejectInviteServletTest {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private RequestDispatcher rd;
	PrintWriter out;
	
	private User currentUser;
	private User invitor;
	private Invite invite;
	
	private static UserDAO udao;
	private static InviteDAO idao;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
	}
	
	@Before
	public void setup() throws IOException {
		
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		rd = Mockito.mock(RequestDispatcher.class);
		out = Mockito.mock(PrintWriter.class);

		String password = "pazzword";
		currentUser = udao.createUser("current-user", password);
		invitor = udao.createUser("invitor", password);
		invite = idao.createInvite(invitor, currentUser);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(currentUser);
		Mockito.when(request.getParameter("inviteId")).thenReturn(Integer.toString(invite.getInviteId()));
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/view-invite.jsp")).thenReturn(rd);
		Mockito.when(response.getWriter()).thenReturn(out);
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		idao.deleteInvite(invitor, currentUser, invite);
		udao.deleteUser(currentUser.getUsername());
	}
	
	@Test
	public void test_DoPost_RespondWithViewInvitePage_GivenInviteIdAndCurrentUser() 
			throws ServletException, IOException {

		new RejectInviteServlet().doPost(request, response);
		
		Mockito.verify(request, Mockito.times(1)).getParameter("inviteId");
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/WEB-INF/views/view-invite.jsp");
		Mockito.verify(rd, Mockito.times(1)).forward(request, response);
	}
	
	@Test
	public void test_DoPost_RespondWithErrorPage_GivenNoInviteId() 
			throws IOException, ServletException {
		
		Mockito.when(request.getParameter("inviteId")).thenReturn(null);
		
		new RejectInviteServlet().doPost(request, response);
		
		Mockito.verify(response, Mockito.times(1)).getWriter();
		String errMsg = "Must have inviteId";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorPage_GivenNoCurrentUser() 
			throws ServletException, IOException {
		
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(null);
		
		new RejectInviteServlet().doPost(request, response);
		
		Mockito.verify(response, Mockito.times(1)).getWriter();
		String errMsg = "Must have session";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RemovesInviteInDb_GivenInviteId() 
			throws ServletException, IOException {
		
		assertNotNull(idao.getInvite(invite.getInviteId()));
		
		new RejectInviteServlet().doPost(request, response);
		
		assertNull(idao.getInvite(invite.getInviteId()));
	}
}
