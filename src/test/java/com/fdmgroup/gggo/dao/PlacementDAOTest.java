package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.E;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.dao.PersistentGameDAO;
import com.fdmgroup.gggo.dao.PersistentStateDAO;
import com.fdmgroup.gggo.dao.PlacementDAO;
import com.fdmgroup.gggo.exceptions.InvalidPlacementException;
import com.fdmgroup.gggo.model.Placement;

public class PlacementDAOTest {

	private static PersistentGameDAO gdao;
	private static PersistentStateDAO sdao;
	private static PlacementDAO pdao;
	
	private static int[][] kifu;  
	private static Game gameWithAFewStates;
	private static Game emptyGame;
	
	@BeforeClass
	public static void setupOnce() {
		gdao = PersistentGameDAO.getInstance();
		sdao = PersistentStateDAO.getInstance();
		pdao = PlacementDAO.getInstance();
		
		emptyGame = gdao.createGame();
		
//		gameWithAFewStates = gdao.createGame();
//		kifu = new int[][] {
//			{4, 4},
//			{7, 7},
//			{4, 7},
//			{7, 4},
//		};
//		try {
//			for (int[] move: kifu) {
//				gameWithAFewStates.place(move[0], move[1]);
//			}
//		} catch (InvalidPlacementException e) {
//			e.printStackTrace();
//		}
	}
	
	@AfterClass
	public static void tearDownOnce() {
		gdao.deleteGame(gameWithAFewStates);
	}
	
	@Test
	public void test_CreatePlacement_ReturnsPlacementWithStateRowColAndTurnNumber_GivenSuch() {
		Game g = gdao.createGame();
		State s = sdao.createState(g);
		int r = 4, c = 4;
		Placement pt = pdao.createPlacement(r, c, s);
		
		assertEquals(c, pt.getColNumber());
		assertEquals(r, pt.getRowNumber());
		assertEquals(s.getTurn(), pt.getPersistentState().getTurnNumber());
		assertEquals(s.getStateId(), pt.getPersistentState().getStateId());
	}
	
	@Test
	public void test_GetPlacements_ReturnsEmptyList_GivenStateOfEmptyGame() {
		State s = emptyGame.getCurState();
		
		int numPlacement = getNumOfPlacements(s);
		
		assertEquals(0, numPlacement);
	}
	
	@Test
	public void test_GetPlacements_ReturnsAllPlacementsOfTheLatestState_GivenState() {
		State curState = gameWithAFewStates.getCurState();
		List<Placement> actual = pdao.getPlacements(curState);
		
		int numPlacements = getNumOfPlacements(curState);
		
		assertEquals(numPlacements, actual.size());
		for (Placement pt: actual) {
			assertEquals(curState.getStateId(), pt.getPersistentState().getStateId());
		}
	}
	
	@Test
	public void test_GetPlacements_ReturnsAllPlacementWithCorrectStone_GivenState() {
		State state = gameWithAFewStates.getCurState();
		List<Placement> actual = pdao.getPlacements(state);
		
		for (Placement pt: actual) {
			boolean found = false;
			for (int i = 0; i < kifu.length; i++) {
				int[] move = kifu[i];
				int r = move[0];
				int c = move[1];
				if (r == pt.getRowNumber() && c == pt.getColNumber()) {
					found = true;
				}
			}
			assertTrue(found);
		}		
	}
	
	@Test
	public void test_GetPlacements_ReturnsAllPlacementsOfAMiddleState_GivenState() {
		List<State> states = gameWithAFewStates.getStates();
		State midState = states.get(states.size() / 2);
		List<Placement> actual = pdao.getPlacements(midState);
		
		int numPlacements = getNumOfPlacements(midState);
		
		assertEquals(numPlacements, actual.size());
		for (Placement pt: actual) {
			assertEquals(midState.getStateId(), pt.getPersistentState().getStateId());
		}
	}
	
	private int getNumOfPlacements(State s) {
		int np = 0;
		Stone[][] curBoard = s.getBoard();
		for (int i = 0; i < curBoard.length; i++) {
			for (int j = 0; j < curBoard.length; j++) {
				if (curBoard[i][j] != E) {
					np++;
				}
			}
		}
		
		return np;
	}
}