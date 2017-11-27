package com.fdmgroup.gggo.model;

import static com.fdmgroup.gggo.model.Stone.E;

import java.util.Arrays;

public class State {
	private int turnNumber;
	private static final int DEFAULT_SIZE = 9;
	Stone[][] board;
	private final int stateId;
	
	public State() {
		board = new Stone[DEFAULT_SIZE][DEFAULT_SIZE];
		
		for (int i = 0; i < DEFAULT_SIZE; i++) {
			for (int j = 0; j < DEFAULT_SIZE; j++) {
				board[i][j] = E;
			}
		}
	
		stateId = -1;
		turnNumber = 1;
	}
	
	public State(Stone[][] b, int t) {
		this(b, t, -1);
	}
	
	public State(Stone[][] b, int t, int stid) {
		setBoard(b);
		turnNumber = t;
		stateId = stid;
	}
	
	public void setBoard(Stone[][] b) {
		int size = b.length;
		board = new Stone[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = b[i][j];
			}
		}
	}
	
	public Stone[][] getBoard() {
		int size = board.length;
		Stone[][] b = new Stone[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				b[i][j] = board[i][j];
			}
		}
		
		return b;
	}
	
	public boolean equals(State os) {
		int size = board.length;
		Stone[][] ob = os.getBoard();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (ob[i][j] != board[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}

	public int getTurn() {
		return turnNumber;
	}

	public int getStateId() {
		return stateId;
	}
}