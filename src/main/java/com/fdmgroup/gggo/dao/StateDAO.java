package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.B;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.model.NamedQuerySet;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;

public class StateDAO {
	private static StateDAO inst;
	private static EntityManagerFactory emf;
	
	private StateDAO() {
	}
	
	public static StateDAO getInstance(EntityManagerFactory _emf) {
		emf = _emf;
		if (inst == null) {
			inst = new StateDAO();
		}
		return inst;
	}

	@SuppressWarnings("unchecked")
	public List<PersistentState> getPersistentStateList(int gameId) {
		EntityManager em = emf.createEntityManager();
		
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentGame pg =  gdao.getPersistentGame(gameId);
		
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
	


	public PersistentState createEmptyPersistentState(int gameId) {
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentGame pg = gdao.getPersistentGame(gameId);
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
	
	public PersistentState createPersistentState(int gameId, int t) {
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentGame pg = gdao.getPersistentGame(gameId);
		
		PersistentState ps = new PersistentState(pg);
		ps.setTurnNumber(t);
		
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
	
	public PersistentState createPersistentState(
			int gameId, int r, int c, int t, Stone st) {
		
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentGame pg = gdao.getPersistentGame(gameId);
		PlacementDAO pdao = DAOFactory.getPlacementDAO();
		PersistentState ps = new PersistentState(pg);
		ps.setTurnNumber(t);
		
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
		
		/* Add new placement */
		
		pdao.createPlacement(r, c, st, ps);
		
		pg.getPersistentStates().add(ps);
		
		return ps;
	}
	
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

	public State createState(Game game, int i, int j, int t, Stone st) {
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentState ps = createPersistentState(game.getGameId(), i, j, t, st);
		
		State s = new State(ps);
		
		return s;
	}
	
	public State createState(Game game, int t) {
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentState ps = createPersistentState(game.getGameId(), t);
		
		State s = new State(ps);
		
		return s;
	}
	
	public State getState(PersistentState ps) {
		State s = new State(ps);
		return s;
	}
}
