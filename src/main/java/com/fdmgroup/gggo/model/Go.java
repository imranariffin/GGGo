package com.fdmgroup.gggo.model;

import com.fdmgroup.gggo.controller.InvalidPlacementException;

public interface Go {
	public abstract void place(int i, int j) throws InvalidPlacementException;
	public abstract void pass();
	public abstract void resign();
}
	