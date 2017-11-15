package com.fdmgroup.goclient;

import com.fdmgroup.goboard.EndOfStateStackException;
import com.fdmgroup.goboard.GoFinishedGame;

public class PastGoCommandHandler extends CommandHandler {
	private GoFinishedGame pastGame;
	
	public PastGoCommandHandler(GoFinishedGame pg) {
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
