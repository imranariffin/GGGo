package com.fdmgroup.gggo.controller;

import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.E;
import static com.fdmgroup.gggo.controller.Stone.W;

import java.util.Stack;

import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.exceptions.InvalidPlacementException;

public class Game extends InteractiveGo implements Go {
	
	private final int gameId;
	private final int SIZE;
	private boolean passed;
	private boolean finished;
	
	public Game() {
		this(-1);
	}
	
	public Game(int gid) {
		gameId = gid;
		SIZE = 9;
		Stone[][] board = new Stone[SIZE][SIZE];
		states = new Stack<State>();
		futureStates = new Stack<State>();
		
		for (int i=0; i<SIZE; i++) {
			for (int j=0; j<SIZE; j++) {
				board[i][j] = E;
			}
		}
		
//		states.push(new State(board, getNextTurn()));
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
		states.push(new State(newBoard, getNextTurn()));
	}

	@Override
	public void pass() {;
		if (passed) {
			Stone[][] board = getBoard();
			Stone winner = GoUtils.getWinner(board);
			if (board == null) {
				System.out.println("=====\n=====\n====\n");
			}
			finish(winner, (float) GoUtils.countTerritory(board, winner));
			return;
		}
		
		Stone[][] board = getBoard();
		Stone[][] newBoard = copyBoard(board);
		
		states.push(new State(newBoard, getNextTurn()));
		passed = true;
	}

	@Override
	public void resign() {
		finish(getCurStone(), -1);
	}

	public boolean isFinished() {
		return finished;
	}

	public Stone getStone(int i, int j) {
		return getBoard()[i][j];
	}

	public int getSize() {
		return SIZE;
	}
	
	private Stone getCurStone() {
		return getTurn() % 2 == 0 ? B : W;
	}

	boolean isValid(int i, int j) {
		if (i < 0 || i >= SIZE || j < 0 || j >= SIZE) return false;
		return getBoard()[i][j] == E;
	}
	
	private void finish(Stone winner, float score) {
		String w = winner == B ? "B" : "W";
		if (score > 0) {
			System.out.println(w + " + " + score);
		} else {
			System.out.println(w + " + R");
		}
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

	public Stack<State> getStates() {
		return states;
	}

	public int getGameId() {
		return gameId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + SIZE;
		result = prime * result + (finished ? 1231 : 1237);
		result = prime * result + gameId;
		result = prime * result + (passed ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (SIZE != other.SIZE)
			return false;
		if (finished != other.finished)
			return false;
		if (gameId != other.gameId)
			return false;
		if (passed != other.passed)
			return false;
		if (!states.equals(other.states))
			return false;
		if (!futureStates.equals(other.futureStates))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", SIZE=" + SIZE + ", passed=" + passed + ", finished=" + finished
				+ ", states=" + states + ", futureStates=" + futureStates + "]";
	}
}