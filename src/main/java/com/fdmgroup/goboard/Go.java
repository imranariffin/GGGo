package com.fdmgroup.goboard;

public abstract class Go {
	public abstract String getWinner();
	public abstract boolean place(int i, int j);
	public abstract void pass();
	public abstract void resign();
}
