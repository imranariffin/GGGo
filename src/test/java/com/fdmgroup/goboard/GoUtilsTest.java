package com.fdmgroup.goboard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.fdmgroup.goboard.Stone.*;

import org.junit.Test;

public class GoUtilsTest {
	
	@Test
	public void test_ToString_ReturnsCorrectEmptyBoardString_GivenEmptyBoard() {
		GoGame newGame = new GoGame();
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
		GoGame goGame = new GoGame();
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
		GoGame goGame = new GoGame();
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
		GoGame goGame = new GoGame();
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

		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
		
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
		GoGame goGame = new GoGame();		
		GoGame spyGoGame = spy(goGame);
		
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
}