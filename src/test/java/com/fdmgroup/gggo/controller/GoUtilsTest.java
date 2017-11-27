package com.fdmgroup.gggo.controller;

import static com.fdmgroup.gggo.model.Stone.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.model.Game;
import com.fdmgroup.gggo.model.Stone;

public class GoUtilsTest {
	
	@Test
	public void test_ToString_ReturnsCorrectEmptyBoardString_GivenEmptyBoard() {
		Game newGame = new Game();
		String expected =
				
				"╔════════════════════════╗\n"+
				"║    A B C D E F G H J   ║\n"+
				"║  1 ┌─┬─┬─┬─┬─┬─┬─┬─┐   ║\n"+
				"║  2 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  3 ├─┼─╬─┼─┼─┼─╬─┼─┤   ║\n"+
				"║  4 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  5 ├─┼─┼─┼─╬─┼─┼─┼─┤   ║\n"+
				"║  6 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  7 ├─┼─╬─┼─┼─┼─╬─┼─┤   ║\n"+
				"║  8 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  9 └─┴─┴─┴─┴─┴─┴─┴─┘   ║\n"+
				"║     ---  GGGo  ---     ║\n"+
				"╚════════════════════════╝\n";
		
		String actual = GoUtils.toString(newGame.getBoard());
		assertEquals(expected, actual);
	}

	
	@Test
	public void test_ToString_ReturnsCorrectBoardString_GivenOnePlacement() {
		Game goGame = new Game();
		String expected =
				
				"╔════════════════════════╗\n"+
				"║    A B C D E F G H J   ║\n"+
				"║  1 ┌─┬─┬─┬─┬─┬─┬─┬─┐   ║\n"+
				"║  2 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  3 ├─┼─○─┼─┼─┼─╬─┼─┤   ║\n"+
				"║  4 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  5 ├─┼─┼─┼─╬─┼─┼─┼─┤   ║\n"+
				"║  6 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  7 ├─┼─╬─┼─┼─┼─╬─┼─┤   ║\n"+
				"║  8 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  9 └─┴─┴─┴─┴─┴─┴─┴─┘   ║\n"+
				"║     ---  GGGo  ---     ║\n"+
				"╚════════════════════════╝\n";
		
		try {
			goGame.place(2, 2);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		String actual = GoUtils.toString(goGame.getBoard());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToString_ReturnsCorrectBoardString_GivenAFewPlacements() {
		Game goGame = new Game();
		String expected =
				
				"╔════════════════════════╗\n"+
				"║    A B C D E F G H J   ║\n"+
				"║  1 ┌─┬─┬─┬─┬─┬─┬─┬─┐   ║\n"+
				"║  2 ├─○─●─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  3 ├─┼─○─┼─┼─┼─╬─┼─┤   ║\n"+
				"║  4 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  5 ├─┼─┼─┼─╬─┼─┼─┼─┤   ║\n"+
				"║  6 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  7 ├─┼─╬─┼─┼─┼─╬─┼─┤   ║\n"+
				"║  8 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  9 └─┴─┴─┴─┴─┴─┴─┴─┘   ║\n"+
				"║     ---  GGGo  ---     ║\n"+
				"╚════════════════════════╝\n";
		
		try {
			goGame.place(2, 2);
			goGame.place(1, 2);
			goGame.place(1, 1);
		} catch (InvalidPlacementException e) {
			fail();
		}
		
		String actual = GoUtils.toString(goGame.getBoard());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToString_ReturnsCorrectBoardString_GivenACapture() {
		Game goGame = new Game();
		String expected =
				
				"╔════════════════════════╗\n"+
				"║    A B C D E F G H J   ║\n"+
				"║  1 ┌─┬─○─┬─┬─┬─┬─┬─┐   ║\n"+
				"║  2 ├─○─┼─○─┼─┼─┼─┼─┤   ║\n"+
				"║  3 ├─●─○─●─┼─┼─╬─┼─┤   ║\n"+
				"║  4 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  5 ├─┼─┼─┼─╬─┼─┼─┼─┤   ║\n"+
				"║  6 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  7 ├─┼─╬─┼─┼─┼─╬─┼─┤   ║\n"+
				"║  8 ├─┼─┼─┼─┼─┼─┼─┼─┤   ║\n"+
				"║  9 └─┴─┴─┴─┴─┴─┴─┴─┘   ║\n"+
				"║     ---  GGGo  ---     ║\n"+
				"╚════════════════════════╝\n";

		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,B,E,E,E,E,E,E}, // 0
			{E,B,E,B,E,E,E,E,E}, // 1
			{E,W,B,W,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		String actual = GoUtils.toString(spyGoGame.getBoard());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_Captured_ReturnsTrue_GivenAGroupThatHasNoLiberty() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,B,E,E,E,E,E,E}, // 0
			{E,B,W,B,E,E,E,E,E}, // 1
			{E,E,B,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		int n = spyGoGame.getSize();
		boolean[][] visited = new boolean[n][n];
		Stone[][] board = spyGoGame.getBoard();
		boolean actual = GoUtils.captured(board, visited, W, 1, 2);
		
		assertTrue(actual);
	}
	
	@Test
	public void test_Captured_ReturnsFalse_GivenAGroupThatStillHasLiberty() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,B,E,E,E,E,E,E}, // 0
			{E,B,W,B,E,E,E,E,E}, // 1
			{E,E,E,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		int n = spyGoGame.getSize();
		boolean[][] visited = new boolean[n][n];
		Stone[][] board = spyGoGame.getBoard();
		boolean actual = GoUtils.captured(board, visited, W, 1, 2);
		
		assertFalse(actual);
	}
	
	@Test
	public void test_Captured_ReturnsFalse_GivenAGroupThatStillHasLibertyButHasACapturedGroupCloseby() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,B,W,B,E,E,E,E}, // 0
			{E,B,W,B,E,E,E,E,E}, // 1
			{E,E,E,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		int n = spyGoGame.getSize();
		boolean[][] visited = new boolean[n][n];
		Stone[][] board = spyGoGame.getBoard();
		boolean actual = GoUtils.captured(board, visited, W, 1, 2);
		
		assertFalse(actual);
	}
	
	@Test
	public void test_Captured_ReturnsFalse_GivenAGroupThatStillHasLibertyButHasACapturedGroupCloseby2() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,W,B,E,E,E,E}, // 2
			{E,E,E,B,W,B,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		int n = spyGoGame.getSize();
		boolean[][] visited = new boolean[n][n];
		Stone[][] board = spyGoGame.getBoard();
		boolean actual = GoUtils.captured(board, visited, W, 3, 4);
		
		assertFalse(actual);
	}
	
	@Test
	public void test_Captured_ReturnsFalse_GivenAStoneAtFringeOfCapturedGroup() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,B,E,E,E,E,E,E}, // 0
			{E,E,W,B,E,E,E,E,E}, // 1
			{E,E,B,E,B,E,E,E,E}, // 2
			{E,E,B,E,B,E,E,E,E}, // 3
			{E,E,E,B,W,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		int n = spyGoGame.getSize();
		boolean[][] visited = new boolean[n][n];
		Stone[][] board = spyGoGame.getBoard();
		boolean actual = GoUtils.captured(board, visited, W, 4, 4);
		
		assertFalse(actual);
	}
	
	@Test
	public void test_RemoveCaptured_ReplacesCapturedOneSizeGroupsWithEmptyStones_AfterAPlacement() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,W,B,E,E,E,E}, // 2
			{E,E,E,B,W,B,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		GoUtils.removeCaptured(board, 2, 3);
		
		Stone[][] expectedBoard = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,W,B,E,E,E,E}, // 2
			{E,E,E,B,W,B,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		};
		
		assertEquals(GoUtils.toString(expectedBoard), GoUtils.toString(board));
	}
	
	@Test
	public void test_RemoveCaptured_ReplacesCapturedTwoSizeGroupsWithEmptyStones_AfterAPlacement() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,W,B,E,E,E,E}, // 2
			{E,E,B,W,B,E,E,E,E}, // 3
			{E,E,E,B,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		GoUtils.removeCaptured(board, 2, 3);
		
		Stone[][] expectedBoard = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,E,B,E,E,E,E}, // 2
			{E,E,B,E,B,E,E,E,E}, // 3
			{E,E,E,B,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		};
		
		assertEquals(GoUtils.toString(expectedBoard), GoUtils.toString(board));
	}
	
	@Test
	public void test_RemoveCaptured_ReplacesCapturedTwoSizeGroupsWithEmptyStonesOnly_AfterAPlacement() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,B,E,E,E,E,E,E}, // 0
			{E,E,W,B,E,E,E,E,E}, // 1
			{E,E,B,W,B,E,E,E,E}, // 2
			{E,E,B,W,B,E,E,E,E}, // 3
			{E,E,E,B,W,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		GoUtils.removeCaptured(board, 2, 3);
		
		Stone[][] expectedBoard = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,B,E,E,E,E,E,E}, // 0
			{E,E,W,B,E,E,E,E,E}, // 1
			{E,E,B,E,B,E,E,E,E}, // 2
			{E,E,B,E,B,E,E,E,E}, // 3
			{E,E,E,B,W,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		};
		
		assertEquals(GoUtils.toString(expectedBoard), GoUtils.toString(board));
	}
	
	@Test
	public void test_Remove_RepplacesAStoneWithEmpty_GivenAStoneThatIsSurroundedByFourStonesOfOppositeColors() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,W,B,E,E,E,E}, // 2
			{E,E,B,W,B,E,E,E,E}, // 3
			{E,E,E,B,W,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		int n = board.length;
		boolean[][] visited = new boolean[n][n];
		
		GoUtils.remove(board, visited, W, 2, 3);
		
		Stone[][] expected = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,E,B,E,E,E,E}, // 2
			{E,E,B,E,B,E,E,E,E}, // 3
			{E,E,E,B,W,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		};
		
		assertTrue(GoUtils.compareBoard(expected, board));
	}
	
	@Test
	public void test_CountTerritory_ReturnsNumOfEmptyPositionsSurroundedByASingleColor_GivenBlackAndBoard() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,E,B,E,E,E,E}, // 2
			{E,E,B,E,B,E,E,E,E}, // 3
			{E,E,E,B,W,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		int actualTerritoryCount = GoUtils.countTerritory(board, B);

		assertEquals(2, actualTerritoryCount);
	}
	
	@Test
	public void test_CountTerritory_ReturnsNumOfEmptyPositionsSurroundedByAWallAndASingleColor_GivenWhiteAndBoard() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,B,E,E,E,E,E}, // 1
			{E,E,B,E,B,E,E,E,W}, // 2
			{E,E,B,E,B,E,E,W,E}, // 3
			{E,E,E,B,W,E,E,W,E}, // 4
			{E,E,E,E,E,E,E,W,E}, // 5
			{E,E,E,E,E,E,E,E,W}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		int actualTerritoryCount = GoUtils.countTerritory(board, W);

		assertEquals(3, actualTerritoryCount);
	}
	
	@Test
	public void test_CountTerritory_ReturnsNumOfEmptyPositionsSurroundedByAWallAndASingleColor_GivenWhiteAndBoardMultipleTerritory() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,W,W,E,E}, // 0
			{E,E,E,B,W,E,E,W,E}, // 1
			{E,E,B,E,B,W,W,W,W}, // 2
			{E,E,B,E,B,E,W,W,E}, // 3
			{E,E,E,B,W,E,E,W,E}, // 4
			{E,E,E,E,E,E,E,W,E}, // 5
			{E,E,E,W,E,E,E,E,W}, // 6
			{E,E,W,E,W,E,E,E,E}, // 7
			{E,E,E,W,E,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		int actualTerritoryCount = GoUtils.countTerritory(board, W);

		assertEquals(9, actualTerritoryCount);
	}
	
	@Test
	public void test_CountTerritory_DoesNotCountIfGroupHasMoreThanOneLiberty() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,E}, // 1
			{E,E,E,E,W,E,E,E,E}, // 2
			{E,E,E,W,E,W,E,E,E}, // 3
			{E,E,E,W,E,W,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,B,E,E,E,E}, // 6
			{E,E,E,B,E,B,E,E,E}, // 7
			{E,E,E,E,B,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		int actualTerritoryCount = GoUtils.countTerritory(board, W);

		assertEquals(0, actualTerritoryCount);
	}
	
	@Test
	public void test_CountTerritory_DoesCountIfOccupiedGroupAtCorner() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,W,E,E,E,E,E,E,E}, // 0
			{W,E,E,E,E,E,E,E,E}, // 1
			{E,E,E,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,B,E,E,E,E}, // 6
			{E,E,E,B,E,B,E,E,E}, // 7
			{E,E,E,E,B,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		int actualTerritoryCount = GoUtils.countTerritory(board, W);

		assertEquals(1, actualTerritoryCount);
	}
	
	@Test
	public void test_CountTerritory_DoesNotCountIfGroupSurroundedByMoreThanOneColor() {
		Game goGame = new Game();		
		Game spyGoGame = spy(goGame);
		
		when(spyGoGame.getBoard()).thenReturn(new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,W}, // 1
			{E,E,E,E,E,E,E,W,E}, // 2
			{E,E,E,E,E,E,E,E,W}, // 3
			{E,E,E,E,B,E,E,E,E}, // 4
			{E,E,E,B,E,B,E,E,E}, // 5
			{E,E,B,E,W,E,B,E,E}, // 6
			{E,E,E,B,E,B,E,E,E}, // 7
			{E,E,E,E,B,E,E,E,E}, // 8
		});
		
		Stone[][] board = spyGoGame.getBoard();
		int actualTerritoryCount = GoUtils.countTerritory(board, B);

		assertEquals(0, actualTerritoryCount);
	}
	
	@Test
	public void test_GenerateGoGame_CreatesAGoGameAndDoASeriesOfPlacements_GivenKifu() {
		int[][] kifu = {
				{3,5},{4,3},{5,4},{5,3},{6,3},{6,4},
				{5,5},{6,2},{7,3},{3,4},{2,5},{7,4},
				{7,2},{6,6},{6,5},{7,5},{5,6},{7,1},
				{5,2},{6,1},{3,3},{3,2},{2,3},{4,2},
				{7,6},{7,7},{6,7},{8,6},{6,6},{8,7},
				{2,2},{4,4},{2,4},{2,1},{1,1},{3,1},
				{7,8},{8,2},{1,0},{4,5},{4,6},{2,0},
		};
		
		Stone[][] actual = GoUtils.generateGoGame(kifu).getBoard();
		
		Stone[][] expected = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{B,B,E,E,E,E,E,E,E}, // 1
			{W,W,B,B,B,B,E,E,E}, // 2
			{E,W,W,B,W,B,E,E,E}, // 3
			{E,E,W,W,W,W,B,E,E}, // 4
			{E,E,B,W,B,B,B,E,E}, // 5
			{E,W,W,B,W,B,B,B,E}, // 6
			{E,W,B,B,W,W,B,W,B}, // 7
			{E,E,W,E,E,E,W,W,E}, // 8
		};
		
		assertTrue(GoUtils.compareBoard(expected, actual));
	}
}