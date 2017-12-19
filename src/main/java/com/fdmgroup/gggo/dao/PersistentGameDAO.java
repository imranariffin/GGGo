package com.fdmgroup.gggo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.model.NamedQuerySet;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;

public class PersistentGameDAO {
	
	private static PersistentGameDAO inst;
	private static EntityManagerFactory emf;
	
	private PersistentGameDAO() {
	}
	
	public static PersistentGameDAO getInstance(EntityManagerFactory _emf) {
		emf = _emf;
		if (inst == null) {
			inst = new PersistentGameDAO();
		}
		return inst;
	}

	@SuppressWarnings("unchecked")
	List<PersistentGame> getPersistentGames() {
		EntityManager em = emf.createEntityManager();
		List<PersistentGame> pGames = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.GAME_FIND_ALL);
			
			em.getTransaction().begin();
			
			pGames = query.getResultList();
			
			em.getTransaction().commit();
		} finally { 
			em.close();
		}
		
		return pGames;
	}
	
	PersistentGame getPersistentGame(int gameId) {
		EntityManager em = emf.createEntityManager();
		PersistentGame pg;
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.GAME_FIND_ONE);
			query.setParameter("gid", gameId);
			pg = (PersistentGame) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} finally {
			em.close();
		}
		
		return pg;	
	}
	
	PersistentGame createPersistentGame() {
		PersistentGame pg = new PersistentGame();
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(pg);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

		return pg;
	}
	
//	private Game mapToGame(PersistentGame pg) {
//		Game game = new Game(pg.getGameId());
//		
//		List<PersistentState> pStates = pg.getStates();
//		Collections.sort(pStates, new Comparator<PersistentState>() {
//			@Override
//			public int compare(PersistentState ps0, PersistentState ps1) {
//				return ps1.getTurnNumber() - ps0.getTurnNumber();
//			}
//		});
//		
//		PersistentStateDAO sdao = PersistentStateDAO.getInstance();
//		for (PersistentState ps: pStates) {
//			State s = sdao.mapToState(ps);
//			game.getStates().push(s);
//		}
//		
//		return game;
//	}

	void deletePersistentGame(PersistentGame pg) {
		EntityManager em = emf.createEntityManager();
		PersistentStateDAO sdao = DAOFactory.getPersistentStateDAO();
		
		if (pg == null) {
			return;
		}
		
		while (!pg.getPersistentStates().isEmpty()) {
			sdao.deletePersistentState(pg, pg.getPersistentStates().get(0));
		}
		
		try {
			em.getTransaction().begin();
			em.remove(em.contains(pg) ? pg : em.merge(pg));
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}	
}
