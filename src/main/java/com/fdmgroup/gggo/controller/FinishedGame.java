package com.fdmgroup.gggo.controller;

import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.W;

import java.util.Stack;

import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.StateDAO;

public class FinishedGame extends InteractiveGo {
	private final float whiteScore;
	private final float blackScore;
	
	public FinishedGame(Game gg, float wScore, float bScore) {
		SIZE = 9;
		states = new Stack<>();
		futureStates = new Stack<>();
		whiteScore = wScore;
		blackScore = bScore;
		
		StateDAO sdao = DAOFactory.getPersistentStateDAO();
		for (State s: gg.getStates()) {
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
