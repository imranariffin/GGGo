package com.fdmgroup.gggo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.fdmgroup.gggo.controller.State;
import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.W;
import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.model.NamedQuerySet;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;

public class PlacementDAO {
	private static PlacementDAO inst;
	private static EntityManagerFactory emf;
	
	private PlacementDAO() {
	}
	
	public static PlacementDAO getInstance(EntityManagerFactory _emf) {
		emf = _emf;
		if (inst == null) {
			inst = new PlacementDAO();
		}
		return inst;
	}
	
	public Placement createPlacement(int r, int c, Stone st, PersistentState ps) {
		EntityManager em = emf.createEntityManager();
		PersistentStateDAO sdao = DAOFactory.getPersistentStateDAO();
		
		Placement pt = new Placement(r, c, st, ps);;

		try {
			em.getTransaction().begin();
			pt.setPersistentState(em.contains(ps) ? ps : em.merge(ps)); 
			em.persist(pt);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		
		ps.getPlacements().add(pt);
		
		return pt;
	}

	@SuppressWarnings("unchecked")
	public List<Placement> getPlacements(int stateId) {
		EntityManager em = emf.createEntityManager();
		List<Placement> placements = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.PLACEMENT_FIND_ALL);
			PersistentState ps = em.find(PersistentState.class, stateId);
			query.setParameter("ps", ps);
			placements = query.getResultList();
		} finally {
			em.close();
		}
		
		return placements;
	}
	
	public Placement getPlacement(int placementId) {
		EntityManager em = emf.createEntityManager();
		Placement pt;
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.PLACEMENT_FIND_ONE);
			query.setParameter("ptid", placementId);
			
			em.getTransaction().begin();
			pt = (Placement) query.getSingleResult();
			em.getTransaction().commit();
			
		} finally {
			em.close();
		}
		
		return pt;
	}
	
	public void deletePlacement(PersistentState ps, Placement pt) {
		EntityManager em = emf.createEntityManager();
		
		ps.getPlacements().remove(pt);
		pt.setPersistentState(null);
		
		try {
			em.getTransaction().begin();
			em.remove(em.contains(pt) ? pt : em.merge(pt));
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}
