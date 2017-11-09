package com.fdmgroup.goclient;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.fdmgroup.goboard.GoGame;
import com.fdmgroup.goboard.GoUtils;

public class GoClientConsole {
	private Scanner in;
	private PrintStream out;
	private GoGame goGame;
	
	public GoClientConsole(GoGame gg, InputStream is, PrintStream os) {
		goGame = gg;
		in = new Scanner(is);
		out = os;
	}
	
	public String console() {
		String stone = goGame.getTurn() % 2 == 0 ? "B" : "W";
		out.print(GoUtils.toString(goGame.getBoard()));
		out.print(stone + "> ");
		return in.nextLine();
	}
	
	public void out(String msg) {
		out.print(msg);
	}
	
	public void welcome() {
		out("Welcome to GGGo!\n");
	}
}
