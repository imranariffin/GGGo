package com.fdmgroup.gggo.controller;

import static com.fdmgroup.gggo.controller.Stone.E;

import java.util.Arrays;

import com.fdmgroup.gggo.model.PersistentState;

public class State {
	private final int stateId;
	private static final int DEFAULT_SIZE = 9;
	private final int turnNumber;
	Stone[][] board;

	public State(PersistentState ps) {
		stateId = ps.getStateId();
		turnNumber = ps.getTurnNumber();
		board = GoUtils.createBoardFromPlacementList(ps.getPlacements());
	}
	
	public State() {
		this(new Stone[DEFAULT_SIZE][DEFAULT_SIZE], -1, -1);
		
		for (int i = 0; i < DEFAULT_SIZE; i++) {
			for (int j = 0; j < DEFAULT_SIZE; j++) {
				board[i][j] = E;
			}
		}
	}
	
	public State(Stone[][] b, int t) {
		this(b, t, -1);
	}
	
	public State(Stone[][] b, int t, int stid) {
		board = b;
		turnNumber = t;
		stateId = stid;
	}
	
//	public void setBoard(Stone[][] b) {
//		int size = b.length;
//		board = new Stone[size][size];
//		
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				board[i][j] = b[i][j];
//			}
//		}
//	}

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
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + stateId;
		result = prime * result + turnNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (stateId != other.stateId)
			return false;
		if (turnNumber != other.turnNumber)
			return false;
		return true;
	}

	public int getTurn() {
		return turnNumber;
	}

	public int getStateId() {
		return stateId;
	}

	@Override
	public String toString() {
		return "State [turnNumber=" + turnNumber + ", stateId=" + stateId + "]";
	}
}