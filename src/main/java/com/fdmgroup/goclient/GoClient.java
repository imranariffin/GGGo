package com.fdmgroup.goclient;

import com.fdmgroup.goboard.GoGame;

public class GoClient {
	private static String MAIN = "MAIN";
	private static String GAME ="GAME";
	private static String HISTORY = "HISTORY";
	private static GoGame goGame;
	private static GoClientConsole console;
	private static GoCommandHandler cmdHandler;
	
	public GoClient(GoGame gg) {
		goGame = gg;
		cmdHandler = new GoCommandHandler(goGame);
	}
	
	public static void main(String[] args) {
		goGame = new GoGame();
		cmdHandler = new GoCommandHandler(goGame);
		console = getConsole(MAIN);
		
		console.welcome();
		
		while (true) {
			console = getConsole(MAIN);
			String input = console.console();
			switch(input) {
				case "V": viewPastGames(); break;
				case "P": startGame(); break;
				case "exit": exit(); 
				default: help(); break;
			}
		}
	}

	private static void viewPastGames() {
		console = getConsole(HISTORY);
		
		while (true) {
			String input = console.console();
			switch (input) {
				case "back": cmdHandler.handleBack(); break;
				case "forward": cmdHandler.handleForward(); break;
				case "first": cmdHandler.handleJumpToFirst(); break;
				case "last": cmdHandler.handleJumpToLast(); break;
				default: help();
			}
		}
	}

	private static void exit() {
		console.out("Bye Bye!\n");
		System.exit(0);
	}
	
	private static void help() {
		console.out("GoClient v0.1 beta \n");
		console.out("Available commands:\n");
		
		console.out("\n* In main menu:\n");
		console.out("@Menu> P - start a new game\n");
		console.out("@Menu> V - view past games\n");
		console.out("@Menu> exit - exits the game\n");
		
		console.out("\n* During game session:\n");
		console.out("@Game> pass - current player passes\n");
		console.out("@Game> resign - current player resigns\n");
		console.out("@Game> back - go back one step to previous state of board, if any\n");
		console.out("@Game> forward - go forward one step to future state of board, if any\n");
		console.out("@Game> first - go back one step to first state of the board\n");
		console.out("@Game> last - go forward to the latest state of the board\n");
		console.out("@Game> exit - exits to main menu\n");
		
		console.out("\n* During past games review:\n");
		console.out("@History> back - go back one step to previous state of board, if any\n");
		console.out("@History> forward - go forward one step to future state of board, if any\n");
		console.out("@History> first - go back one step to first state of the board\n");
		console.out("@History> last - go forward to the latest state of the board\n");
		console.out("@Game> exit - exits to main menu\n");
	}
	
	private static void startGame() {
		console = getConsole(GAME);
		
		while (!goGame.isFinished()) {
			String input = console.console();
			switch (input) {
				case "pass": cmdHandler.handlePass(); break;
				case "resign": cmdHandler.handleResign(); break;
				case "back": cmdHandler.handleBack(); break;
				case "forward": cmdHandler.handleForward(); break;
				case "first": cmdHandler.handleJumpToFirst(); break;
				case "last": cmdHandler.handleJumpToLast(); break;
				case "exit": return;
				case "help": help();
				default: cmdHandler.handlePlacement(input);
			}
		}	
	}
	
	static GoClientConsole getConsole(String whichConsole) {
		switch (whichConsole) {
			case "MAIN": return MainConsole.getInstance(goGame);
			case "GAME": return GameConsole.getInstance(goGame);
//			case "HISTORY": return HistoryConsole.getInstance();
			default: throw new Error();
		}
	}
}
