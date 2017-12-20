package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.E;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.model.NamedQuerySet;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;

public class PersistentStateDAO {
	private static PersistentStateDAO inst;
	private static EntityManagerFactory emf;
	
	private PersistentStateDAO() {
	}
	
	public static PersistentStateDAO getInstance(EntityManagerFactory _emf) {
		emf = _emf;
		if (inst == null) {
			inst = new PersistentStateDAO();
		}
		return inst;
	}

	@SuppressWarnings("unchecked")
	List<PersistentState> getPersistentStateList(PersistentGame pg) {
		EntityManager em = emf.createEntityManager();
		List<PersistentState> pStates;
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.PERSISTENT_STATE_FIND_ALL);
			query.setParameter("pGame", pg);
			
			em.getTransaction().begin();
			pStates = (List<PersistentState>) query.getResultList();
			em.getTransaction().commit();
			
		} finally {
			em.close();
		}
		
		return pStates;
	}
	
	PersistentState getPersistentState(int stateId) {
		EntityManager em = emf.createEntityManager();
		PersistentState ps;
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.PERSISTENT_STATE_FIND_ONE);
			query.setParameter("stid", stateId);
			
			em.getTransaction().begin();
			ps = (PersistentState) query.getSingleResult();
			em.getTransaction().commit();
			
		} finally {
			em.close();
		}
		
		return ps;
	}
	
	public PersistentState createPersistentState(PersistentGame pg) {
		PersistentState ps = new PersistentState(pg);
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			ps.setPersistentGame((em.contains(pg) ? pg : em.merge(pg)));
			em.persist(ps);
			em.getTransaction().commit();
		} catch (EntityExistsException eee) {
			eee.printStackTrace();
			return null;
		} catch(IllegalArgumentException iae) {
			iae.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		
		pg.getPersistentStates().add(ps);
		
		return ps;
	}
	
//	public State mapToState(PersistentState ps) {
//		int size = 9;
//		Stone[][] board = new Stone[size][size];
//		
//		for (Placement p: ps.getPlacements()) {
//			Stone s = p.getStone();
//			int r = p.getRowNumber();
//			int c = p.getColNumber();
//			board[r][c] = s;
//		}
//		
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				if (board[i][j] == null) {
//					board[i][j] = E;
//				}
//			}
//		}
//
//		State state = new State(board, ps.getTurnNumber(), ps.getStateId());
//		
//		return state;
//	}
	
	public void deletePersistentState(PersistentGame pg, PersistentState ps) {
		
		if (pg == null || ps == null) { return; }
		
		EntityManager em = emf.createEntityManager();
		
		pg.getPersistentStates().remove(ps);
		ps.setPersistentGame(null);
		
		PlacementDAO pdao = DAOFactory.getPlacementDAO();
		while (!ps.getPlacements().isEmpty()) {
			Placement pt = ps.getPlacements().get(0);
			pdao.deletePlacement(ps, pt);
		}
		
		try {
			em.getTransaction().begin();			
			em.remove(em.contains(ps) ? ps : em.merge(ps));
			em.getTransaction().commit();
		} catch (NoResultException nre) {
			nre.printStackTrace();
		} finally {
			em.close();
		}		
	}
}
