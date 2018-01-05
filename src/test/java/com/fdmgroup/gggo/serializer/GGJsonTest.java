package com.fdmgroup.gggo.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class GGJsonTest {
	
	private static UserDAO udao;
	private static InviteDAO idao;
	private static GameDAO gdao;
	private static InviteDAO mockIdao;
	private static UserDAO mockUdao;
	
	private GGJson ggjson;

	private User sai;
	private User hikaru;
	private Invite saivshikaru;
	
	private Invite inv;
	private Invite inv2;
	private User invitor;
	private User invitee;
	private List<Invite> receivedInvites;
	private List<Invite> sentInvites;
	
	@BeforeClass
	public static void setupOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
		gdao = DAOFactory.getPersistentGameDAO();
		
		mockIdao = Mockito.mock(InviteDAO.class);
		mockUdao = Mockito.mock(UserDAO.class);
		
		udao.deleteUser("sai");
		udao.deleteUser("hikaru");
	}

	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser(sai.getUsername());
		udao.deleteUser(hikaru.getUsername());
		idao.deleteInvite(sai, hikaru, saivshikaru);
	}
	
	@Before
	public void setup() {
		
		String password = "pazzword";
		sai = udao.createUser("sai", password);
		hikaru = udao.createUser("hikaru", password);
		saivshikaru = idao.createInvite(sai, hikaru); 
		
		ggjson = Mockito.spy(new GGJson());
		
		invitor = Mockito.mock(User.class);
		invitee = Mockito.mock(User.class);
		
		inv = Mockito.mock(Invite.class);
		inv2 = Mockito.mock(Invite.class);
		
		receivedInvites = new ArrayList<>();
		receivedInvites.add(inv);
		receivedInvites.add(inv2);
		
		sentInvites = new ArrayList<>();
		sentInvites.add(inv);
		sentInvites.add(inv2);
		
		Mockito.when(mockIdao.getInvite(1)).thenReturn(inv);
		
		Mockito.when(inv.getInviteId()).thenReturn(1);
		Mockito.when(inv.getInvitor()).thenReturn(invitor);
		Mockito.when(inv.getInvitee()).thenReturn(invitee);
		
		Mockito.when(inv2.getInviteId()).thenReturn(2);
		Mockito.when(inv2.getInvitor()).thenReturn(invitor);
		Mockito.when(inv2.getInvitee()).thenReturn(invitee);
		
		Mockito.when(invitor.getUsername()).thenReturn("saifujiwara");
		Mockito.when(invitee.getUsername()).thenReturn("shindouhikaru");
		
		Mockito.when(invitor.getReceivedInvites()).thenReturn(new ArrayList<>());
		Mockito.when(invitor.getSentInvites()).thenReturn(sentInvites);
		Mockito.when(invitee.getReceivedInvites()).thenReturn(sentInvites);
		Mockito.when(invitee.getSentInvites()).thenReturn(new ArrayList<>());
	}
	
	@Test
	public void test_ToJsonInvite_ReturnsValidJsonString_GivenInvite() {
		
		String expected = "{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}";
		String actual = ggjson.toJsonInvite(inv);
		
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInvite(inv);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJsonInviteList_ReturnsValidJsonString_GivenListOfInvite() {
		List<Invite> invites = new ArrayList<>();
		invites.add(inv);
		invites.add(inv2);

		String expected = new StringBuilder()
			.append("[{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}")
			.append(",{\"inviteId\":2,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}]")
		.toString(); 
		String actual = ggjson.toJsonInviteList(invites);
		
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInviteList(invites);
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInvite(inv);
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInvite(inv2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJsonUser_ReturnsValidJsonString_GivenUserWhosIsInvitor() {
		String expected = new StringBuilder()
			.append("{\"username\":\"saifujiwara\",\"sentInvites\":")
				.append("[{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"},")
				.append("{\"inviteId\":2,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}],")
			.append("\"receivedInvites\":")
				.append("[]")
			.append("}")
		.toString();
		String actual = ggjson.toJsonUser(invitor);

		Mockito.verify(ggjson, Mockito.times(1)).toJsonUser(invitor);
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInvite(inv);
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInvite(inv2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJsonUser_ReturnsValidJsonString_GivenUserWhosIsInvitee() {
		String expected = new StringBuilder()
			.append("{\"username\":\"shindouhikaru\",\"sentInvites\":")
				.append("[],")
			.append("\"receivedInvites\":")
				.append("[{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"},")
				.append("{\"inviteId\":2,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}]")
			.append("}")
		.toString();
		String actual = ggjson.toJsonUser(invitee);
		
		Mockito.verify(ggjson, Mockito.times(1)).toJsonUser(invitee);
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInvite(inv);
		Mockito.verify(ggjson, Mockito.times(1)).toJsonInvite(inv2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJson_ReturnsValidJsonString_GivenListOfUsers() {
		
	}
	
	@Test
	public void test_ToJsonGameList_ReturnsValidString_GivenEmptyListOfGame() {		
		List<Game> userGameList = new ArrayList<>();
		
		String actual = ggjson.toJsonGameList(userGameList);
		String expected = "[]";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJsonGameList_ReturnsValidString_GivenListOfOneGame() {

		Game game = gdao.createGame(saivshikaru);
		List<Game> userGameList = new ArrayList<>();
		userGameList.add(game);
		
		assertEquals(1, game.getStates().size());
		assertEquals(0, game.getFutureStates().size());
		
		String expected = new StringBuilder()
				.append("[")
					.append("{")
						.append("\"gameId\": \"" + game.getGameId() + "\",")
						.append("\"black\": \"" + game.getBlack().getUsername() + "\",")
						.append("\"white\": \"" + game.getWhite().getUsername() + "\",")
						.append("\"states\": ")
						.append(ggjson.toJsonStateList(game.getStates()))
						.append(",")
						.append("\"futureStates\": []")
					.append("}")
				.append("]")
		.toString();
		
		String actual = ggjson.toJsonGameList(userGameList); 
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJsonGame_ReturnsValidGameJson_GivenGame() {
		Game game = gdao.createGame(saivshikaru);
		
		StringBuilder json = new StringBuilder();
		
		json
		.append("{")
			.append("\"gameId\": \"" + game.getGameId() + "\",")
			.append("\"black\": \"" + game.getBlack().getUsername() + "\",")
			.append("\"white\": \"" + game.getWhite().getUsername() + "\",")
			.append("\"states\": ")
				.append(ggjson.toJsonStateList(game.getStates()))
			.append(",")
			.append("\"futureStates\": ")
				.append(ggjson.toJsonStateList(game.getFutureStates()))
		.append("}");
		
		String expected = json.toString();
		String actual = ggjson.toJsonGame(game);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJsonStateList_ReturnsEmptyValidJson_GivenEmptyListOfStates() {

		List<State> states = new ArrayList<>();
		
		String expected = new StringBuilder().append("[]").toString();
		String actual = ggjson.toJsonStateList(states); 
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJsonState_ReturnsValidJson_GivenEmptyState() {
		Game game = gdao.createGame(saivshikaru);
		State s = game.getCurState();
		Stone[][] board = s.getBoard();
		
		StringBuilder json = new StringBuilder();
		
		json.append("{")
			.append("\"stateId\": " + s.getStateId() + ",")
			.append("\"turn\": " + s.getTurn() + ",")
			.append("\"board\": " + "[");
			for (int i = 0; i < board.length; i++) {
				json.append("[");
				for (int j = 0; j < board.length; j++) {
					String st = board[i][j].toString();
					json.append("\"" + st + "\"");
					if (j < board.length - 1) json.append(",");
				}
				json.append("]");
				if (i < board.length - 1) json.append(",");
			}
			json.append("]");
		json.append("}");
		
		String expected = json.toString();
		String actual = ggjson.toJsonState(s);
		
		assertEquals(expected, actual);
	}
}
