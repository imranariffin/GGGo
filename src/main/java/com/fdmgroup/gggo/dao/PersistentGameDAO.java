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
import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.NamedQuerySet;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.User;

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
	
	@SuppressWarnings("unchecked")
	List<PersistentGame> getPersistentGames(User user) {
		EntityManager em = emf.createEntityManager();
		List<PersistentGame> pGames = new ArrayList<>();
		List<Invite> invites = new ArrayList<>();
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.INVITE_FIND_ALL);
			query.setParameter("user", user);
			em.getTransaction().begin();
			
			invites = query.getResultList();
			
			em.getTransaction().commit();
		} finally { 
			em.close();
		}
		
		
		for (Invite inv: invites) {
			if (inv.getGame() != null) {
				pGames.add(inv.getGame());
			}
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
	
	PersistentGame createPersistentGame(Invite inv) {
		PersistentGame pg = new PersistentGame();
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(pg);
			em.getTransaction().commit();
		} finally {
			em.close();
		}

		InviteDAO idao = DAOFactory.getInviteDAO();
		inv.setGame(pg);
		idao.updateInvite(inv);
		
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

	void deletePersistentGame(PersistentGame pg, Invite inv) {
		EntityManager em = emf.createEntityManager();
		PersistentStateDAO sdao = DAOFactory.getPersistentStateDAO();
		
		if (pg == null) {
			return;
		}
		
		while (!pg.getPersistentStates().isEmpty()) {
			sdao.deletePersistentState(pg, pg.getPersistentStates().get(0));
		}
		
		InviteDAO idao = DAOFactory.getInviteDAO();
		inv.setGame(null);
		idao.updateInvite(inv);
		
		try {
			em.getTransaction().begin();
			em.remove(em.contains(pg) ? pg : em.merge(pg));
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public Game createGame(Invite inv) {
		PersistentGame pg = createPersistentGame(inv);
		Game g = new Game(pg);
		
		return g;
	}

	public List<Game> getGames(User user) {
		List<Game> res = new ArrayList<>();
		
		for (PersistentGame pg: getPersistentGames(user)) {
			res.add(new Game(pg));
		}
		
		return res;
	}
}
