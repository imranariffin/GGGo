package com.fdmgroup.goboard;

import static com.fdmgroup.goboard.Stone.E;

public class GoUtils {
	public static void removeCaptured(Stone[][] board, int i, int j) {
		int[][] sides = new int[][] {
			{i - 1, j}, {i + 1, j}, 
			{i, j - 1}, {i, j + 1},
			{i - 1, j - 1}, {i + 1, j + 1}, 
			{i + 1, j - 1}, {i - 1, j + 1},
		};		
		
		for (int[] side: sides) {
			int ii = side[0];
			int jj = side[1];
			if (isValid(board, ii, jj)) {
				int n = board.length;
				boolean[][] visited = new boolean[n][n];
				Stone neighbourStone = board[ii][jj];
				if (captured(board, visited, neighbourStone, ii, jj)) {
					visited = new boolean[n][n];
					remove(board, visited, neighbourStone, ii, jj);
				}	
			}
		}
	}

	private static void remove(Stone[][] b, boolean[][] v, Stone stone, int i, int j) {
		int n = b.length;
		if (i < 0 || i >= n || j < 0 || j >= n) {
			return;
		}
		
		if (v[i][j] || b[i][j] !=stone) {
			return;
		}
		
		b[i][j] = E;
		v[i][j] = true;
		
		int[][] sides = new int[][] {
			{i - 1, j}, {i + 1, j}, 
			{i, j - 1}, {i, j + 1},
			{i - 1, j - 1}, {i + 1, j + 1}, 
			{i + 1, j - 1}, {i - 1, j + 1},
		};
		
		for (int[] side: sides) {
			int ii = side[0];
			int jj = side[1];
			remove(b, v, stone, ii, jj);
		}
	}

	private static boolean captured(Stone[][] b, boolean[][] v, Stone stone, int i, int j) {
		
		if (i < 0 || i >= v.length || j < 0 || j >= v.length) {
			return true;
		}
		
		if (b[i][j] == E) {
			return false;
		}
		
		if (v[i][j]) {
			return true;
		}
		
		if (b[i][j] != stone) {
			return true;
		}
		
		v[i][j] = true;
		
		return 
				captured(b, v, stone, i - 1, j) &&
				captured(b, v, stone, i + 1, j) &&
				captured(b, v, stone, i, j - 1) &&
				captured(b, v, stone, i, j + 1);
	}
	
	private static boolean isValid(Stone[][] b, int i, int j) {
		int n = b.length;
		return i >= 0 && i < n && j >= 0 && j < n;  
	}
}
