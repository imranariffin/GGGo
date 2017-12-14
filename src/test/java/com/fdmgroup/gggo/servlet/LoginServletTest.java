package com.fdmgroup.gggo.servlet;

import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.dao.DAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.model.User;
import com.lambdaworks.crypto.SCryptUtil;

public class LoginServletTest {

	@Test
	public void test_DoGet_RespondsWithALoginJSPPage_GivenExistingUsernameAndCorrectPassword() {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		
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
		UserDAO udao = DAO.getUserDAO();
		String username = "fukui";
		String password = "pazzword";
		User user = new User(username, SCryptUtil.scrypt(password, 2 << 13, 3, 7));
		udao.deleteUser(user);
		udao.postUser(user);
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
		
		Mockito.when(request.getParameter("username")).thenReturn(user.getUsername());
		Mockito.when(request.getParameter("password")).thenReturn(password);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(SessionAttributes.CURRENT_USER)).thenReturn(user);
		Mockito.when(request.getRequestDispatcher("/WEB-INF/views/home.jsp")).thenReturn(rd);
		
		try {
			new LoginServlet().doPost(request, response);
		} catch (ServletException | IOException e1) {
			fail();
			e1.printStackTrace();
		}
		
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/WEB-INF/views/home.jsp");
		try {
			Mockito.verify(rd, Mockito.times(1)).forward(request, response);
		} catch (ServletException | IOException e) {
			fail();
			e.printStackTrace();
		}
	}
}
