package com.fdmgroup.goclient;

import com.fdmgroup.goboard.EndOfStateStackException;
import com.fdmgroup.goboard.GoGame;
import com.fdmgroup.goboard.InvalidPlacementException;

public class GoCommandHandler extends CommandHandler {

	private GoGame goGame;
	
	public GoCommandHandler(GoGame gg) {
		goGame = gg;
	}

	public void handleJumpToLast() {
		goGame.jumpToLast();
	}

	public void handleJumpToFirst() {
		goGame.jumpToFirst();
	}

	public void handleForward() {
		try {
			goGame.forward();
		} catch (EndOfStateStackException eosse) {
			System.out.println(eosse.getMessage());
		}
	}

	public void handleBack() {
		try {
			goGame.back();
		} catch (EndOfStateStackException eosse) {
			System.out.println(eosse.getMessage());
		}
	}

	public void handleResign() {
		goGame.resign();		
	}

	public void handlePass() {
		goGame.pass();		
	}

	public void handlePlacement(String input) {
		if (input == null || input.length() <= 1 ||  input.split(",").length != 2) {
			System.out.print("Invalid command!\n");
			
			return;
		}
		
		String[] pos = input.split(",");
		int i = Integer.parseInt(pos[0]) - 1; 
		int j = Integer.parseInt(pos[1]) - 1;
		
		try {
			goGame.place(i, j);
		} catch (InvalidPlacementException ipe) {
			System.out.println(ipe.getMessage());
		}
	}
}
