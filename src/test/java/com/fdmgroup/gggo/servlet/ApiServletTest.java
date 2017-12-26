package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.w3c.dom.Attr;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.serializer.GGJson;

public class ApiServletTest {

	private static UserDAO udao;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ErrorResponse errResponse;
	private HttpSession session;
	private ServletContext context;
	private PrintWriter out;
	
	private User currentUser;
	private User user2;
	private User user3;
	private User user4;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
	}
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		errResponse = Mockito.mock(ErrorResponse.class);
		session = Mockito.mock(HttpSession.class);
		context = Mockito.mock(ServletContext.class);
		out = Mockito.mock(PrintWriter.class);
		
		String password = "pazzword";
		udao.deleteUser("currentUser");
		udao.deleteUser("user2");
		udao.deleteUser("user3");
		udao.deleteUser("user4");
		currentUser = udao.createUser("currentUser", password);
		user2 = udao.createUser("user2", password);
		user3= udao.createUser("user3", password);
		user4 = udao.createUser("user4", password);
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser(currentUser.getUsername());
		udao.deleteUser(user2.getUsername());
		udao.deleteUser(user3.getUsername());
		udao.deleteUser(user4.getUsername());
	}
	
	@Test
	public void test_DoGet_RespondsWithEmptyJsonListOfOnlineUser_GivenRequest_WhenNoUsersLoggedIn() 
			throws IOException {
		
		String url = new String("/GGGo/api/online-users");
		Map<String, User> onlineUsers = new HashMap<>();
		
		Mockito.when(request.getRequestURI()).thenReturn(url);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getServletContext()).thenReturn(context);
		Mockito.when(context.getAttribute(Attributes.Context.ONLINE_USERS)).thenReturn(onlineUsers);
		Mockito.when(response.getWriter()).thenReturn(out);
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		GGJson ggjson = Mockito.spy(new GGJson());
		servlet.doGet(request, response, errResponse);

		Mockito.verify(out, Mockito.times(1)).write("[]");
	}
	
	@Test
	public void test_DoGet_RespondsWithAnEmptyJsonListOf_GivenRequestFromAUser_WhenOnlyTheUserLoggedIn() 
			throws IOException {

		String url = new String("/GGGo/api/online-users");
		Map<String, User> onlineUsers = new HashMap<>();
		onlineUsers.put("currentUser", currentUser);

		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(currentUser);
		Mockito.when(request.getRequestURI()).thenReturn(url);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getServletContext()).thenReturn(context);
		Mockito.when(context.getAttribute(Attributes.Context.ONLINE_USERS)).thenReturn(onlineUsers);
		Mockito.when(response.getWriter()).thenReturn(out);
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		servlet.doGet(request, response, errResponse);

		Mockito.verify(out, Mockito.times(1)).write("[]");
	}
	
	@Test
	public void test_DoGet_RespondsWithJsonListOnlineUsersExceptCurrentUser_WhenOnlyCurrentUserLoggedIn() 
			throws IOException {

		String url = new String("/GGGo/api/online-users");
		Map<String, User> onlineUsers = new HashMap<>();
		
		onlineUsers.put("currentUser", currentUser);
		onlineUsers.put("user2", user2);
		onlineUsers.put("user3", user3);
		onlineUsers.put("user4", user4);
		
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(currentUser);
		Mockito.when(request.getRequestURI()).thenReturn(url);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getServletContext()).thenReturn(context);
		Mockito.when(context.getAttribute(Attributes.Context.ONLINE_USERS)).thenReturn(onlineUsers);
		Mockito.when(response.getWriter()).thenReturn(out);
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		
		servlet.doGet(request, response, errResponse);

		onlineUsers.remove(currentUser.getUsername());
		String expected = new GGJson().toJson(onlineUsers);
		Mockito.verify(out, Mockito.times(1)).write(expected);
	}
}
