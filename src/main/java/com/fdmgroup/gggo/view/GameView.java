package com.fdmgroup.gggo.view;

import java.util.Scanner;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.GoUtils;

public class GameView {

	public void main() {
		
		Scanner in = new Scanner(System.in);
		Game game = new Game();
		GoCommandHandler cmdHandler = new GoCommandHandler(game);
		
		print(welcome());
		
		while (!game.isFinished()) {
			
			print(GoUtils.toString(game.getBoard()));
			print(console());
			String input = in.nextLine();
			
			switch (input) {
				case "pass":
					cmdHandler.handlePass();
					break;
				case "resign":
					cmdHandler.handleResign();
					break;
				case "back":
					cmdHandler.handleBack();
					break;
				case "first":
					cmdHandler.handleJumpToFirst();
					break;
				case "next":
					cmdHandler.handleForward();
					break;
				case "last":
					cmdHandler.handleJumpToLast();
					break;
				case "exit":
					return;
				case "help":
					print(help());
					break;
				default:
					cmdHandler.handlePlacement(input);
			}
		}
	}

	String console() {
		return "GGGo:Game> ";
	}

	private void print(String msg) {
		System.out.print(msg);
	}

	String welcome() {
		return help();
	}

	String help() {
		return new StringBuilder()
				.append("GGGo:Game\n")
				.append("> [ROW],[COL]: place stone at (ROW,COL) e.g. '4,4'\n")
				.append("> back: go back to previous state of the game, if any\n")
				.append("> next: go to next state of the game, if any\n")
				.append("> first: go back to first state of the game\n")
				.append("> last: go last played played state of the game\n")
				.append("> resign: resign the game\n")
				.append("> pass: pass the turn to opponent\n")
				.append("> help: show this message\n")
				.append("> exit the game, resulting in auto-resignation\n")
				.toString();				
	}
}
