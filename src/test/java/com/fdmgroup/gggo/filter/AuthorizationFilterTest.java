package com.fdmgroup.gggo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.servlet.Attributes;
import com.fdmgroup.gggo.servlet.ErrorResponse;
import com.fdmgroup.gggo.servlet.Path;

public class AuthorizationFilterTest {
	private AuthorizationFilter authFilter;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private FilterConfig fConfig;
	private FilterChain fc;
	private RequestDispatcher rd;
	private User user;
	
	@Before
	public void setup() throws ServletException {
		
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		fConfig = Mockito.mock(FilterConfig.class);
		fc = Mockito.mock(FilterChain.class);
		rd = Mockito.mock(RequestDispatcher.class);
		user = Mockito.mock(User.class);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(user);
		Mockito.when(request.getRequestDispatcher(Path.Page.LOGIN)).thenReturn(rd);
		
		authFilter = new AuthorizationFilter();
		authFilter.init(fConfig);
	}
	
	@Test
	public void test_DoFilter_RedirectsToLoginPage_GivenSessionNull() 
			throws IOException, ServletException {
		
		Mockito.when(request.getSession()).thenReturn(null);
		
		authFilter.doFilter(request, response, fc);
		
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher(Path.Page.LOGIN);
		Mockito.verify(rd, Mockito.times(1)).forward(request, response);
	}
	
	@Test
	public void test_DoFilter_RedirectsToLoginPage_GivenCurrentUserNull() 
			throws IOException, ServletException {
		
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(null);
		
		authFilter.doFilter(request, response, fc);
		
		Mockito.verify(request, Mockito.times(1)).getRequestDispatcher(Path.Page.LOGIN);
		Mockito.verify(rd, Mockito.times(1)).forward(request, response);
	}
	
	@Test
	public void test_DoFilter_DoesNotRedirectToLoginPage_GivenNullSessionAndPathExcluded() 
			throws IOException, ServletException {
		
		Mockito.when(request.getServletPath()).thenReturn("/home");
		Mockito.when(request.getSession()).thenReturn(null);
		
		authFilter.doFilter(request, response, fc);
		
		Mockito.verify(request, Mockito.times(0)).getRequestDispatcher(Path.Page.LOGIN);
		Mockito.verify(rd, Mockito.times(0)).forward(request, response);
	}
	
	@Test
	public void test_DoFilter_DoesNotRedirectToLoginPage_GivenSessionAndCurrentUserAndPathNotExcluded() 
			throws IOException, ServletException {
		
		Mockito.when(request.getServletPath()).thenReturn("/invite");
		
		authFilter.doFilter(request, response, fc);
		
		Mockito.verify(request, Mockito.times(0)).getRequestDispatcher(Path.Page.LOGIN);
		Mockito.verify(rd, Mockito.times(0)).forward(request, response);
	}
	
	@Test
	public void test_DoFilter_SendsErrorMessageToLoginPage_GivenSessionOrCurrentUserNull() 
			throws IOException, ServletException {
		
		Mockito.when(request.getSession()).thenReturn(null);
		
		authFilter.doFilter(request, response, fc);
		
		Mockito.verify(request, Mockito.times(1)).setAttribute(
				Attributes.Request.PLEASE_LOGIN, 
				ErrorResponse.PLEASE_LOGIN);
	}
}
