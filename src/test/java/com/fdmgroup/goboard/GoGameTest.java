package com.fdmgroup.goboard;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class GoGameTest {
	
	GoGame spyGoBoard;
	
	@Test
	public void test_IsValid_ReturnsTrue_GivenEveryPosition_WhenInitialBoard() {
		GoGame goGame = new GoGame();
		for (int i=0; i<goGame.getSize(); i++) {
			for (int j=0; j<goGame.getSize(); j++) {
				assertTrue(goGame.isValid(i, j));
			}
		}
	}
	
	@Test
	public void test_IsValid_ReturnsFalse_GivenAPosition_WhenStoneAlreadyPlaced() {
		GoGame goGame = new GoGame();
		goGame.place(5, 5);
		assertFalse(goGame.isValid(5, 5));
	}
	
	@Test
	public void test_IsValid_ReturnsTrue_GivenAPosition_WhenAnotherPositionPlaced() {
		GoGame goGame = new GoGame();
		goGame.place(5, 5);
		assertTrue(goGame.isValid(0, 0));
	}
	
	@Test
	public void test_Place_IncreasesNumberOfTurnsByOne() {
		GoGame goGame = new GoGame();
		goGame.place(5, 5);
		assertEquals(1, goGame.getTurn());
	}
	
	@Test
	public void test_Place_PlacesABlackStoneOnBoard_GivenNumberOfTurnIsEven() {
		
		GoGame spyGoBoard = spy(new GoGame());
		
		when(spyGoBoard.getTurn()).thenReturn(0);
		spyGoBoard.place(0, 0);
		
		assertEquals("B", spyGoBoard.getStone(0, 0));
		
		when(spyGoBoard.getTurn()).thenReturn(2);
		spyGoBoard.place(6, 6);
		
		System.out.println(spyGoBoard.getStone(6, 6));
		assertEquals("B", spyGoBoard.getStone(6, 6));
	}

	@Test
	public void test_IsFinished_AlwaysReturnsFalse_AtBeginningOfGame() {
		GoGame goGame = new GoGame();
		
		assertFalse(goGame.isFinished());
	}
	
	@Test
	public void test_Pass_FinishesTheGame_WhenCalledTwiceInARow() {
		GoGame goGame = new GoGame();
		
		goGame.pass();
		goGame.pass();
		
		assertTrue(goGame.isFinished());
	}
	
	@Test
	public void test_Pass_DoesNotFinishTheGame_WhenOnceAndFollowedByAPlacement() {
		GoGame goGame = new GoGame();
		
		goGame.pass();
		goGame.place(5, 5);
		
		assertFalse(goGame.isFinished());
	}
	
	@Test
	public void test_Back_ReducesNumberOfTurnByOne() throws EmptyStateStackException {
		GoGame goGame = new GoGame();
		
		goGame.place(0, 0);
		goGame.back();
		
		assertEquals(0, goGame.getTurn());
	}
		
	@Test
	public void test_Back_throwsError_IfCurrentStateIsBeginningOfGame() {
		GoGame goGame = new GoGame();
		
		try {
			goGame.back();
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), is("PlayableGo.back(): State stack is empty -- no previous state available!"));
			assertEquals(e.getClass(), EmptyStateStackException.class);
		}
	}
	
	@Test
	public void test_Back_throwsError_IfKeepsGoingBackBeyondBeginningOfGame() {
		GoGame goGame = new GoGame();
		
		goGame.place(5, 5);
		goGame.place(4, 4);
		
		try {
			goGame.back(); 
			goGame.back();
			goGame.back();
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), is("PlayableGo.back(): State stack is empty -- no previous state available!"));
			assertEquals(e.getClass(), EmptyStateStackException.class);
		}
	}
	
	@Test
	public void test_Next_IncreasesNumberOfTurnsByOne() throws EmptyStateStackException {
		GoGame goGame = new GoGame();
		
		goGame.place(5, 5);
		goGame.back();
		goGame.next();
		
		assertEquals(1, goGame.getTurn());
	}
	
	@Test
	public void test_Next_ThrowsException_IfNoFutureStateAvailable() throws EmptyStateStackException {
		GoGame goGame = new GoGame();
		
		goGame.place(5, 5);
		goGame.back();
		
		try {
			goGame.next();
			goGame.next();
			fail();
		} catch(Exception e) {
			assertThat(e.getMessage(), is("Playable.next(): End of state stack -- no future state available!"));
		}
	}
}
