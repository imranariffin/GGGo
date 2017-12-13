package com.fdmgroup.gggo.view;

import java.io.InputStream;
import java.io.PrintStream;

import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.controller.FinishedGame;

public class PastGameConsole extends Console {
	private static PastGameConsole inst;
	private FinishedGame pastGame;
	
	public static PastGameConsole getInstance(FinishedGame pg) {
		if (inst == null) {
			return new PastGameConsole(pg, System.in, System.out);
		}
		return inst;
	}
	
	private PastGameConsole (FinishedGame pg, InputStream is, PrintStream os) {
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
