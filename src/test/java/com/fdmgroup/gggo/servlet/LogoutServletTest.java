package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.ha.session.SessionMessage;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.model.User;

public class LogoutServletTest {
	@Test
	public void test_DoGet_ForwardALogoutJSPPage() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/logout.jsp")).thenReturn(rd);		
		
		try {
			new LogoutServlet().doGet(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/WEB-INF/views/logout.jsp");
		
		try {
			Mockito.verify(rd, Mockito.times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_DoPost_RemovesCurrentUserAttributeAndInvalidatesSessionGivenExistingUsername_GivenSessionWithCurrentUserAttribute() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		User user = Mockito.mock(User.class);

		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(rd);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(user);
		
		LogoutServlet servlet = new LogoutServlet(); 
		try {
			servlet.doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
		}
		
		Mockito.verify(session, Mockito.times(1)).removeAttribute(Attributes.Session.CURRENT_USER);
		Mockito.verify(session, Mockito.times(1)).invalidate();
		try {
			Mockito.verify(rd, Mockito.times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_DoPost_RespondsWithErrorMessage_GivenSessionDoesNotHaveCurrentUserAttribute() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		PrintWriter out = Mockito.mock(PrintWriter.class);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(null);
		 
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}

		try {
			new LogoutServlet().doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.MUST_LOGIN_TO_LOGOUT);
	}
}
