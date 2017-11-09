package com.fdmgroup.goboard;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static com.fdmgroup.goboard.Stone.E;
import static com.fdmgroup.goboard.Stone.W;
import static com.fdmgroup.goboard.Stone.B;

import org.junit.Before;
import org.junit.Test;

public class GoGameTest {
	
	GoGame spyGoGame;
	GoGame mockGoGame;
	GoGame goGame;
	
	@Before
	public void setup() {
		goGame = new GoGame();
		mockGoGame = mock(GoGame.class);
		spyGoGame = spy(goGame);
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
		goGame.place(5, 5);
		assertFalse(goGame.isValid(5, 5));
	}
	
	@Test
	public void test_IsValid_ReturnsTrue_GivenAPosition_WhenAnotherPositionPlaced() {
		goGame.place(5, 5);
		assertTrue(goGame.isValid(0, 0));
	}
	
	@Test
	public void test_Place_IncreasesNumberOfTurnsByOne() {
		goGame.place(5, 5);
		assertEquals(1, goGame.getTurn());
	}
	
	@Test
	public void test_Place_PlacesABlackStoneOnBoard_GivenNumberOfTurnIsEven() {
		when(spyGoGame.getTurn()).thenReturn(0);
		spyGoGame.place(0, 0);
		
		assertEquals(B, spyGoGame.getStone(0, 0));
	}
	
	@Test
	public void test_Place_PlacesABlackStoneOnBoard_GivenNumberOfTurnIsEven2() {
		when(spyGoGame.getTurn()).thenReturn(2);
		spyGoGame.place(6, 6);
		
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
		
		goGame.place(2, 3);
		goGame.place(3, 3);
		goGame.place(3, 2);
		
		assertTrue(goGame.getCurState().equals(new State(currBoard)));
		
		goGame.place(4, 3);
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
		goGame.place(2, 2); // B
		goGame.place(2, 3); // W
		goGame.place(1, 3); // B
		goGame.place(1, 2); // W
		goGame.place(3, 3); // B
		goGame.place(3, 2); // W
		
		assertTrue(goGame.getCurState().equals(new State(currBoard)));
		/*
		 * On this board, the number of turn is 6 so it's Black's turn to place a stone.
		 * When Black places at 2,4, White at 2,3 should be captured and removed from board.
		*/
		
		goGame.place(2, 4);
		
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
		goGame.place(1, 1); // B
		goGame.place(1, 0); // W
		goGame.place(2, 0); // B
		goGame.place(2, 1); // W
		goGame.place(3, 1); // B
		
		assertTrue(goGame.getCurState().equals(new State(currBoard)));
		/*
		 * On this board, the number of turn is 5 so it's White's turn to place a stone.
		 * When White places at 3,0, Black at 2,0 should be captured and removed from board.
		*/
		
		goGame.place(3, 0); // W
		
		assertEquals(E, goGame.getStone(2, 0));
	}
	
	@Test
	public void test_Place_WillRemoveCapturedStones_GivenTwoEyedCapture() {
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
		
		assertTrue(goGame.getCurState().equals(new State(currBoard)));
		
		/*
		 * On this board, the number of turn is 12 so it's Black's turn to place a stone.
		 * When Black places at 3,4, White at 2,3 and 2,4 should be captured and removed from board.
		*/
		goGame.place(2, 5);
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
		goGame.place(5, 5);
		
		assertFalse(goGame.isFinished());
	}
	
	@Test
	public void test_Back_ReducesNumberOfTurnByOne() throws EndOfStateStackException {
		goGame.place(0, 0);
		goGame.back();
		
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
		goGame.place(5, 5);
		goGame.place(4, 4);
		
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
	public void test_Next_IncreasesNumberOfTurnsByOne() throws EndOfStateStackException {

		goGame.place(5, 5);
		goGame.back();
		goGame.next();
		
		assertEquals(1, goGame.getTurn());
	}
	
	@Test
	public void test_Next_ThrowsException_IfNoFutureStateAvailable() throws EndOfStateStackException {
		
		goGame.place(5, 5);
		goGame.back();
		
		try {
			goGame.next();
			goGame.next();
			fail();
		} catch(Exception e) {
			String expectedErrMsg = "Playable.next(): State stack reaches its end -- no future state available!";
			assertThat(e.getMessage(), is(expectedErrMsg));
			assertEquals(EndOfStateStackException.class, e.getClass());
		}
	}
}
