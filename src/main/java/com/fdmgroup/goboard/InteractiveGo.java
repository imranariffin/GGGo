package com.fdmgroup.goboard;

import java.util.Stack;

public class InteractiveGo {
	protected int SIZE;
	protected Stack<State> states;
	protected Stack<State> futureStates;
	
	public void back() throws EndOfStateStackException {
		if (states.size() == 1) {
			String errMsg = "PlayableGo.back(): State stack reaches its end -- no previous state available!";
			throw new EndOfStateStackException(errMsg);
		}
		futureStates.push(states.pop());
	}
	
	public void forward() throws EndOfStateStackException {
		if (futureStates.size() == 0) {
			String errMsg = "Playable.next(): State stack reaches its end -- no future state available!";
			throw new EndOfStateStackException(errMsg);
		}
		states.push(futureStates.pop());
	}
	
	public void jumpToFirst() {
		while (true) {
			try {
				back();
			} catch (EndOfStateStackException eosse) {
				break;
			}
		}
	}
	
	public void jumpToLast() {
		while (true) {
			try {
				forward();
			} catch (EndOfStateStackException eosse) {
				break;
			}
		}
	}
}
