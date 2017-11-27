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

@Entity
@Table(name="TBL_PLACEMENT")
public class Placement {
	@Id
	@Column(name="placementId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int placementId;
	
	@Column(name="colNumber", nullable=false)
	private int colNumber;
	
	@Column(name="rowNumber", nullable=false)
	private int rowNumber;
	
	@Column(name="stone", nullable=false)
	@Enumerated(EnumType.STRING)
	private Stone stone;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
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
}