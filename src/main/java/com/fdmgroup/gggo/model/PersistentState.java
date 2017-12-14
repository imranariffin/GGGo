package com.fdmgroup.gggo.model;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fdmgroup.gggo.controller.Stone;

import static com.fdmgroup.gggo.controller.Stone.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name="TBL_PERSISTENT_STATE")
@NamedQueries({
	@NamedQuery(name=NamedQuerySet.PERSISTENT_STATE_FIND_ALL, query="select ps from PersistentState ps where ps.pGame = :pGame"),
	@NamedQuery(name=NamedQuerySet.PERSISTENT_STATE_DELETE, query="delete from PersistentState ps where ps.stateId = :stid"),
	@NamedQuery(name=NamedQuerySet.PERSISTENT_STATE_FIND_ONE, query="select ps from PersistentState ps where ps.stateId = :stid"),
})
public class PersistentState {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int stateId;
	
	@Column(name="turnNumber", nullable=false)
	private int turnNumber;
	
	@ManyToOne(cascade=CascadeType.ALL, optional=false, fetch=FetchType.EAGER)
	private PersistentGame pGame;
	
	@OneToMany(mappedBy="persistentState", fetch=FetchType.EAGER, orphanRemoval=true)
	private List<Placement> placements;
	
	public PersistentState() {
		this(null);
	}
	
	public PersistentState(PersistentGame pg) {
		pGame = pg;
		placements = new ArrayList<>();
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

	public void setPersistentGame(PersistentGame pg) {
		pGame = pg;
	}
	
	@Override
	public String toString() {
		return "PersistentState [stateId=" + stateId + ", turnNumber=" + turnNumber + ", intersections=" + placements
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pGame == null) ? 0 : pGame.hashCode());
		result = prime * result + ((placements == null) ? 0 : placements.hashCode());
		result = prime * result + stateId;
		result = prime * result + turnNumber;
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
		PersistentState other = (PersistentState) obj;
		if (pGame == null) {
			if (other.pGame != null)
				return false;
		}
		if (placements == null) {
			if (other.placements != null)
				return false;
		}
		Comparator<Placement> comp = new Comparator<Placement>() {

			@Override
			public int compare(Placement o1, Placement o2) {
				return o1.getPlacementId() - o2.getPlacementId();
			}
			
		};
		Collections.sort(placements, comp);
		Collections.sort(other.placements, comp);
		
		for (int i = 0; i < placements.size(); i++) {
			Placement pt = placements.get(i);
			if (!pt.equals(other.placements.get(i))) {
				return false;
			}
		}

		if (stateId != other.stateId)
			return false;
		if (turnNumber != other.turnNumber)
			return false;
		return true;
	}

	public PersistentGame getPersistentGame() {
		return pGame;
	}
}