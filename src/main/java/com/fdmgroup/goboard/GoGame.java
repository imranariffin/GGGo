package com.fdmgroup.goboard;

import java.util.Stack;

public class GoGame extends Go implements PlayableGo {
	
	private int size;
	private boolean passed;
	private String[][] board;
	private boolean finished;
	Stack<State> states = new Stack<State>();
	Stack<State> futureStates = new Stack<State>();
	
	public GoGame() {
		size = 9;
		board = new String[size][size];
		
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				board[i][j] = "E";
			}
		}
	}
	
	@Override
	public String getWinner() {
		return null;
	}

	@Override
	public boolean place(int i, int j) {
		String stone = getCurStone();
		passed = false;
		
		if (!isValid(i, j)) {
			return false;
		}
		
		board[i][j] = stone;
		states.push(new State());
		
		return true;
	}

	@Override
	public void pass() {;
		if (passed) {
			System.out.println("Calculating territories ...");
			System.out.println("X wins by Y points!");
			finish();
		}
		
		states.push(new State());
		passed = true;
	}

	@Override
	public void resign() {
		String stone = getCurStone();
		System.out.println(stone + " resigned!");
		finish();
	}

	public boolean isFinished() {
		return finished;
	}
	
	public int getTurn() {
		return states.size();
	}

	public String getStone(int i, int j) {
		return board[i][j];
	}

	public int getSize() {
		return size;
	}
	
	public void back() throws EmptyStateStackException {
		if (states.size() == 0) {
			String errMsg = "PlayableGo.back(): State stack is empty -- no previous state available!";
			throw new EmptyStateStackException(errMsg);
		}
		futureStates.push(states.pop());
	}

	public void next() throws EmptyStateStackException {
		if (futureStates.size() == 0) {
			String errMsg = "Playable.next(): End of state stack -- no future state available!";
			throw new EmptyStateStackException(errMsg);
		}
		states.push(futureStates.pop());
	}

	public void jumpToFirst() {
		// TODO Auto-generated method stub
	}

	public void jumpToLast() {
		// TODO Auto-generated method stub
		
	}
	
	private String getCurStone() {
		return getTurn() % 2 == 0 ? "B" : "W";
	}

	boolean isValid(int i, int j) {
		return board[i][j].equals("E");
	}
	
	private void finish() {
		finished = true;
		System.out.println("Game finished");
	}
}
