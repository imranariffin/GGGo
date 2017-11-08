package com.fdmgroup.goboard;

public interface PlayableGo {	
	public abstract void back() throws EmptyStateStackException;
	public abstract void next() throws EmptyStateStackException;
	public abstract void jumpToFirst();
	public abstract void jumpToLast();
}
