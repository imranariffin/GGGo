package com.fdmgroup.gggo.view;

import java.util.Scanner;

public class MainView {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		AuthView authView = new AuthView();
		GameView gameView = new GameView();
		PastGameListView pgListView = new PastGameListView();
		MainView mainView = new MainView();
		
		System.out.println(mainView.welcome());
		
		while (true) {
			mainView.print(mainView.console());
			
			String input = in.nextLine();
			boolean ret = false;
			switch (input) {
				case "signup": 
					ret = authView.main(); 
					break;
				case "login": authView.main(); break;
				case "help": mainView.print(mainView.help()); break;
				case "play": gameView.main(); break;
//				case "review": pgListView.main(); break;
				case "exit": return;
				default: mainView.print(mainView.help());
			}
		}
	}
	
	private void print(String msg) {
		System.out.print(msg);
	}
	
	String help() {
		return new StringBuilder()
				.append("GGGo:\n")
				.append("> signup: proceed to authentication page\n")
				.append("> login: proceed to authentication page\n")
				.append("> play: proceed to play a game\n")
				.append("> review: proceed to view past games\n")
				.append("> help: show this page\n")
				.toString();
	}

	String console() {
		return new StringBuilder()
				.append("GGGo> ")
				.toString();
	}

	String welcome() {
		String welcomeMsg = new StringBuilder()
				.append("# ----- GGGO v0.1 ----- #\n")
				.append("Welcome to Good Game Go (GGGo), A 2-player Go Client on terminal!\n")
				.append("Please enjoy the game, yoroshiku onegaishimaaasu...!\n")
				.append("\n")
				.append("╔════════════════════════════════════════════╗\n")
				.append("║    A B C D E F G H J K L M N O P Q R S T   ║\n")
				.append("║  1 ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐   ║\n")
				.append("║  2 ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤   ║\n")
				.append("║  3 ├─┼─╬─●─●─●─●─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─┤   ║\n")
				.append("║  4 ├─┼─●─●─●─●─●─●─┼─┼─┼─┼─●─●─●─●─●─┼─┤   ║\n")
				.append("║  5 ├─●─●─●─╬─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  6 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  7 ├─●─●─●─┼─┼─╬─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  8 ├─●─●─●─┼─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  9 ├─●─●─●─┼─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 10 ├─●─●─●─┼─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 11 ├─●─●─●─┼─┼─●─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 12 ├─●─●─●─┼─●─●─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 13 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 14 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 15 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 16 ├─┼─●─●─●─●─●─●─┼─┼─┼─┼─●─●─●─●─●─┼─┤   ║\n")
				.append("║ 17 ├─┼─┼─●─●─●─●─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─┤   ║\n")
				.append("║ 18 ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤   ║\n")
				.append("║ 19 └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘   ║\n")
				.append("║               ---  GGGo  ---               ║\n")
				.append("╚════════════════════════════════════════════╝\n")
				.append("\n")
				.toString();
		return welcomeMsg;
	}
}
