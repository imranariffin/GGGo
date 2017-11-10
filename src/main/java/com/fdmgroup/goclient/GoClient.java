package com.fdmgroup.goclient;

import com.fdmgroup.goboard.EndOfStateStackException;
import com.fdmgroup.goboard.GoGame;

public class GoClient {
	private static GoGame goGame;
	public static void main(String[] args) {
		goGame = new GoGame();
		GoClientConsole console = new GoClientConsole(goGame, System.in, System.out);
		
		console.welcome();
		while (!goGame.isFinished()) {
			String input = console.console();
			
			switch (input) {
				case "pass": handlePass(); break;
				case "resign": handleResign(); break;
				case "back": handleBack(); break;
				case "forward": handleForward(); break;
				case "first": handleJumpToFirst(); break;
				case "last": handleJumpToLast(); break;
				case "exit": exit(console);
				default: handlePlacement(input);
			}
			
			if (input.equals("exit")) {
				break;
			}
		}
		
		exit(console);
	}

	private static void handleJumpToLast() {
		goGame.jumpToLast();
	}

	private static void handleJumpToFirst() {
		goGame.jumpToFirst();
	}

	private static void handleForward() {
		try {
			goGame.forward();
		} catch (EndOfStateStackException eosse) {
			System.out.println(eosse.getMessage());
		}
	}

	private static void handleBack() {
		try {
			goGame.back();
		} catch (EndOfStateStackException eosse) {
			System.out.println(eosse.getMessage());
		}
	}

	private static void handleResign() {
		goGame.resign();		
	}

	private static void handlePass() {
		goGame.pass();		
	}

	private static void handlePlacement(String input) {
		String[] pos = input.split(",");
		if (pos.length != 2) {
			System.out.print("Invalid command!\n"); 
		}
		
		int i = Integer.parseInt(pos[0]) - 1; 
		int j = Integer.parseInt(pos[1]) - 1;
		goGame.place(i, j);
	}

	private static void exit(GoClientConsole console) {
		String winner = goGame.getWinner();
		console.out(winner + " wins by X");
		console.out("Bye Bye!\n");
		System.exit(0);
	}
}
