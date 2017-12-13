package com.fdmgroup.gggo.controller;

import java.util.Stack;

import com.fdmgroup.gggo.exceptions.EndOfStateStackException;

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
	
	public State getCurState() {
		return states.peek();
	}
	
	public Stone[][] getBoard() {
		return getCurState().getBoard();
	}
	
	public int getTurn() {
		return states.size() - 1;
	}
	
	public int getNextTurn() {
		return getTurn() + 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SIZE;
		result = prime * result + ((futureStates == null) ? 0 : futureStates.hashCode());
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InteractiveGo other = (InteractiveGo) obj;
		if (SIZE != other.SIZE)
			return false;
		if (futureStates == null) {
			if (other.futureStates != null)
				return false;
		} else if (!futureStates.equals(other.futureStates))
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		return true;
	}
}
