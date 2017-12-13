package com.fdmgroup.gggo.controller;

import static org.junit.Assert.*;
import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.E;
import static com.fdmgroup.gggo.controller.Stone.W;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.exceptions.EndOfStateStackException;
import com.fdmgroup.gggo.exceptions.InvalidPlacementException;
import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.dao.PersistentGameDAO;

public class GameTest {
	
	Game spyGoGame;
	Game mockGoGame;
	Game goGame;
	private static PersistentGameDAO gdao;
	
	@BeforeClass
	public static void setupOnce() {
		gdao = PersistentGameDAO.getInstance();
	}
	
	@Before
	public void setup() {
		goGame = gdao.createGame();
		mockGoGame = mock(Game.class);
		spyGoGame = spy(goGame);
	}
	
	@After
	public void tearDown() {
		gdao.deleteGame(goGame);
	}
	
	@Test
	public void test_IsValid_ReturnsTrue_GivenEveryPosition_WhenInitialBoard() {
		for (int i=0; i<goGame.getSize(); i++) {
			for (int j=0; j<goGame.getSize(); j++) {
				assertTrue(goGame.isValid(i, j));
			}
		}
	}
	
	@Test
	public void test_IsValid_ReturnsFalse_GivenAPosition_WhenStoneAlreadyPlaced() {
		
		try {
			goGame.place(5, 5);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertFalse(goGame.isValid(5, 5));
	}
	
	@Test
	public void test_IsValid_ReturnsTrue_GivenAPosition_WhenAnotherPositionPlaced() {
		
		try {
			goGame.place(5, 5);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertTrue(goGame.isValid(0, 0));
	}
	
	@Test
	public void test_Place_IncreasesNumberOfTurnsByOne() {
		
		try {
			goGame.place(5, 5);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertEquals(1, goGame.getTurn());
	}
	
	@Test
	public void test_Place_PlacesABlackStoneOnBoard_GivenNumberOfTurnIsEven() {
		when(spyGoGame.getTurn()).thenReturn(0);
		
		try {
			spyGoGame.place(0, 0);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertEquals(B, spyGoGame.getStone(0, 0));
	}
	
	@Test
	public void test_Place_PlacesABlackStoneOnBoard_GivenNumberOfTurnIsEven2() {
		when(spyGoGame.getTurn()).thenReturn(2);
		
		try {
			spyGoGame.place(6, 6);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertEquals(B, spyGoGame.getStone(6, 6));
	}
	
	@Test
	public void test_Place_WillCopyTheCurrentBoardAndAddOneMoreApproriateStone() {
		Stone[][] currBoard = new Stone[][] {
//			 0, 1, 2, 3, 4, 5, 6, 7, 8
			{E, E, E, E, E, E, E, E, E}, // 0
			{E, E, E, E, E, E, E, E, E}, // 1
			{E, E, E, B, E, E, E, E, E}, // 2
			{E, E, B, W, E, E, E, E, E}, // 3
			{E, E, E, E, E, E, E, E, E}, // 4
			{E, E, E, E, E, E, E, E, E}, // 5
			{E, E, E, E, E, E, E, E, E}, // 6
			{E, E, E, E, E, E, E, E, E}, // 7
			{E, E, E, E, E, E, E, E, E}, // 8			
		};
		
		try {
			goGame.place(2, 3);
			goGame.place(3, 3);
			goGame.place(3, 2);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertTrue(goGame.getCurState().equals(new State(currBoard, goGame.getNextTurn())));
		
		try {
			goGame.place(4, 3);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertEquals(W, goGame.getStone(4, 3));
	}
	
	@Test
	public void test_Place_WillRemoveCapturedStones_GivenOneEyedCapture() {
		Stone[][] currBoard = new Stone[][] {
// 			 0, 1, 2, 3, 4, 5, 6, 7, 8
			{E, E, E, E, E, E, E, E, E}, // 0
			{E, E, W, B, E, E, E, E, E}, // 1
			{E, E, B, W, E, E, E, E, E}, // 2
			{E, E, W, B, E, E, E, E, E}, // 3
			{E, E, E, E, E, E, E, E, E}, // 4
			{E, E, E, E, E, E, E, E, E}, // 5
			{E, E, E, E, E, E, E, E, E}, // 6
			{E, E, E, E, E, E, E, E, E}, // 7
			{E, E, E, E, E, E, E, E, E}, // 8
		};
		try {
			goGame.place(2, 2); // B
			goGame.place(2, 3); // W
			goGame.place(1, 3); // B
			goGame.place(1, 2); // W
			goGame.place(3, 3); // B
			goGame.place(3, 2); // W
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertTrue(goGame.getCurState().equals(new State(currBoard, goGame.getNextTurn())));
		/*
		 * On this board, the number of turn is 6 so it's Black's turn to place a stone.
		 * When Black places at 2,4, White at 2,3 should be captured and removed from board.
		*/
		
		
		try {
			goGame.place(2, 4);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertEquals(B, goGame.getStone(2, 4));
		assertEquals(E, goGame.getStone(2, 3));
	}
	
	@Test
	public void test_Place_WillRemoveCapturedStones_GivenOneEyedCaptureOnTheWall() {
		Stone[][] currBoard = new Stone[][] {
// 			 0, 1, 2, 3, 4, 5, 6, 7, 8
			{E, E, E, E, E, E, E, E, E}, // 0
			{W, B, E, E, E, E, E, E, E}, // 1
			{B, W, E, E, E, E, E, E, E}, // 2
			{E, B, E, E, E, E, E, E, E}, // 3
			{E, E, E, E, E, E, E, E, E}, // 4
			{E, E, E, E, E, E, E, E, E}, // 5
			{E, E, E, E, E, E, E, E, E}, // 6
			{E, E, E, E, E, E, E, E, E}, // 7
			{E, E, E, E, E, E, E, E, E}, // 8
		};
		
		try {
			goGame.place(1, 1); // B
			goGame.place(1, 0); // W
			goGame.place(2, 0); // B
			goGame.place(2, 1); // W
			goGame.place(3, 1); // B
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertTrue(goGame.getCurState().equals(new State(currBoard, goGame.getNextTurn())));
		/*
		 * On this board, the number of turn is 5 so it's White's turn to place a stone.
		 * When White places at 3,0, Black at 2,0 should be captured and removed from board.
		*/
		
		try { 
			goGame.place(3, 0); // W
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertEquals(E, goGame.getStone(2, 0));
	}
	
	@Test
	public void test_Place_WillRemoveCapturedStones_GivenTwoEyedCapture() {
		try {
			goGame.place(2, 2); // B
			goGame.place(2, 3); // W
			goGame.place(1, 3); // B
			goGame.place(1, 2); // W
			goGame.place(3, 3); // B
			
			goGame.place(2, 4); // W
			goGame.place(1, 4); // B
			goGame.place(3, 2); // W
			goGame.place(2, 1); // B
			goGame.place(3, 1); // W
			
			goGame.place(3, 4); // B
			goGame.place(1, 1); // W
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		Stone[][] currBoard = new Stone[][] {
//			 0, 1, 2, 3, 4, 5, 6, 7, 8
			{E, E, E, E, E, E, E, E, E}, // 0
			{E, W, W, B, B, E, E, E, E}, // 1
			{E, B, B, W, W, E, E, E, E}, // 2
			{E, W, W, B, B, E, E, E, E}, // 3
			{E, E, E, E, E, E, E, E, E}, // 4
			{E, E, E, E, E, E, E, E, E}, // 5
			{E, E, E, E, E, E, E, E, E}, // 6
			{E, E, E, E, E, E, E, E, E}, // 7
			{E, E, E, E, E, E, E, E, E}, // 8
		};
		
		assertTrue(goGame.getCurState().equals(new State(currBoard, goGame.getNextTurn())));
		
		/*
		 * On this board, the number of turn is 12 so it's Black's turn to place a stone.
		 * When Black places at 3,4, White at 2,3 and 2,4 should be captured and removed from board.
		*/
		
		try {
			goGame.place(2, 5);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertEquals(B, goGame.getStone(1, 4));
		assertEquals(E, goGame.getStone(2, 3));
		assertEquals(E, goGame.getStone(2, 4));
	}

	@Test
	public void test_IsFinished_AlwaysReturnsFalse_AtBeginningOfGame() {		
		assertFalse(goGame.isFinished());
	}
	
	@Test
	public void test_Pass_FinishesTheGame_WhenCalledTwiceInARow() {		
		goGame.pass();
		goGame.pass();
		
		assertTrue(goGame.isFinished());
	}
	
	@Test
	public void test_Pass_DoesNotFinishTheGame_WhenOnceAndFollowedByAPlacement() {
		goGame.pass();
		
		try {
			goGame.place(5, 5);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		assertFalse(goGame.isFinished());
	}
	
	@Test
	public void test_Back_ReducesNumberOfTurnByOne() {
		
		try {
			goGame.place(0, 0);
			goGame.back();
		} catch (InvalidPlacementException | EndOfStateStackException e) {
			fail();
		}
		
		assertEquals(0, goGame.getTurn());
	}
		
	@Test
	public void test_Back_throwsError_IfCurrentStateIsBeginningOfGame() {
		try {
			goGame.back();
			fail();
		} catch (Exception e) {
			String expectedErrMsg = "PlayableGo.back(): State stack reaches its end -- no previous state available!";
			assertThat(e.getMessage(), is(expectedErrMsg));
			assertEquals(EndOfStateStackException.class, e.getClass());
		}
	}
	
	@Test
	public void test_Back_throwsError_IfKeepsGoingBackBeyondBeginningOfGame() {
		try {
			goGame.place(5, 5);
			goGame.place(4, 4);
		} catch (InvalidPlacementException e1) {
			fail();
		}
		
		try {
			goGame.back(); 
			goGame.back();
			goGame.back();
			fail();
		} catch (Exception e) {
			String expectedErrMsg = "PlayableGo.back(): State stack reaches its end -- no previous state available!";
			assertThat(e.getMessage(), is(expectedErrMsg));
			assertEquals(EndOfStateStackException.class, e.getClass());
		}
	}
	
	@Test
	public void test_Next_IncreasesNumberOfTurnsByOne() {
 
		try {
			goGame.place(5, 5);
			goGame.back();
			goGame.forward();
		} catch (InvalidPlacementException | EndOfStateStackException e) {
			fail();
		}

		assertEquals(1, goGame.getTurn());
	}
	
	@Test
	public void test_Next_ThrowsException_IfNoFutureStateAvailable() {
		
		try {
			goGame.place(5, 5);
			goGame.back();
		} catch (InvalidPlacementException | EndOfStateStackException e1) {
			fail();
		}
		
		try {
			goGame.forward();
			goGame.forward();
			fail();
		} catch(Exception e) {
			String expectedErrMsg = "Playable.next(): State stack reaches its end -- no future state available!";
			assertThat(e.getMessage(), is(expectedErrMsg));
			assertEquals(EndOfStateStackException.class, e.getClass());
		}
	}
	
	@Test
	public void test_Place_WillRemoveCapturedStones_GivenCaptureFromWithin() {
		try {
			goGame.place(3, 3); // B
			goGame.place(2, 3); // W
			goGame.place(3, 4); // B
			goGame.place(2, 4); // W
			goGame.place(3, 5); // B
			goGame.place(2, 5); // W
			
			goGame.place(4, 5); // B
			goGame.place(4, 6); // W
			goGame.place(5, 5); // B
			goGame.place(5, 6); // W
			goGame.place(5, 3); // B
			goGame.place(5, 2); // W
			
			goGame.place(5, 4); // B
			goGame.place(6, 4); // W
			
			goGame.place(4, 3); // B
			goGame.place(4, 2); // W
			
			goGame.place(2, 2); // B
			goGame.place(3, 6); // W
			goGame.place(2, 6); // B
			goGame.place(3, 2); // W
			goGame.place(6, 6); // B
			goGame.place(6, 5); // W
			goGame.place(5, 7); // B
			goGame.place(6, 3); // W
			goGame.place(3, 7); // B
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		Stone[][] expected = new Stone[][] {
//			 0, 1, 2, 3, 4, 5, 6, 7, 8
			{E, E, E, E, E, E, E, E, E}, // 0
			{E, E, E, E, E, E, E, E, E}, // 1
			{E, E, B, W, W, W, B, E, E}, // 2
			{E, E, W, B, B, B, W, B, E}, // 3
			{E, E, W, B, E, B, W, E, E}, // 4
			{E, E, W, B, B, B, W, B, E}, // 5
			{E, E, E, W, W, W, B, E, E}, // 6
			{E, E, E, E, E, E, E, E, E}, // 7
			{E, E, E, E, E, E, E, E, E}, // 8
		};
		
		assertTrue(goGame.getCurState().equals(new State(expected, goGame.getNextTurn())));
		
		/*
		 * On this board, the number of turn is 12 so it's Black's turn to place a stone.
		 * When Black places at 3,4, White at 2,3 and 2,4 should be captured and removed from board.
		*/
		
		try {
			goGame.place(4, 4);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		expected = new Stone[][] {
//			 0, 1, 2, 3, 4, 5, 6, 7, 8
			{E, E, E, E, E, E, E, E, E}, // 0
			{E, E, E, E, E, E, E, E, E}, // 1
			{E, E, B, W, W, W, B, E, E}, // 2
			{E, E, W, E, E, E, W, B, E}, // 3
			{E, E, W, E, W, E, W, E, E}, // 4
			{E, E, W, E, E, E, W, B, E}, // 5
			{E, E, E, W, W, W, B, E, E}, // 6
			{E, E, E, E, E, E, E, E, E}, // 7
			{E, E, E, E, E, E, E, E, E}, // 8
		};
		
		assertTrue(goGame.getCurState().equals(new State(expected, goGame.getNextTurn())));
	}
}
