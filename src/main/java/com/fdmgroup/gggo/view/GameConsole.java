package com.fdmgroup.gggo.view;

import java.io.InputStream;
import java.io.PrintStream;

import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.controller.Game;

public class GameConsole extends Console {
	
	private static GameConsole inst;
	private Game goGame;
	private GameConsole(Game gg, InputStream is, PrintStream os) {
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

	public static Console getInstance(Game gg) {
		if (inst == null) {
			return new GameConsole(gg, System.in, System.out);
		}
		return inst;
	}

	public void run() {
	}
}
