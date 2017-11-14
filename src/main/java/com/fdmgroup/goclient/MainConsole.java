package com.fdmgroup.goclient;

import java.io.InputStream;
import java.io.PrintStream;

import com.fdmgroup.goboard.GoGame;

public class MainConsole extends GoClientConsole {
	private static MainConsole inst;
	private MainConsole(GoGame gg, InputStream is, PrintStream os) {
		super(gg, is, os);
	}
	
	@Override
	public String console() {
		out.print("@Menu > ");
		return in.nextLine();
	}
	
	@Override
	public void welcome() {
		out("Welcome to GGGo!\n");
		out("P: Start new game\n");
		out("V: View past games\n");
		out("----------------------\n");
	}

	public static GoClientConsole getInstance(GoGame gg) {
		if (inst == null) {
			return new MainConsole(gg, System.in, System.out);
		}
		return inst;
	}
}