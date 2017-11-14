package com.fdmgroup.goclient;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.fdmgroup.goboard.GoGame;

public abstract class GoClientConsole {
	protected Scanner in;
	protected PrintStream out;
	protected GoGame goGame;
	
	public GoClientConsole(GoGame gg, InputStream is, PrintStream os) {
		goGame = gg;
		in = new Scanner(is);
		out = os;
	}
	
	public abstract String console();
	public abstract void welcome();
	
	public void out(String msg) {
		out.print(msg);
	}
}
