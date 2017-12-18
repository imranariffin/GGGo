package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tools.ant.taskdefs.condition.Http;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.dao.DAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.model.User;

public class SignupServletTest {
	@Test
	public void test_DoGet_ForwardsToJSPSignUpPage() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/signup.jsp")).thenReturn(rd);
		
		try {
			new SignupServlet().doGet(request, response);
		} catch (ServletException | IOException e1) {
			fail();
			e1.printStackTrace();
		}
		
		try {
			Mockito.verify(rd, Mockito.times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_DoPost_ForwardsToJSPHomePage_GivenAvailableUserNameAndValidPasswordAndConfirmationPassword() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

		UserDAO udao = DAO.getUserDAO();
		String username = "isumi-san";
		String password = "pazzword";
		User user = new User(username, password);
		
		udao.createUser(user);
		udao.deleteUser(user);
		
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		Mockito.when(request.getParameter("confirmPassword")).thenReturn(password);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/home.jsp")).thenReturn(rd);
		
		
		try {
			new SignupServlet().doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			Mockito.verify(rd, Mockito.times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		User isumi = udao.getUser(username);
		udao.deleteUser(isumi);
	}
	
	@Test
	public void test_DoPost_RespondsWithUsernameNotAvailablePage_GivenExistingUsername() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		PrintWriter out = Mockito.mock(PrintWriter.class);
		
		UserDAO udao = DAO.getUserDAO();
		String username = "isumi-san";
		String password = "pazzword";
		String confirmPassword = password;
		User user = new User(username, password);
		udao.createUser(user);
		
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		Mockito.when(request.getParameter("confirmPassword")).thenReturn(confirmPassword);
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			new SignupServlet().doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.USERNAME_NOT_AVAILABLE);
		
		udao.deleteUser(user);
	}
	
	@Test
	public void test_DoPost_RespondsWithPasswordNotMatchPage_GivenUnmatchingPasswordAndConfirmPassword() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		PrintWriter out = Mockito.mock(PrintWriter.class);
		
		UserDAO udao = DAO.getUserDAO();
		String username = "isumi-san";
		String password = "pazzword";
		String confirmPassword = "DifferentPassword";
		User user = new User(username, password);
		
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		Mockito.when(request.getParameter("confirmPassword")).thenReturn(confirmPassword);
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			new SignupServlet().doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.PASSWORD_NOT_MATCH);
	}
	
	@Test
	public void test_DoPost_RespondsWithUserNameAndPasswordCannotBeEmptyPage_GivenEmptyInput() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		PrintWriter out = Mockito.mock(PrintWriter.class);
		
		UserDAO udao = DAO.getUserDAO();
		String username = "";
		String password = "";
		String confirmPassword = "DifferentPassword";
		User user = new User(username, password);
		
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		Mockito.when(request.getParameter("confirmPassword")).thenReturn(confirmPassword);
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			new SignupServlet().doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.USERNAME_AND_PASSWORD_CANNOT_BE_EMPTY);
	}
	
	@Test
	public void test_DoPost_RespondsWithInvalidParameterPage_GivenNullInput() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		PrintWriter out = Mockito.mock(PrintWriter.class);
		
		UserDAO udao = DAO.getUserDAO();
		String username = null;
		String password = null;
		String confirmPassword = "DifferentPassword";
		User user = new User(username, password);
		
		Mockito.when(request.getParameter("username")).thenReturn(username);
		Mockito.when(request.getParameter("password")).thenReturn(password);
		Mockito.when(request.getParameter("confirmPassword")).thenReturn(confirmPassword);
		try {
			Mockito.when(response.getWriter()).thenReturn(out);
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
		try {
			new SignupServlet().doPost(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
		
		Mockito.verify(out, Mockito.times(1)).println(ServletErrorResponsePages.INVALID_PARAMETERS);
	}
}
