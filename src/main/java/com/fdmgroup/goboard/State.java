package com.fdmgroup.goboard;

public class State {
	Stone[][] board;
	
	public State(Stone[][] b) {
		board = b;
	}
	
	public Stone[][] getBoard() {
		return board;
	}
	
	public boolean equals(State os) {
		int n = board.length;
		Stone[][] ob = os.getBoard();
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (ob[i][j] != board[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}
}
