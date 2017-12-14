package com.fdmgroup.gggo.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO {
	private static EntityManagerFactory emf;
	
	public static PersistentGameDAO getPersistentGameDAO() {
		if (emf == null) {
			emf = createEntityManagerFactor();
		}
		return PersistentGameDAO.getInstance(emf);
	}
	
	public static PersistentStateDAO getPersistentStateDAO() {
		if (emf == null) {
			emf = createEntityManagerFactor();
		}
		return PersistentStateDAO.getInstance(emf);
	}
	
	public static PlacementDAO getPlacementDAO() {
		if (emf == null) {
			emf = createEntityManagerFactor();
		}
		return PlacementDAO.getInstance(emf);
	}
	
	private static EntityManagerFactory createEntityManagerFactor() {
		return Persistence.createEntityManagerFactory("GGGo");
	}
}
