package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.dao.PersistentGameDAO;
import com.fdmgroup.gggo.dao.PersistentStateDAO;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;
import com.fdmgroup.gggo.controller.Go;
import com.fdmgroup.gggo.controller.State;

public class StateDAOTest {
	
	private static PersistentGameDAO gdao;
	private static PersistentStateDAO sdao;
	private static PlacementDAO pdao;
	
	@BeforeClass
	public static void setupOnce() throws Exception {
		gdao = DAO.getPersistentGameDAO();
		sdao = DAO.getPersistentStateDAO();
		pdao = DAO.getPlacementDAO();	
	}

	@AfterClass
	public static void tearDownOnce() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void test_CreatePersistentState_ReturnsStateWithZeroNumOfPlacements() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		
		assertEquals(0, ps.getPlacements().size());
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_CreateState_SavesStateAndItsPlacements_GivenStateWithPlacements() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		Placement pt = pdao.createPlacement(4, 4, B, ps);
		
		PersistentState actual = sdao.getPersistentState(ps.getStateId());
		
		assertEquals(1, actual.getPlacements().size());
		assertEquals(pt, actual.getPlacements().get(0));
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_CreateState_AppendsToGameListOfStates_GivenGame() {
		PersistentGame pg = gdao.createPersistentGame();
		
		
		int n = pg.getPersistentStates().size();
		sdao.createPersistentState(pg);
		
		assertEquals(n + 1, pg.getPersistentStates().size());
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_GetPersistentStateList_ReturnsEmpty_GivenEmptyGame() {
		PersistentGame pg = gdao.createPersistentGame();
		assertEquals(0, sdao.getPersistentStateList(pg).size());
	}
	
	@Test
	public void test_DeleteState_RemovesAStateFromDatabase_GivenGameAndState() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		
		int n = sdao.getPersistentStateList(pg).size();
		sdao.deletePersistentState(pg, ps);
		
		assertEquals(n - 1, sdao.getPersistentStateList(pg).size());
		
		gdao.deletePersistentGame(pg);
	}
	
	
	@Test
	public void test_DeleteState_RemovesAStateFromGameObject_GivenGameAndState() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		
		int n = sdao.getPersistentStateList(pg).size();
		sdao.deletePersistentState(pg, ps);
		
		assertEquals(n - 1, pg.getPersistentStates().size());
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_DeleteState_CatchesNoResultException_GivenStateId() {
	}
	
	private boolean assertContains(List<State> actual, State item) {
		for (Object e: actual) {
			if (e.equals(item)) {
				return true;
			}
		}
		return false;
	}
}