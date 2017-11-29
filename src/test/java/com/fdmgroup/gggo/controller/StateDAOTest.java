package com.fdmgroup.gggo.controller;

import static org.junit.Assert.*;

import static com.fdmgroup.gggo.model.Stone.*;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.model.Go;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.State;
import com.fdmgroup.gggo.model.Stone;

public class StateDAOTest {

	private static StateDAO sdao;
	private State s1, s2;
	private static State nonExistingState;
	private static State existingState;
	private static State stateWithPlacement;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sdao = StateDAO.getInstance();
		
		existingState = sdao.postState(new State());
		
		Stone[][] board = new Stone[][] {
//			 0, 1, 2, 3, 4, 5, 6, 7, 8
			{E, E, E, E, E, E, E, E, E}, // 0
			{E, E, E, E, E, E, E, E, E}, // 1
			{E, E, B, E, E, E, E, E, E}, // 2
			{E, E, E, E, E, E, E, E, E}, // 3
			{E, E, E, E, E, E, E, E, E}, // 4
			{E, E, E, E, E, E, E, E, E}, // 5
			{E, E, E, E, E, E, E, E, E}, // 6
			{E, E, E, E, E, E, E, E, E}, // 7
			{E, E, E, E, E, E, E, E, E}, // 8
		};
		stateWithPlacement = new State(board, 1);
		
		nonExistingState = sdao.postState(new State());
		sdao.deleteState(nonExistingState.getStateId());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		sdao.deleteState(existingState.getStateId());
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_PostState_ConstructsEntityManagerThenCallsPersistThenClosesIt() {
	}
	
	@Test
	public void test_PostState_SavesStateAndItsIntersections_GivenStateWithNonEmptyBoard() {
		stateWithPlacement = sdao.postState(stateWithPlacement);
		
		State actual = sdao.getState(stateWithPlacement.getStateId());
		
//		System.out.println();
//		System.out.println(GoUtils.toString(stateWithPlacement.getBoard()));
		
//		System.out.println(stateWithPlacement.getBoard());
		System.out.println(GoUtils.toString(stateWithPlacement.getBoard()));
		System.out.println(GoUtils.toString(actual.getBoard()));
		
		assertTrue(GoUtils.compareBoard(
				stateWithPlacement.getBoard(),
				actual.getBoard()
		));
	}
	
	@Test
	public void test_PostState_SavesStateToDatabase() {
		int n = sdao.getStates().size();
		
		State state = new State(new Stone[][] {
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, B, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
		}, 1);
		
		state = sdao.postState(state);
		
		assertEquals(n + 1, sdao.getStates().size());
		
		sdao.deleteState(state.getStateId());
	}
	
	@Test
	public void test_GetStates_ReturnsListOfStatesOrderedByTurnNumber() {
		int n = sdao.getStates().size();
		
		s1 = new State(new Stone[][] {
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, B, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
		}, 1);
		
		s2 = new State(new Stone[][] {
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, W, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, B, E, E},
			{E, E, E, E, E, E, E, E, E},
			{E, E, E, E, E, E, E, E, E},
		}, 1);
		
		s1 = sdao.postState(s1);
		s2 = sdao.postState(s2);

		List<State> actual = sdao.getStates();

		assertEquals(n + 2, actual.size());
		assertContains(actual, s1);
		assertContains(actual, s2);
		
		sdao.deleteState(s1.getStateId());
		sdao.deleteState(s2.getStateId());
	}
	
	@Test
	public void test_DeleteState_DeletesStateFromDatabase_GivenStateId() {
		int n = sdao.getStates().size();
		
		sdao.deleteState(existingState.getStateId());
		
		assertEquals(n - 1, sdao.getStates().size());
	}
	
	@Test
	public void test_DeleteState_CatchesNoResultException_GivenStateId() {
		int n = sdao.getStates().size();
		sdao.deleteState(nonExistingState.getStateId());
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