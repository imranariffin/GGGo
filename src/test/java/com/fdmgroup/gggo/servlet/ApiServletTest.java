package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.serializer.GGJson;

public class ApiServletTest {

	private static UserDAO udao;
	private static GameDAO gdao;
	private static InviteDAO idao;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ErrorResponse errResponse;
	private HttpSession session;
	private ServletContext context;
	private GGJson ggjson;
	private PrintWriter out;
	
	Map<String, User> onlineUsers;
	
	private User invitor;
	private User invitee;
	private Invite invite;
	
	private User currentUser;
	private User user2;
	private User user3;
	private User user4;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
		gdao = DAOFactory.getPersistentGameDAO();
		idao = DAOFactory.getInviteDAO();
	}
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException, IOException {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		errResponse = Mockito.mock(ErrorResponse.class);
		session = Mockito.mock(HttpSession.class);
		context = Mockito.mock(ServletContext.class);
		ggjson = Mockito.mock(GGJson.class);
		out = Mockito.mock(PrintWriter.class);
		
		onlineUsers = new HashMap<>();
		
		String password = "pazzword";
		
		udao.deleteUser("currentUser");
		udao.deleteUser("user2");
		udao.deleteUser("user3");
		udao.deleteUser("user4");
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		
		currentUser = udao.createUser("currentUser", password);
		user2 = udao.createUser("user2", password);
		user3= udao.createUser("user3", password);
		user4 = udao.createUser("user4", password);
		
		invitor = udao.createUser("invitor", password);
		invitee = udao.createUser("invitee", password);
		invite = idao.createInvite(invitor, invitee);
		
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(currentUser);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getServletContext()).thenReturn(context);
		Mockito.when(context.getAttribute(Attributes.Context.ONLINE_USERS)).thenReturn(onlineUsers);
		Mockito.when(response.getWriter()).thenReturn(out);
		
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		idao.deleteInvite(invitor, invitee, invite);
		
		udao.deleteUser(currentUser.getUsername());
		udao.deleteUser(user2.getUsername());
		udao.deleteUser(user3.getUsername());
		udao.deleteUser(user4.getUsername());
		udao.deleteUser(invitor.getUsername());
		udao.deleteUser(invitee.getUsername());
		
		onlineUsers.clear();
	}
	
	@Test
	public void test_DoGet_RespondsWithEmptyJsonListOfOnlineUser_GivenRequest_WhenNoUsersLoggedIn() 
			throws IOException {
		
		String url = new String("/GGGo/api/online-users");
		
		onlineUsers.remove("currentUser");
		Mockito.when(request.getRequestURI()).thenReturn(url);
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		GGJson ggjson = Mockito.spy(new GGJson());
		servlet.doGet(request, response, ggjson, errResponse);

		Mockito.verify(out, Mockito.times(1)).write("[]");
	}
	
	@Test
	public void test_DoGet_RespondsWithAnEmptyJsonListOf_GivenRequestFromAUser_WhenOnlyTheUserLoggedIn() 
			throws IOException {

		String url = new String("/GGGo/api/online-users");
		onlineUsers.put("currentUser", currentUser);

		Mockito.when(request.getRequestURI()).thenReturn(url);
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		ggjson = Mockito.spy(new GGJson());
		
		servlet.doGet(request, response, ggjson, errResponse);

		Mockito.verify(out, Mockito.times(1)).write("[]");
	}
	
	@Test
	public void test_DoGet_RespondsWithJsonListOnlineUsersExceptCurrentUser_WhenOnlyCurrentUserLoggedIn() 
			throws IOException {

		String url = new String("/GGGo/api/online-users");
		
		onlineUsers.put("currentUser", currentUser);
		onlineUsers.put("user2", user2);
		onlineUsers.put("user3", user3);
		onlineUsers.put("user4", user4);
		
		Mockito.when(request.getRequestURI()).thenReturn(url);
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		ggjson = Mockito.spy(new GGJson());
		servlet.doGet(request, response, ggjson, errResponse);

		onlineUsers.remove(currentUser.getUsername());
		List<User> users = new ArrayList<>();
		for (User user: onlineUsers.values()) {
			users.add(user);
		}
		
		String expected = new GGJson().toJsonUserList(users);
		Mockito.verify(out, Mockito.times(1)).write(expected);
	}
	
	@Test
	public void test_DoGet_CallsRespondWithGameListMethod_GivenGameUrlAndUserOnline() 
			throws ServletException, IOException {
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		String url = new String("/GGGo/api/game");
		ggjson = Mockito.spy(new GGJson());
		
		Mockito.when(request.getRequestURI()).thenReturn(url);
		
		servlet.doGet(request, response, ggjson, errResponse);
		
		Mockito.verify(servlet, Mockito.times(1)).respondWithUserGameList(request, response, ggjson);
	}
	
	@Test
	public void test_RespondWithGameList_ReturnsEmptyJsonListOfGamesOfAUser_GivenUserOnlineWithoutAnyGame() 
			throws IOException {
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		String url = new String("/GGGo/api/game");
		ggjson = Mockito.spy(new GGJson());

		List<Game> userGameList = new ArrayList<>();
		
		Mockito.when(request.getRequestURI()).thenReturn(url);
		
		servlet.respondWithUserGameList(request, response, ggjson);
		
		String expected = "[]";
		Mockito.verify(ggjson, Mockito.times(1)).toJsonGameList(userGameList);
		Mockito.verify(out, Mockito.times(1)).write(expected);
	}
	
	@Test
	public void test_RespondWithGameList_ReturnsJsonListOfGamesOfAUser_GivenUserOnline() 
			throws IOException {
		
		ApiServlet servlet = Mockito.spy(new ApiServlet());
		String url = new String("/GGGo/api/game");
		ggjson = Mockito.spy(new GGJson());
		
		List<Game> userGameList = new ArrayList<>();
		Game game = gdao.createGame(invite);
		userGameList.add(game);
		
		Mockito.when(request.getRequestURI()).thenReturn(url);
		Mockito.when(session.getAttribute(Attributes.Session.CURRENT_USER)).thenReturn(invitor);
		
		servlet.respondWithUserGameList(request, response, ggjson);
		
		String expected = new GGJson().toJsonGameList(userGameList);
		Mockito.verify(ggjson, Mockito.times(1)).toJsonGameList(userGameList);
		Mockito.verify(out, Mockito.times(1)).write(expected);
	}
}
