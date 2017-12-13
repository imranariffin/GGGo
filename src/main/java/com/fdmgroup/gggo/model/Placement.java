package com.fdmgroup.gggo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.controller.Stone;

@Entity
@Table(name="TBL_PLACEMENT")
public class Placement {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int placementId;
	
	@Column(name="colNumber", nullable=false)
	private int colNumber;
	
	@Column(name="rowNumber", nullable=false)
	private int rowNumber;
	
	@Column(name="stone", nullable=false)
	@Enumerated(EnumType.STRING)
	private Stone stone;
	
	@ManyToOne(cascade=CascadeType.ALL, optional=false, fetch=FetchType.EAGER)
	private PersistentState persistentState;
	
	public Placement() {
		super();
	}
	
	public Placement(int i, int j, Stone s, PersistentState ps) {
		colNumber = i;
		rowNumber = j;
		stone = s;
		persistentState = ps;
	}
	
	public int getColNumber() {
		return colNumber;
	}

	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getPlacementId() {
		return placementId;
	}

	public Stone getStone() {
		return stone;
	}
	
	public String toString() {
		return "[" + rowNumber + "," + colNumber + ":" + GoUtils.toString(stone) + "]"; 
	}

	public PersistentState getPersistentState() {
		return persistentState;
	}

	public void setPersistentState(PersistentState persistentState) {
		this.persistentState = persistentState;
	}

	public void setStone(Stone stone) {
		this.stone = stone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colNumber;
		result = prime * result + placementId;
		result = prime * result + rowNumber;
		result = prime * result + ((stone == null) ? 0 : stone.hashCode());
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
		Placement other = (Placement) obj;
		if (colNumber != other.colNumber)
			return false;
		if (placementId != other.placementId)
			return false;
		if (rowNumber != other.rowNumber)
			return false;
		if (stone != other.stone)
			return false;
		return true;
	}
}