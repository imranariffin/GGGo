package com.fdmgroup.gggo.view;

import java.io.InputStream;
import java.io.PrintStream;

import com.fdmgroup.gggo.model.Game;

public class MainConsole extends Console {
	private static MainConsole inst;
	private MainConsole(InputStream is, PrintStream os) {
		super(is, os);
	}
	
	@Override
	public String console() {
		out.print("@Menu > ");
		return in.nextLine();
	}
	
	@Override
	public void welcome() {
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
			.append("║  7 ├─●─●─●─┼─┼─●─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
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
		
		out(welcomeMsg);
	}

	public static Console getInstance() {
		if (inst == null) {
			inst = new MainConsole(System.in, System.out);
		}
		return inst;
	}
}