package com.fdmgroup.goclient;

import com.fdmgroup.goboard.GoGame;
import com.fdmgroup.goboard.Stone;

public class GoClient {
	private static GoGame goGame;
	public static void main(String[] args) {
		goGame = new GoGame();
		GoClientConsole console = new GoClientConsole(goGame, System.in, System.out);
		
		console.welcome();
		while (!goGame.isFinished()) {
			String input = console.console();
			
			switch (input) {
				case "exit": exit(console);
				case "pass": handlePass(); break;
				case "resign": handleResign(); break;
				default: handlePlacement(input);
			}
			
			if (input.equals("exit")) {
				break;
			}
		}
		
		exit(console);
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
			System.out.print("Invalid placement\n"); 
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
