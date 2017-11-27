package com.fdmgroup.goclient;

import java.io.InputStream;
import java.io.PrintStream;

import com.fdmgroup.goboard.GoFinishedGame;
import com.fdmgroup.goboard.GoUtils;

public class PastGameConsole extends Console {
	private static PastGameConsole inst;
	private GoFinishedGame pastGame;
	
	public static PastGameConsole getInstance(GoFinishedGame pg) {
		if (inst == null) {
			return new PastGameConsole(pg, System.in, System.out);
		}
		return inst;
	}
	
	private PastGameConsole (GoFinishedGame pg, InputStream is, PrintStream os) {
		super(is, os);
		pastGame = pg;
	}

	@Override
	public String console() {
		String stone = pastGame.getTurn() % 2 == 0 ? "B" : "W";
		out.print(GoUtils.toString(pastGame.getBoard()));
		out.print("@History: " + stone + "> ");
		return in.nextLine();
	}

	@Override
	public void welcome() {
		out.print("View past games here\n");
	}

}
