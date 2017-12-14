package com.fdmgroup.gggo.controller;

import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.W;

import java.util.Stack;

public class FinishedGame extends InteractiveGo {
	private final float whiteScore;
	private final float blackScore;
	
	public FinishedGame(Game gg, float wScore, float bScore) {
		SIZE = 9;
		states = new Stack<>();
		futureStates = new Stack<>();
		whiteScore = wScore;
		blackScore = bScore;
		
		for (State s: gg.getStates()) {
			states.push(new State(s.getBoard(), getNextTurn()));
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
