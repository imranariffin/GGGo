package com.fdmgroup.gggo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name=GoEntities.TBL_GO_GAME)
@NamedQueries({
	@NamedQuery(
			name=NamedQuerySet.GAME_FIND_ALL, 
			query="select pg from PersistentGame pg"),
	@NamedQuery(
			name=NamedQuerySet.GAME_FIND_ONE, 
			query="select pg from PersistentGame pg where pg.gameId = :gid"),
})
public class PersistentGame extends PersistentInteractiveGo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected int gameId;
	
	@OneToMany(mappedBy="pGame", fetch=FetchType.EAGER, orphanRemoval=true)
	protected List<PersistentState> pStates;

	public PersistentGame() {
		pStates = new ArrayList<>();
	}
	
	public List<PersistentState> getPersistentStates() {
		return pStates;
	}

	public int getGameId() {
		return gameId;
	}

	@Override
	public String toString() {
		return "PersistentGame [gameId=" + gameId + ", pStates=" + pStates + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gameId;
		result = prime * result + ((pStates == null) ? 0 : pStates.hashCode());
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
		PersistentGame other = (PersistentGame) obj;
		if (gameId != other.gameId)
			return false;
		if (pStates == null) {
			if (other.pStates != null)
				return false;
		}		
		if (pStates.size() != other.pStates.size()) {
			return false;
		}

		Comparator<PersistentState> comp = new Comparator<PersistentState>() {
			@Override
			public int compare(PersistentState o1, PersistentState o2) {
				return o1.getStateId() - o2.getStateId();
			}
			
		};
		Collections.sort(pStates, comp);
		Collections.sort(other.pStates, comp);
		
		for (int i = 0; i < pStates.size(); i++) {
			PersistentState ps = pStates.get(i);
			if (!ps.equals(other.pStates.get(i))) {
				return false;
			}
		}		
		return true;
	}
}
