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
	private static int nGames;
	
	@BeforeClass
	public static void setupOnce() {
		gdao = DAO.getPersistentGameDAO();
		sdao = DAO.getPersistentStateDAO();
		pdao = DAO.getPlacementDAO();

		nGames = gdao.getPersistentGames().size();
	}
	
	@Before
	public void setup() {
	}
	
	@After
	public void tearDown() {
		assertEquals(nGames, gdao.getPersistentGames().size());
	}
	
	@AfterClass
	public static void tearDownOnce() {
	}

	@Test
	public void test_CreatePersistentGame_ConstructsANewGameAndSaveItOnDatabaseAndReturnsIt() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentGame actual = gdao.getPersistentGame(pg.getGameId());

		assertEquals(pg, actual);
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_CreatePersistentGame_ReturnsAGameWithAnIdGreaterThanZero() {
		PersistentGame pg = gdao.createPersistentGame();
		
		assertTrue(pg.getGameId() > 0);
		
		gdao.deletePersistentGame(pg);
	}	
	
	@Test
	public void test_CreatePersistentGame_ReturnsAGameWithEmptyListOfStates() {
		PersistentGame pg = gdao.createPersistentGame();
		
		assertNotNull(pg.getPersistentStates());
		assertEquals(0, pg.getPersistentStates().size());
		
		gdao.deletePersistentGame(pg);
	}

	
	@Test
	public void test_GetPersistentGame_ReturnsNull_GivenNonExistingGameId() {
		PersistentGame pg = gdao.createPersistentGame();
		int id = pg.getGameId();
		gdao.deletePersistentGame(pg);
		
		assertNull(gdao.getPersistentGame(id));
	}
	
	@Test
	public void test_DeletePersistentGame_removesExistingGameFromDatabase_GivenGame() {
		PersistentGame pg = gdao.createPersistentGame();
		int n = gdao.getPersistentGames().size();
		
		gdao.deletePersistentGame(pg);
		
		assertEquals(n - 1, gdao.getPersistentGames().size());
	}
	
	@Test
	public void test_DeletePersistentGame_DoesNothing_GivenNonExistingGame() {
		PersistentGame pg = gdao.createPersistentGame();
		gdao.deletePersistentGame(pg);
		int n = gdao.getPersistentGames().size();
		
		gdao.deletePersistentGame(pg);
		
		assertEquals(n, gdao.getPersistentGames().size());
	}
}