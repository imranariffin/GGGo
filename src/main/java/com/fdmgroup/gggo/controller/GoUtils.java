package com.fdmgroup.gggo.controller;

import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.E;
import static com.fdmgroup.gggo.controller.Stone.H;
import static com.fdmgroup.gggo.controller.Stone.W;

import java.util.List;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.exceptions.InvalidPlacementException;
import com.fdmgroup.gggo.model.Placement;

public class GoUtils {
	
	private static final String EMPTY = "┼"; // Empty
	private static final String WHITE = "●"; // White
	private static final String BLACK = "○"; // Black
	private static final String HOSHI = "╬"; // Hoshi (star)
	private static boolean isTerritory;
	
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

	public static Stone getWinner(Stone[][] board) {
		int blackScore = countTerritory(board, B);
		int whiteScore = countTerritory(board, W);
		
		return ((float) blackScore) - 6.5 > (float) whiteScore ? B : W; 
	}
	
	public static int countTerritory(Stone[][] board, Stone stone) {
		int n = board.length;
		boolean[][] visited = new boolean[n][n];
		isTerritory = false;
		return countTerritory(board, stone, visited);
	}
	
	public static Game generateGoGame(int[][] kifu) {
		Game goGame = new Game();
		goGame.states.push(new State());
		for (int[] pos: kifu) {
			int i = pos[0];
			int j = pos[1];
			
			try {
				goGame.place(i, j);
			} catch (InvalidPlacementException e) {
				System.out.println(e.getMessage());
			}
		}
		return goGame;
	}
	
	private static int countTerritory(Stone[][] board, Stone stone, boolean[][] visited) {
		int ret = 0;
		int n = board.length;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == E && !visited[i][j]) {
					// dfs will update isTerritory
					isTerritory = true;
					int r = dfs(board, stone, i, j, visited);
					ret = isTerritory ? ret + r : ret;
				}
			}
		}
		
		return ret;
	}

	private static int dfs(Stone[][] board, Stone stone, int i, int j, boolean[][] visited) {
		if (i < 0 || i >= board.length || j < 0 || j >= board.length) {
			return 0;
		}
		if (visited[i][j]) {
			return 0;
		}
		if (board[i][j] != E && board[i][j] != stone) {
			isTerritory = false;
			return 0;
		}
		if (board[i][j] != E) {
			return 0;
		}
		
		int ret = 1;
		visited[i][j] = true;
		
		ret += dfs(board, stone, i - 1, j, visited);
		ret += dfs(board, stone, i + 1, j, visited);
		ret += dfs(board, stone, i, j - 1, visited);
		ret += dfs(board, stone, i, j + 1, visited);
		
		return ret;
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
	
	public static String toString(Stone stone) {
		if (stone == null) {
			return null;
		}
		
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

	public static Stone toStone(String stoneString) {
		switch (stoneString) {
			case EMPTY: return E;
			case BLACK: return B;
			case WHITE: return W;
			case HOSHI: return H;
			default: return null;
		}
	}

	public static Stone[][] createBoardFromPlacementList(List<Placement> placements) {
		Stone[][] b = new Stone[][] {
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
		
		for (Placement pt: placements) {
			int r = pt.getRowNumber(), c = pt.getColNumber();
			Stone st = pt.getStone();
			b[r][c] = st;
		}
		
		return b;
	}
}
