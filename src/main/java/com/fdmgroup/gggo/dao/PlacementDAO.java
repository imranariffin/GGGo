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
		emf = Persistence.createEntityManagerFactory("GGGo");
	}
	
	public static PlacementDAO getInstance() {
		if (inst == null) {
			inst = new PlacementDAO();
		}
		return inst;
	}
	
	public Placement createPlacement(int r, int c, Stone st, PersistentState ps) {
		EntityManager em = emf.createEntityManager();
		PersistentStateDAO sdao = PersistentStateDAO.getInstance();
		
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

	public List<Placement> getPlacements(State curState) {
		EntityManager em = emf.createEntityManager();
		List<Placement> placements = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.PLACEMENT_FIND_ALL);
			PersistentState pState = em.find(PersistentState.class, curState.getStateId());
			query.setParameter("pState", pState);
			
			for (Object obj: query.getResultList()) {
				placements.add((Placement) obj);
			}
		} finally {
			em.close();
		}
		
		return placements;
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
