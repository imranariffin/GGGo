package com.fdmgroup.gggo.model;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fdmgroup.gggo.controller.NamedQuerySet;

import static com.fdmgroup.gggo.model.Stone.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="TBL_PERSISTENT_STATE")
@NamedQueries({
	@NamedQuery(name=NamedQuerySet.PERSISTENT_STATE_FIND_ALL, query="select ps from PersistentState ps"),
	@NamedQuery(name=NamedQuerySet.PERSISTENT_STATE_DELETE, query="delete from PersistentState ps where ps.stateId = :stid"),
	@NamedQuery(name=NamedQuerySet.PERSISTENT_STATE_FIND_ONE, query="select ps from PersistentState ps where ps.stateId = :stid"),
})
public class PersistentState {
	
	@Id
	@Column(name="stateId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int stateId;
	
//	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
//	private Game game;
	
	@Column(name="turnNumber", nullable=false)
	private int turnNumber;
	
	@OneToMany(mappedBy="persistentState", fetch=FetchType.EAGER)
	private List<Placement> placements;
	
	public PersistentState() {
		this(new State());
	}
	
	public PersistentState(State state) {
		Stone[][] board = state.getBoard();
		int size = board.length;
		placements = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] != E) {
					Stone stone = board[i][j];
					placements.add(new Placement(i, j, stone, this));
				}
			}
		}
		
		turnNumber = state.getTurn();
	}
	
	public State toState() {
//		int size = game.getSize();
		int size = 9;
		Stone[][] b = new Stone[size][size];
		
		Iterator<Placement> it = placements.iterator(); 
		while(it.hasNext()) {
			Placement p = it.next();
			b[p.getRowNumber()][p.getColNumber()] = p.getStone();
		}
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (b[i][j] == null) {
					b[i][j] = E;
				}
			}
		}
		
		State s = new State(b, turnNumber, stateId);
		return s;
	}

	public int getStateId() {
		return stateId;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public List<Placement> getPlacements() {
		return placements;
	}

	public void setIntersections(List<Placement> intersections) {
		this.placements = intersections;
	}

	@Override
	public String toString() {
		return "PersistentState [stateId=" + stateId + ", turnNumber=" + turnNumber + ", intersections=" + placements
				+ "]";
	}
}