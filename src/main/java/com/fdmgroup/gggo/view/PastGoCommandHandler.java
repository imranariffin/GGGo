package com.fdmgroup.gggo.view;

import com.fdmgroup.gggo.controller.EndOfStateStackException;
import com.fdmgroup.gggo.model.FinishedGame;

public class PastGoCommandHandler extends CommandHandler {
	private FinishedGame pastGame;
	
	public PastGoCommandHandler(FinishedGame pg) {
		pastGame = pg;
	}
	
	public void handleJumpToLast() {
		pastGame.jumpToLast();
	}

	public void handleJumpToFirst() {
		pastGame.jumpToFirst();
	}

	public void handleForward() {
		try {
			pastGame.forward();
		} catch (EndOfStateStackException eosse) {
			System.out.println(eosse.getMessage());
		}
	}

	public void handleBack() {
		try {
			pastGame.back();
		} catch (EndOfStateStackException eosse) {
			System.out.println(eosse.getMessage());
		}
	}
}