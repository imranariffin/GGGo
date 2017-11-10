package com.fdmgroup.goboard;

import static com.fdmgroup.goboard.Stone.E;
import static com.fdmgroup.goboard.Stone.H;

public class GoUtils {
	
	private static final String EMPTY = "┼"; // Empty
	private static final String WHITE = "●"; // White
	private static final String BLACK = "○"; // Black
	private static final String HOSHI = "╬"; // Hoshi (star)
	
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
	
	public static String toString(Stone[][] board) {
		StringBuilder sb = new StringBuilder();
		sb.append(getHeaderString());
		sb.append(getBodyString(board));
		sb.append(getFooterString());
		return sb.toString();
	}
	
	public static boolean compareBoard(Stone[][] b1, Stone[][] b2) {
		if (b1 == null && b2 == null) {
			return true;
		}
		if (b1 == null || b2 == null) {
			return false;
		}
		if (b1.length != b2.length) {
			return false;
		}
		
		int n = b1.length;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (b1[i][j] != b2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	static void remove(Stone[][] b, boolean[][] v, Stone stone, int i, int j) {
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
//			{i - 1, j - 1}, {i + 1, j + 1}, 
//			{i + 1, j - 1}, {i - 1, j + 1},
		};
		
		for (int[] side: sides) {
			int ii = side[0];
			int jj = side[1];
			remove(b, v, stone, ii, jj);
		}
	}

	static boolean captured(Stone[][] b, boolean[][] v, Stone stone, int i, int j) {
		
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

	private static String getBodyString(Stone[][] board) {
		int size = board.length;
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<size; i++) {
			if (i < 9) {
				sb.append("║  " + (i + 1) + " ");
			} else {
				sb.append("║ " + (i + 1) + " ");
			}
			for (int j=0; j<size; j++) {
				Stone stone = board[i][j];
				if (i == 0 && j == 0) {
					sb.append(stone == E ? "┌─" : toString(stone) + "─");
				} else if (i == 0 && j == size - 1) {
					sb.append(stone == E ? "┐" : toString(stone));
				} else if (i == size - 1 && j == 0) {
					sb.append(stone == E ? "└─" : toString(stone) + "─");
				} else if (i == size - 1 && j == size - 1) {
					sb.append(stone == E ? "┘" : toString(stone));
				} else if (isHoshi(i, j)) {
					sb.append(stone == E? toString(H) + "─": toString(stone) + "─");
				} else if (i == 0) {
					sb.append(stone == E ? "┬─" : toString(stone) + "─");
				} else if (i == size - 1) {
					sb.append(stone == E ? "┴─" : toString(stone) + "─");
				} else if (j == 0) {
					sb.append(stone == E ? "├─" : toString(stone) + "─");
				} else if (j == size - 1) {
					sb.append(stone == E ? "┤" : toString(stone));
				} else {
					sb.append(stone == E ? "┼─" : toString(stone) + "─");
				}
			}
			sb.append("   ║\n");
		}
		return sb.toString();
	}
	
	private static String toString(Stone stone) { 
		switch (stone) {
			case E: return EMPTY;
			case B: return BLACK;
			case W: return WHITE;
			case H: return HOSHI;
			default: return null;
		}
	}

	private static String getHeaderString() {
		return 				
				"╔════════════════════════╗\n"+
				"║    A B C D E F G H J   ║\n";
	}
	
	private static String getFooterString() {
		return 
				"║     ---  GGGo  ---     ║\n"+
				"╚════════════════════════╝\n";
	}
	
	private static boolean isHoshi(int i, int j) {
		return 
				(i == 2 || i == 6) &&
				(j == 2 || j == 6) || 
				(i == 4 && j == 4);
	}

	public static int countTerritory(Stone[][] board, Stone b) {
		return 2;
	}
}
