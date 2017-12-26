package com.fdmgroup.gggo.servlet;

import java.awt.print.Printable;
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
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.User;

public class SendInviteServletTest {

	private static UserDAO udao;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private PrintWriter out;
	private ErrorResponse errResponse;
	private User invitor;
	private User invitee;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
	}
	
	@Before
	public void setup() {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		out = Mockito.mock(PrintWriter.class);
		errResponse = Mockito.spy(new ErrorResponse());
		
		String password = "pazzword";
		invitor = udao.createUser("invitor", password);
		invitee = udao.createUser("invitee", password);
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorMessage_GivenRequestWithNullInvitor() 
			throws IOException, ServletException {

		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(SessionAttributes.CURRENT_USER)).thenReturn(null);
		Mockito.when(response.getWriter()).thenReturn(out);

		new SendInviteServlet().doPost(request, response, errResponse);
		
		String errorMsg = "Session must have user";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errorMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorMessage_GivenRequestWithNullInvitee() 
			throws IOException, ServletException {
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(SessionAttributes.CURRENT_USER)).thenReturn(invitor);
		Mockito.when(request.getParameter("invitee")).thenReturn(null);
		Mockito.when(response.getWriter()).thenReturn(out);

		new SendInviteServlet().doPost(request, response, errResponse);
		
		String errorMsg = "Invitee field cannot be empty";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errorMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorMessage_GivenRequestWithEmptyStringInvitee() 
			throws IOException, ServletException {
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(SessionAttributes.CURRENT_USER)).thenReturn(invitor);
		Mockito.when(request.getParameter("invitee")).thenReturn("");
		Mockito.when(response.getWriter()).thenReturn(out);

		new SendInviteServlet().doPost(request, response, errResponse);
		
		String errorMsg = "Invitee field cannot be empty";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errorMsg + "</p>");
	}
	
	@Test
	public void test_DoPost_RespondWithErrorMessage_GivenRequestWithNonExistingInvitee() 
			throws IOException, ServletException, DeleteInviteInvitorInviteeMismatchException {
		
		udao.deleteUser("nonexistinguser");
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(SessionAttributes.CURRENT_USER)).thenReturn(invitor);
		Mockito.when(request.getParameter("invitee")).thenReturn("nonexistinguser");
		Mockito.when(response.getWriter()).thenReturn(out);

		Mockito.spy(new SendInviteServlet()).doPost(request, response, errResponse);
		
		String errorMsg = "Invitee doesn't exist";
		Mockito.verify(out, Mockito.times(1)).println("<p style='color: red'>" + errorMsg + "</p>");
	}
}
