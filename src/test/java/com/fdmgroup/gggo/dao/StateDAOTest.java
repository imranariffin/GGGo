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
	
	private static PersistentGame existingGameWithAFewStates;
	private static PersistentGame existingEmptyGame;
	private static PersistentGame pg;
	
	private static PersistentState nonExistingState;
	private static PersistentState existingEmptyState;
	private static PersistentState stateWithAPlacement;
	private static PersistentState stateWithAFewPlacements;
	
	@BeforeClass
	public static void setupOnce() throws Exception {
		gdao = PersistentGameDAO.getInstance();
		sdao = PersistentStateDAO.getInstance();
		pdao = PlacementDAO.getInstance();		
		
		existingEmptyGame = gdao.createPersistentGame();
		existingGameWithAFewStates = gdao.createPersistentGame(); 
		
		pg = gdao.createPersistentGame();
		existingEmptyState = sdao.createPersistentState(pg);
		stateWithAPlacement = sdao.createPersistentState(pg);
		pdao.createPlacement(3, 3, B, stateWithAPlacement);
		stateWithAFewPlacements = sdao.createPersistentState(pg);
		pdao.createPlacement(3, 3, B, stateWithAFewPlacements);
				
		PersistentGame existingGameWithAFewStates = gdao.createPersistentGame();
		PersistentState ps1 = sdao.createPersistentState(existingGameWithAFewStates);
		pdao.createPlacement(4, 4, B, ps1);
		PersistentState ps2 = sdao.createPersistentState(existingGameWithAFewStates);
		pdao.createPlacement(7, 7, B, ps2);
		
		nonExistingState = sdao.createPersistentState(pg);
		sdao.deletePersistentState(pg, nonExistingState);
	}

	@AfterClass
	public static void tearDownOnce() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() {
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_GetPersistentStateList_ReturnsEmpty_GivenEmptyGame() {
		assertEquals(0, sdao.getPersistentStateList(existingEmptyGame).size());
	}
	
	@Test
	public void test_CreatePersistentState_ReturnsStateWithZeroNumOfPlacements() {
		PersistentState ps = sdao.createPersistentState(existingGameWithAFewStates);
		assertEquals(0, ps.getPlacements().size());
	}
//	
//	@Test
//	public void test_PostState_SavesStateAndItsIntersections_GivenStateWithNonEmptyBoard() {
//		stateWithPlacement = sdao.postState(stateWithPlacement);
//		
//		State actual = sdao.getState(stateWithPlacement.getStateId());
//		
////		System.out.println();
////		System.out.println(GoUtils.toString(stateWithPlacement.getBoard()));
//		
////		System.out.println(stateWithPlacement.getBoard());
//		System.out.println(GoUtils.toString(stateWithPlacement.getBoard()));
//		System.out.println(GoUtils.toString(actual.getBoard()));
//		
//		assertTrue(GoUtils.compareBoard(
//				stateWithPlacement.getBoard(),
//				actual.getBoard()
//		));
//	}
//	
//	@Test
//	public void test_PostState_SavesStateToDatabase() {
//		int n = sdao.getStates().size();
//		
//		State state = new State(new Stone[][] {
//			{E, E, E, E, E, E, E, E, E},
//			{E, E, E, E, E, E, E, E, E},
//			{E, E, E, E, E, E, E, E, E},
//			{E, E, E, E, E, E, E, E, E},
//			{E, E, E, E, E, E, E, E, E},
//			{E, E, E, E, E, E, E, E, E},
//			{E, E, E, E, E, E, B, E, E},
//			{E, E, E, E, E, E, E, E, E},
//			{E, E, E, E, E, E, E, E, E},
//		}, 1);
//		
//		state = sdao.postState(state);
//		
//		assertEquals(n + 1, sdao.getStates().size());
//		
//		sdao.deleteState(state.getStateId());
//	}
//	
//	@Test
//	public void test_DeleteState_DeletesStateFromDatabase_GivenStateId() {
//		int n = sdao.getStates().size();
//		
//		sdao.deleteState(existingState.getStateId());
//		
//		assertEquals(n - 1, sdao.getStates().size());
//	}
//	
//	@Test
//	public void test_DeleteState_CatchesNoResultException_GivenStateId() {
//		int n = sdao.getStates().size();
//		sdao.deleteState(nonExistingState.getStateId());
//	}
//	
//	private boolean assertContains(List<State> actual, State item) {
//		for (Object e: actual) {
//			if (e.equals(item)) {
//				return true;
//			}
//		}
//		return false;
//	}
}