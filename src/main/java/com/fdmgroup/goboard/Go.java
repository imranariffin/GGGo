package com.fdmgroup.goboard;

public abstract class Go {
	public abstract String getWinner();
	public abstract void place(int i, int j) throws InvalidPlacementException;
	public abstract void pass();
	public abstract void resign();
}
	