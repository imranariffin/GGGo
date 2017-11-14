package com.fdmgroup.goboard;

import static com.fdmgroup.goboard.Stone.W;
import static com.fdmgroup.goboard.Stone.B;
import java.util.Stack;

public class GoFinishedGame extends InteractiveGo {
	private final float whiteScore;
	private final float blackScore;
	
	public GoFinishedGame(Stack<State> _states, float wScore, float bScore) {
		SIZE = 9;
		states = new Stack<>();
		futureStates = new Stack<>();
		whiteScore = wScore;
		blackScore = bScore;
		
		for (State s: _states) {
			states.push(s);
		}
	}
	
	public Stone getWinner() {
		float ws = (float) whiteScore;
		float bs = (float) blackScore;
		if (ws + 6.5 > bs) {
			return W;
		}
		return B;
	}
}
