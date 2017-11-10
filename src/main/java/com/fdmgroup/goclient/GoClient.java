package com.fdmgroup.goclient;

import com.fdmgroup.goboard.GoGame;

public class GoClient {
	private static GoGame goGame;
	public static void main(String[] args) {
		goGame = new GoGame();
		GoClientConsole console = new GoClientConsole(goGame, System.in, System.out);
		GoCommandHandler cmdHandler = new GoCommandHandler(goGame);
		console.welcome();
		
		while (!goGame.isFinished()) {
			String input = console.console();
			
			switch (input) {
				case "pass": cmdHandler.handlePass(); break;
				case "resign": cmdHandler.handleResign(); break;
				case "back": cmdHandler.handleBack(); break;
				case "forward": cmdHandler.handleForward(); break;
				case "first": cmdHandler.handleJumpToFirst(); break;
				case "last": cmdHandler.handleJumpToLast(); break;
				case "exit": exit(console);
				default: cmdHandler.handlePlacement(input);
			}
			
			if (input.equals("exit")) {
				break;
			}
		}
		
		exit(console);
	}

	private static void exit(GoClientConsole console) {
		String winner = goGame.getWinner();
		console.out(winner + " wins by X");
		console.out("Bye Bye!\n");
		System.exit(0);
	}
}
