package com.fdmgroup.goclient;

import java.io.InputStream;
import java.io.PrintStream;

import com.fdmgroup.goboard.GoGame;
import com.fdmgroup.goboard.GoUtils;

public class GameConsole extends GoClientConsole {
	
	private static GameConsole inst;
	private GoGame goGame;
	private GameConsole(GoGame gg, InputStream is, PrintStream os) {
		super(is, os);
		goGame = gg;
	}

	@Override
	public String console() {
		String stone = goGame.getTurn() % 2 == 0 ? "B" : "W";
		out.print(GoUtils.toString(goGame.getBoard()));
		out.print("@Game: " + stone + "> ");
		return in.nextLine();
	}

	@Override
	public void welcome() {
		out("Black to start. Board size: 9x9. Komi: 0.0\n");		
	}

	public static GoClientConsole getInstance(GoGame gg) {
		if (inst == null) {
			return new GameConsole(gg, System.in, System.out);
		}
		return inst;
	}
}
