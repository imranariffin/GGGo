package com.fdmgroup.gggo.controller;

import static com.fdmgroup.gggo.controller.Stone.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.PlacementDAO;
import com.fdmgroup.gggo.dao.StateDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.exceptions.InvalidPlacementException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;
import com.fdmgroup.gggo.model.User;

public class GoUtilsTest {

	private static UserDAO udao;
	private static InviteDAO idao;
	private static GameDAO gdao;
	private static StateDAO sdao;
	
	private User invitor;
	private User invitee;
	private Invite invite;
	private Game game;
	
	@BeforeClass
	public static void setupOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
		gdao = DAOFactory.getPersistentGameDAO();
		sdao = DAOFactory.getPersistentStateDAO();
		
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
	}
	
	@Before
	public void setup() {
		String password = "pazzword";
		invitor = udao.createUser("invitor", password);
		invitee = udao.createUser("invitee", password);
		invite = idao.createInvite(invitor, invitee);
		game = gdao.createGame(invite);
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser(invitor.getUsername());
		udao.deleteUser(invitee.getUsername());
	}
	
	@Test
	public void test_ToString_ReturnsCorrectEmptyBoardString_GivenEmptyBoard() {
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		
		String actual = GoUtils.toString(goGame.getBoard());
		assertEquals(expected, actual);
	}

	
	@Test
	public void test_ToString_ReturnsCorrectBoardString_GivenOnePlacement() {
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		Invite invite = idao.createInvite(invitor, invitee);
		Game goGame = gdao.createGame(invite);
		
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
		
		System.out.println(GoUtils.toString(actual));
		System.out.println(GoUtils.toString(expected));
		assertTrue(GoUtils.compareBoard(expected, actual));
	}
	
	@Test
	public void test_CreateBoardFromPlacementList_ReturnsEmptyBoard_GivenEmptyListOfPlacements() {
		List<Placement> placements = new ArrayList<>();
		
		Stone[][] expected = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,E}, // 1
			{E,E,E,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		};
		Stone[][] actual = GoUtils.createBoardFromPlacementList(placements);
		assertTrue(GoUtils.compareBoard(expected, actual));		
	}
	
	@Test
	public void test_CreateBoardFromPlacementList_ReturnsBoard_GivenListOfPlacements() {
		
		Placement pt1 = Mockito.mock(Placement.class);
		Placement pt2 = Mockito.mock(Placement.class);
		
		/* Black san-san top left then White replies diagonal san-san */
		Mockito.when(pt1.getRowNumber()).thenReturn(2);
		Mockito.when(pt1.getColNumber()).thenReturn(2);
		Mockito.when(pt1.getStone()).thenReturn(B);
		Mockito.when(pt2.getRowNumber()).thenReturn(6);
		Mockito.when(pt2.getColNumber()).thenReturn(6);
		Mockito.when(pt2.getStone()).thenReturn(W);
		
		List<Placement> placements = new ArrayList<>();
		
		placements.add(pt1);
		placements.add(pt2);
		
		Stone[][] expected = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,E}, // 1
			{E,E,B,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,W,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		};
		Stone[][] actual = GoUtils.createBoardFromPlacementList(placements);
		assertTrue(GoUtils.compareBoard(expected, actual));
	}
	
	@Test
	public void test_CreateBoardFromPersistentState_ReturnsBoard_GivenEmptyPersistentState() {
		PersistentState ps = sdao.createEmptyPersistentState(game.getGameId());
		
		Stone[][] expected = new Stone[][] {
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,E}, // 1
			{E,E,E,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8			
		};
		Stone[][] actual = GoUtils.createBoardFromPersistentState(ps);
		
		assertTrue(Arrays.deepEquals(expected, actual));
	}
	
	@Test
	public void test_CreateBoardFromPersistentState_ReturnsBoard_GivenPersistentState() {
		PersistentState ps1 = sdao.createPersistentState(game.getGameId(), 2, 2, 0, B);
		PersistentState ps2 = sdao.createPersistentState(game.getGameId(), 6, 6, 1, W);
		PersistentState ps3 = sdao.createPersistentState(game.getGameId(), 2, 6, 2, B);
		
		Stone[][] expected = new Stone[][] {
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,E}, // 1
			{E,E,B,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8			
		};
		Stone[][] actual = GoUtils.createBoardFromPersistentState(ps1);
		
		assertTrue(Arrays.deepEquals(expected, actual));
		
		expected = new Stone[][] {
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,E}, // 1
			{E,E,B,E,E,E,E,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,W,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8			
		};
		actual = GoUtils.createBoardFromPersistentState(ps2);
		
		assertTrue(Arrays.deepEquals(expected, actual));
		
		expected = new Stone[][] {
			{E,E,E,E,E,E,E,E,E}, // 0
			{E,E,E,E,E,E,E,E,E}, // 1
			{E,E,B,E,E,E,B,E,E}, // 2
			{E,E,E,E,E,E,E,E,E}, // 3
			{E,E,E,E,E,E,E,E,E}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,W,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8			
		};
		actual = GoUtils.createBoardFromPersistentState(ps3);
		
		assertTrue(Arrays.deepEquals(expected, actual));
	}
}