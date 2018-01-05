package com.fdmgroup.gggo.dao;

import static org.junit.Assert.*;


import com.fdmgroup.gggo.model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.PersistentGame;

public class GameDAOTest {

	private static UserDAO udao;
	private static InviteDAO idao;
	private static GameDAO gdao;
	
	@BeforeClass
	public static void setupOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
		gdao = DAOFactory.getPersistentGameDAO();
		
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
	}
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {		
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
	}
	
	@AfterClass
	public static void tearDownOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
	}

	@Test
	public void test_CreatePersistentGame_ConstructsANewGameAndSaveItOnDatabaseAndReturnsIt() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		PersistentGame pg = gdao.createPersistentGame(inv);
		PersistentGame actual = gdao.getPersistentGame(pg.getGameId());

		assertEquals(pg, actual);
		
		gdao.deletePersistentGame(pg, inv);
	}
	
	@Test
	public void test_CreatePersistentGame_ReturnsAGameWithAnIdGreaterThanZero() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		PersistentGame pg = gdao.createPersistentGame(inv);
		
		assertTrue(pg.getGameId() > 0);
		
		gdao.deletePersistentGame(pg, inv);
	}	
	
	@Test
	public void test_CreatePersistentGame_ReturnsAGameWithEmptyListOfStates() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		PersistentGame pg = gdao.createPersistentGame(inv);
		
		assertNotNull(pg.getPersistentStates());
		assertEquals(0, pg.getPersistentStates().size());
		
		gdao.deletePersistentGame(pg, inv);
	}

	
	@Test
	public void test_GetPersistentGame_ReturnsNull_GivenNonExistingGameId() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		PersistentGame pg = gdao.createPersistentGame(inv);
		int id = pg.getGameId();
		gdao.deletePersistentGame(pg, inv);
		
		assertNull(gdao.getPersistentGame(id));
	}
	
	@Test
	public void test_DeletePersistentGame_removesExistingGameFromDatabase_GivenGame() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		PersistentGame pg = gdao.createPersistentGame(inv);

		int n = gdao.getPersistentGames().size();
		
		gdao.deletePersistentGame(pg, inv);
		
		assertEquals(n - 1, gdao.getPersistentGames().size());
	}
	
	@Test
	public void test_DeletePersistentGame_DoesNothing_GivenNonExistingGame() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		PersistentGame pg = gdao.createPersistentGame(inv);
		
		gdao.deletePersistentGame(pg, inv);
		int n = gdao.getPersistentGames().size();
		
		gdao.deletePersistentGame(pg, inv);
		
		assertEquals(n, gdao.getPersistentGames().size());
	}
	
	@Test
	public void test_CreateGame_InitGameAndPersistentGameThenReturnsGame_GivenInviteWithNullGame() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		Invite inv = idao.createInvite(invitor, invitee);
		
		int n = gdao.getPersistentGames().size();
		
		Game g = gdao.createGame(inv);
		
		assertNotNull(g);
		
		PersistentGame pg = gdao.getPersistentGame(g.getGameId());
		
		assertNotNull(pg);
		assertEquals(pg.getGameId(), g.getGameId());
		assertEquals(pg.getPersistentStates().size(), g.getStates().size());
		assertEquals(n + 1, gdao.getPersistentGames().size());
		
		gdao.deletePersistentGame(pg, inv);
	}
	
	@Test
	public void test_GetGames_ReturnsListOfGamesByConvertingFromPersistentGames_GivenUserId() {
		User imran = udao.createUser("imran.ariffin", "pazzword");
		User amir = udao.createUser("amir.ariffin", "pazzword");
		Invite inv1 = idao.createInvite(imran, amir);
		Invite inv2 = idao.createInvite(imran, amir);
		Game game1 = gdao.createGame(inv1);
		Game game2 = gdao.createGame(inv2);
		
		assertNotNull(gdao.getGames(imran.getUsername()));
		assertEquals(2, gdao.getGames(imran.getUsername()).size());
	}
	
	@Test
	public void test_GetGame_ReturnsGame_GivenGameId() {
		
	}
	
	@Test
	public void test_DeleteGame_removesExistingGameFromDatabase_GivenGameId() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		Game game = gdao.createGame(inv);

		int n = gdao.getPersistentGames().size();
		
		gdao.deleteGame(game.getGameId());
		
		assertEquals(n - 1, gdao.getPersistentGames().size());
	}
	
	@Test
	public void test_DeleteGame_DoesNothing_GivenNonExistingGameId() {
		User invitor = udao.createUser("invitor", "password");
		User invitee = udao.createUser("invitee", "password");
		Invite inv = idao.createInvite(invitor, invitee);
		Game game = gdao.createGame(inv);
		
		gdao.deleteGame(game.getGameId());
		int n = gdao.getPersistentGames().size();
		
		gdao.deleteGame(game.getGameId());
		
		assertEquals(n, gdao.getPersistentGames().size());
	}
}