package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
import com.lambdaworks.crypto.SCryptUtil;

public class LoginServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDispatcher rd;
	private HttpSession session;
	private PrintWriter out; 
	private User fukui;
	private String password;
	
	private static UserDAO udao;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
	}
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		rd = Mockito.mock(RequestDispatcher.class);
		session = Mockito.mock(HttpSession.class);
		out = Mockito.mock(PrintWriter.class);
		
		String username = "fukui";
		password = "pazzword";
		
		udao.deleteUser(username);
		fukui = udao.createUser(username, SCryptUtil.scrypt(password, 2 << 13, 3, 7));
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("fukui");
	}
	
	@Test
	public void test_DoGet_RespondsWithALoginJSPPage_GivenExistingUsernameAndCorrectPassword() {
		
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(rd);
		
		try {
			new LoginServlet().doGet(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/WEB-INF/views/login.jsp");
		try {
			Mockito.verify(rd, Mockito.times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_DoPost_ForwardToHomePage_GivenExistingUsernameAndCorrectPassword() {
		Mockito.when(request.getParameter("username")).thenReturn(fukui.getUsername());
		Mockito.when(request.getParameter("password")).thenReturn(password);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/home.jsp")).thenReturn(rd);
		
		try {
			new LoginServlet().doPost(request, response);
		} catch (ServletException | IOException e1) {
			fail();
			e1.printStackTrace();
		}
		
		Mockito.verify(session, Mockito.times(1)).setAttribute(SessionAttributes.CURRENT_USER, fukui);
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/WEB-INF/views/home.jsp");
		try {
			Mockito.verify(rd, Mockito.times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_DoPost_RespondWithUsernotNotExistErrorMessage_GivenNonExistingUsername() {

		Mockito.when(request.getParameter("username")).thenReturn("nonexistingusername");
		Mockito.when(request.getParameter("password")).thenReturn(password);
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e1) {
			fail();
			e1.printStackTrace();
		}
		
		LoginServlet servlet = new LoginServlet(); 
		try {
			servlet.doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			Mockito.verify(response, Mockito.times(1)).getWriter();
			Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.USERNAME_DOES_NOT_EXIST);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_DoPost_RespondsWithWrongPasswordErrorMessage_GivenExsitingUsernameAndWrongPassword() {

		String wrongPassword = "wrong!";
		
		Mockito.when(request.getParameter("username")).thenReturn(fukui.getUsername());
		Mockito.when(request.getParameter("password")).thenReturn(wrongPassword);
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e1) {
			fail();
			e1.printStackTrace();
		}
		
		LoginServlet servlet = new LoginServlet(); 
		try {
			servlet.doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			Mockito.verify(response, Mockito.times(1)).getWriter();
			Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.PASSWORD_WRONG);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_DoPost_RespondsWithUsernameAndPasswordCannotBeEmptyErrorMessage_GivenEmptyStringUsernameAndPassword() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		String emptyUsername = "";
		String emptyPassword = "";
		
		Mockito.when(request.getParameter("username")).thenReturn(emptyUsername);
		Mockito.when(request.getParameter("password")).thenReturn(emptyPassword);
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e1) {
			fail();
			e1.printStackTrace();
		}
		
		LoginServlet servlet = new LoginServlet(); 
		try {
			servlet.doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			Mockito.verify(response, Mockito.times(1)).getWriter();
			Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.USERNAME_AND_PASSWORD_CANNOT_BE_EMPTY);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
}
