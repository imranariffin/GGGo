package com.fdmgroup.gggo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.fdmgroup.gggo.model.Placement;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.State;

public class StateDAO {
	private static StateDAO inst;
	private static EntityManagerFactory emf;
	
	private StateDAO() {
		emf = Persistence.createEntityManagerFactory("GGGo");
	}
	
	public static StateDAO getInstance() {
		if (inst == null) {
			inst = new StateDAO();
		}
		return inst;
	}

	public State postState(State state) {
		EntityManager em = getEntityManager();
		PersistentState ps = new PersistentState(state);
		
		try {
			em.getTransaction().begin();
			
			em.persist(ps);
			
			for (Placement p: ps.getPlacements()) {
				System.out.println(p);
				em.persist(p);
			}
			
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		
		return ps.toState();
	}
	
	public State getState(int stateId) {
		PersistentState ps = getPersistentState(stateId);
		if (ps == null) {
			return null;
		}
		
		return ps.toState();
	}
	
	public List<State> getStates() {
		EntityManager em = getEntityManager();
		List<State> states = new ArrayList<>();

		try {
			Query query = em.createNamedQuery(NamedQuerySet.PERSISTENT_STATE_FIND_ALL);
			
			if (query != null) {
				List<PersistentState> list = query.getResultList();
				for (PersistentState ps: list) {
					states.add(ps.toState());
				}	
			}
		} finally {
			em.close();
		}

		Collections.sort(states, new Comparator<State>() {
			@Override
			public int compare(State s1, State s2) {
				return s1.getTurn() - s2.getTurn();
			}
		});
		
		return states;
	}

	public void deleteState(int stateId) {
		EntityManager em = emf.createEntityManager();

		try {
			
			em.getTransaction().begin();
			
			PersistentState ps = getPersistentState(stateId);
			System.out.print(stateId + " == ");
			System.out.print(ps.getStateId() + "\n");
			
			em.remove(em.contains(ps) ? ps : em.merge(ps));
			
			em.getTransaction().commit();
		} catch (NoResultException nre) {
			nre.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	private PersistentState getPersistentState(int stateId) {
		EntityManager em = getEntityManager();
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
}
