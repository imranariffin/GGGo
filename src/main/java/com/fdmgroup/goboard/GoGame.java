package com.fdmgroup.goboard;

import static com.fdmgroup.goboard.Stone.E;
import static com.fdmgroup.goboard.Stone.B;
import static com.fdmgroup.goboard.Stone.W;

import java.util.Stack;

public class GoGame extends Go implements PlayableGo {
	
	private final int SIZE;
	private boolean passed;
	private boolean finished;
	Stack<State> states;
	Stack<State> futureStates;
	
	public GoGame() {
		SIZE = 9;
		Stone[][] board = new Stone[SIZE][SIZE];
		states = new Stack<State>();
		futureStates = new Stack<State>();
		
		for (int i=0; i<SIZE; i++) {
			for (int j=0; j<SIZE; j++) {
				board[i][j] = E;
			}
		}
		
		states.push(new State(board));
	}

	@Override
	public String getWinner() {
		return null;
	}

	@Override
	public void place(int i, int j) throws InvalidPlacementException {
		Stone stone = getCurStone();
		passed = false;
		
		if (!isValid(i, j)) {
			throw new InvalidPlacementException();
		}
		
		// removes futures states if any
		if (futureStates.size() > 0) {
			// from this point on, the board will thread a 
			// different path of states different from original one
			futureStates.clear();
		}
		
		Stone[][] newBoard = createNewBoard(i, j, stone);
		GoUtils.removeCaptured(newBoard, i, j);
		states.push(new State(newBoard));
	}

	@Override
	public void pass() {;
		if (passed) {
			finish();
		}
		
		Stone[][] board = getBoard();
		Stone[][] newBoard = copyBoard(board);
		
		states.push(new State(newBoard));
		passed = true;
	}

	@Override
	public void resign() {
		finish();
	}

	public boolean isFinished() {
		return finished;
	}
	
	public int getTurn() {
		return states.size() - 1;
	}

	public Stone getStone(int i, int j) {
		return getBoard()[i][j];
	}

	public int getSize() {
		return SIZE;
	}
	
	public void back() throws EndOfStateStackException {
		if (states.size() == 1) {
			String errMsg = "PlayableGo.back(): State stack reaches its end -- no previous state available!";
			throw new EndOfStateStackException(errMsg);
		}
		futureStates.push(states.pop());
	}

	public void forward() throws EndOfStateStackException {
		if (futureStates.size() == 0) {
			String errMsg = "Playable.next(): State stack reaches its end -- no future state available!";
			throw new EndOfStateStackException(errMsg);
		}
		states.push(futureStates.pop());
	}

	public void jumpToFirst() {
		while (true) {
			try {
				back();
			} catch (EndOfStateStackException eosse) {
				break;
			}
		}
	}

	public void jumpToLast() {
		while (true) {
			try {
				forward();
			} catch (EndOfStateStackException eosse) {
				break;
			}
		}
	}

	public State getCurState() {
		return states.peek();
	}
	
	public Stone[][] getBoard() {
		return getCurState().getBoard();
	}
	
	private Stone getCurStone() {
		return getTurn() % 2 == 0 ? B : W;
	}

	boolean isValid(int i, int j) {
		if (i < 0 || i >= SIZE || j < 0 || j >= SIZE) return false;
		return getBoard()[i][j] == E;
	}
	
	private void finish() {
		finished = true;
	}
	
	Stone[][] createNewBoard(int i, int j, Stone stone) {
		
		// copy current board and update it with new placed stone
		Stone[][] board = getBoard();
		Stone[][] newBoard = copyBoard(board);
		newBoard[i][j] = stone;
		
		return newBoard;
	}
	
	Stone[][] copyBoard(Stone[][] board) {
		Stone[][] newBoard = new Stone[SIZE][SIZE];
		for (int i=0; i<SIZE; i++) {
			System.arraycopy(board[i], 0, newBoard[i], 0, SIZE);
		}
		
		return newBoard;
	}
}
