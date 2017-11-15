package com.fdmgroup.goclient;

import com.fdmgroup.goboard.GoFinishedGame;
import com.fdmgroup.goboard.GoGame;
import com.fdmgroup.goboard.GoUtils;
import static com.fdmgroup.goboard.Stone.B;
import static com.fdmgroup.goboard.Stone.W;

public class GoClient {
	private static String MAIN = "MAIN";
	private static String GAME ="GAME";
	private static String HISTORY = "HISTORY";
	private static GoGame goGame;
	private static GoFinishedGame pastGame;
	private static GoClientConsole console;
	private static GoCommandHandler goCmdHandler;
	private static PastGoCommandHandler pgCmdHandler;
	
	public GoClient(GoGame gg) {
		goGame = gg;
		goCmdHandler = new GoCommandHandler(goGame);
	}
	
	public static void main(String[] args) {
		goGame = new GoGame();
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
		pgCmdHandler = new PastGoCommandHandler(pastGame);
		
		while (true) {
			String input = console.console();
			switch (input) {
				case "back": pgCmdHandler.handleBack(); break;
				case "forward": pgCmdHandler.handleForward(); break;
				case "first": pgCmdHandler.handleJumpToFirst(); break;
				case "last": pgCmdHandler.handleJumpToLast(); break;
				default: help();
			}
		}
	}

	private static void startGame() {
		console = getConsole(GAME);
		goCmdHandler = new GoCommandHandler(goGame);
		
		while (!goGame.isFinished()) {
			String input = console.console();
			switch (input) {
				case "pass": goCmdHandler.handlePass(); break;
				case "resign": goCmdHandler.handleResign(); break;
				case "back": goCmdHandler.handleBack(); break;
				case "forward": goCmdHandler.handleForward(); break;
				case "first": goCmdHandler.handleJumpToFirst(); break;
				case "last": goCmdHandler.handleJumpToLast(); break;
				case "exit": return;
				case "help": help();
				default: goCmdHandler.handlePlacement(input);
			}
		}
		
		int wscore = GoUtils.countTerritory(goGame.getBoard(), W);
		int bscore = GoUtils.countTerritory(goGame.getBoard(), B);
		pastGame = new GoFinishedGame(goGame, wscore, bscore);
	}
	
	static GoClientConsole getConsole(String whichConsole) {
		switch (whichConsole) {
			case "MAIN": return MainConsole.getInstance(goGame);
			case "GAME": return GameConsole.getInstance(goGame);
			case "HISTORY": return HistoryConsole.getInstance(pastGame);
			default: throw new Error();
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
}
