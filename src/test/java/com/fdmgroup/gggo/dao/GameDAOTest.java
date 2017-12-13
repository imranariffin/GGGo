package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.E;
import static com.fdmgroup.gggo.controller.Stone.W;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.dao.PersistentGameDAO;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;

public class GameDAOTest {

	private static PersistentGameDAO gdao;
	private static PersistentStateDAO sdao;
	private static PlacementDAO pdao;
	
	private PersistentGame existingEmptyGame;
	private PersistentGame existingGameWithStates;
	private static PersistentGame newGame; 
	private static PersistentGame game;
	
	@BeforeClass
	public static void setupOnce() {
		gdao = PersistentGameDAO.getInstance();
		sdao = PersistentStateDAO.getInstance();
		pdao = PlacementDAO.getInstance();
		
//		game = gdao.createPersistentGame();

//		gameWithAFewStates = GoUtils.generateGoGame(new int[][] {
//			 {4, 4}, {7, 7}, {4, 7}, {7, 4}
//		});
//		gameWithFutureStates = GoUtils.generateGoGame(new int[][] {
//			 {4, 4}, {7, 7}, {4, 7}, {7, 4}
//		});
		
//		try {
//			gameWithAFewStates.back();
//		} catch (EndOfStateStackException eosse) {
//			eosse.printStackTrace();
//		}
	}
	
	@Before
	public void setup() {
		existingEmptyGame = gdao.createPersistentGame();
		existingGameWithStates = gdao.createPersistentGame();
		
		PersistentState ps1 = sdao.createPersistentState(existingGameWithStates);
		pdao.createPlacement(4, 4, B, ps1);
		
		PersistentState ps2 = sdao.createPersistentState(existingGameWithStates);
		pdao.createPlacement(7, 7, W, ps2);
	}
	
	@After
	public void tearDown() {
		gdao.deletePersistentGame(existingEmptyGame);
		gdao.deletePersistentGame(newGame);
		gdao.deletePersistentGame(existingGameWithStates);
	}
	
	@AfterClass
	public static void tearDownOnce() {
	}
	
	@Test
	public void test_GetGame_ReturnsAGame_GivenExistingGameId() {
		PersistentGame expected = existingEmptyGame;
		PersistentGame actual = gdao.getPersistentGame(existingEmptyGame.getGameId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_GetGame_ReturnsNull_GivenNonExistingGameId() {
		
		gdao.deletePersistentGame(existingEmptyGame);

		PersistentGame actual = gdao.getPersistentGame(existingEmptyGame.getGameId());
		
		assertNull(actual);
	}

	@Test
	public void test_CreateGame_ConstructsANewGameAndSaveItOnDatabaseAndReturnsIt() {
		int n = gdao.getPersistentGames().size();
		
		newGame = gdao.createPersistentGame();
		
		assertEquals(n + 1, gdao.getPersistentGames().size());
	}
	
	@Test
	public void test_CreateGame_ReturnsAGameWithAnIdGreaterThanZero() {
		newGame = gdao.createPersistentGame();
		assertTrue(newGame.getGameId() > 0);
	}	
	
	@Test
	public void test_CreateGame_ReturnsAGameWithEmptyListOfStates() {
		newGame = gdao.createPersistentGame();
		assertEquals(0, newGame.getPersistentStates().size());
	}

	@Test
	public void test_DeleteGame_removesExistingGameFromDatabase_GivenGame() {
		
		int n = gdao.getPersistentGames().size();
		
		gdao.deletePersistentGame(existingEmptyGame);
		
		assertEquals(n - 1, gdao.getPersistentGames().size());
		assertNull(gdao.getPersistentGame(existingEmptyGame.getGameId()));
	}
	
	@Test
	public void test_GetPersistentGame_ReturnsGameWithStates_GivenIdOfGameWithStates() {
		PersistentGame expected = existingGameWithStates;
		PersistentGame actual = gdao.getPersistentGame(existingGameWithStates.getGameId());
		
		System.out.println(expected);
		System.out.println(actual);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_CreatePersistentState_AddsAPersistentStateToListOfStatesInGame() {
		int expected = sdao.getPersistentStateList(existingGameWithStates).size();
		sdao.createPersistentState(existingGameWithStates);
		int actual = sdao.getPersistentStateList(existingGameWithStates).size();
		
		assertEquals(expected, actual - 1);
		
		expected = existingGameWithStates.getPersistentStates().size();
		sdao.createPersistentState(existingGameWithStates);
		actual = existingGameWithStates.getPersistentStates().size();
		
		assertEquals(expected, actual - 1);
		
	}
	
	@Test
	public void test_DeletePersistentState_RemovesPersistentStateAlongWithPlacements() {
		int n = sdao.getPersistentStateList(existingGameWithStates).size();
		
		sdao.deletePersistentState(existingGameWithStates, existingGameWithStates.getPersistentStates().get(0));
		
		assertEquals(n - 1, sdao.getPersistentStateList(existingGameWithStates).size());
	}
	
	@Test
	public void test_DeletePersistentState_RemovesPersistentStateOfAPersistentGame() {
		sdao.deletePersistentState(existingGameWithStates, existingGameWithStates.getPersistentStates().get(0));
		PersistentGame actual = gdao.getPersistentGame(existingGameWithStates.getGameId());
//		assertNotEquals(existingGameWithStates, actual);
	}
}
