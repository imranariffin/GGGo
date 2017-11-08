package com.fdmgroup.goboard;

public interface PlayableGo {	
	public abstract void back() throws EndOfStateStackException;
	public abstract void next() throws EndOfStateStackException;
	public abstract void jumpToFirst();
	public abstract void jumpToLast();
}
