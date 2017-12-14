package com.fdmgroup.gggo.controller;

import com.fdmgroup.gggo.exceptions.InvalidPlacementException;

public interface Go {
	public abstract void place(int i, int j) throws InvalidPlacementException;
	public abstract void pass();
	public abstract void resign();
}
	