package com.fdmgroup.goboard;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.fdmgroup.goboard.Stone.W;
import static com.fdmgroup.goboard.Stone.B;
import static com.fdmgroup.goboard.Stone.E;

public class GoFinishedGameTest {
	GoFinishedGame pastGame;
	GoGame goGame;
	
	@Test
	public void test_GetWinner_ReturnsWhiteStone_IfWhiteHasHigherScore() {
		int[][] kifu = {
			{4,0},{0,7},{3,1},{1,7},{2,2},{2,7},
			{1,3},{3,7},{0,4},{4,8}
		};
		goGame = GoUtils.generateGoGame(kifu);
		Stone[][] actual = new Stone[][] {
//			 0 1 2 3 4 5 6 7 8
			{E,E,E,E,B,E,E,W,E}, // 0
			{E,E,E,B,E,E,E,W,E}, // 1
			{E,E,B,E,E,E,E,W,E}, // 2
			{E,B,E,E,E,E,E,W,E}, // 3
			{B,E,E,E,E,E,E,E,W}, // 4
			{E,E,E,E,E,E,E,E,E}, // 5
			{E,E,E,E,E,E,E,E,E}, // 6
			{E,E,E,E,E,E,E,E,E}, // 7
			{E,E,E,E,E,E,E,E,E}, // 8
		};
		
		assertTrue(GoUtils.compareBoard(actual, goGame.getBoard()));
		
		int bScore = GoUtils.countTerritory(goGame.getBoard(), B);
		int wScore = GoUtils.countTerritory(goGame.getBoard(), W);
		
		assertEquals(10, bScore);
		assertEquals(4, wScore);
		
		pastGame = new GoFinishedGame(goGame, wScore, bScore); 

		assertEquals(W, pastGame.getWinner());
	}
}