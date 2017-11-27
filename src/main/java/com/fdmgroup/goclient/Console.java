package com.fdmgroup.goclient;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public abstract class Console {
	protected Scanner in;
	protected PrintStream out;
	
	public Console(InputStream is, PrintStream os) {
		in = new Scanner(is);
		out = os;
	}
	
	public abstract String console();
	public abstract void welcome();
	
	public void out(String msg) {
		out.print(msg);
	}
}
