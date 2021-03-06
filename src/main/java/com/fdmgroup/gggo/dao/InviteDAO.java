package com.fdmgroup.gggo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.NamedQuerySet;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.User;

public class InviteDAO {
	private static InviteDAO inst;
	private static EntityManagerFactory emf;
	
	private InviteDAO() {}
	
	public static InviteDAO getInstance(EntityManagerFactory _emf) {
		emf = _emf;
		if (inst == null) {
			inst = new InviteDAO();
		}
		return inst;
	}

	@SuppressWarnings("unchecked")
	List<Invite> getInvites(User user) {
		EntityManager em = emf.createEntityManager();
		List<Invite> res = new ArrayList<>();
		
		try {
			Query q = em.createNamedQuery(NamedQuerySet.INVITE_FIND_ALL);
			q.setParameter("user", user);
			res = q.getResultList();
			
		} finally {
			em.close();
		}
		
		return res;
	}
	
	public Invite getInvite(int inviteId) {
		EntityManager em = emf.createEntityManager();
		Invite res = null;
		
		try {
			Query q = em.createNamedQuery(NamedQuerySet.INVITE_FIND_ONE);
			q.setParameter("invid", inviteId);
			res = (Invite) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} finally {
			em.close();
		}
		
		return res;
	}

	public Invite createInvite(User invitor, User invitee) {
		
		if (invitor == null || invitee == null) {
			return null;
		}
		
		EntityManager em = emf.createEntityManager();
		Invite inv = new Invite();
		inv.setInvitor(invitor);
		inv.setInvitee(invitee);
		
		try { 
			em.getTransaction().begin();
			inv.setInvitor(em.contains(inv.getInvitor()) 
					? inv.getInvitor() 
					: em.merge(inv.getInvitor()));
			inv.setInvitee(em.contains(inv.getInvitee()) 
					? inv.getInvitee() 
					: em.merge(inv.getInvitee()));
			em.persist(inv);
			
			em.getTransaction().commit();
		} catch(PersistenceException pe) {
			pe.printStackTrace();
			return null;
		} catch(ConstraintViolationException cve) {
			cve.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		
		invitor.getSentInvites().add(inv);
		invitee.getReceivedInvites().add(inv);
		
		return inv;
	}

	public void deleteInvite(User invitor, User invitee, Invite inv) 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		if (inv == null) { return; }
		if (!invitor.getSentInvites().contains(inv) && !invitee.getReceivedInvites().contains(inv)) {
			throw new DeleteInviteInvitorInviteeMismatchException();
		}
		
		invitor.getSentInvites().remove(inv);
		invitee.getReceivedInvites().remove(inv);
		
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.remove(em.contains(inv) ? inv : em.merge(inv));
			em.getTransaction().commit();
		} catch (EntityNotFoundException enfe) {
			return;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Invite> getInvites(String username) {
		EntityManager em = emf.createEntityManager();
		List<Invite> res = new ArrayList<>();
		
		UserDAO udao = DAOFactory.getUserDAO();
		User user = udao.getUser(username);
		
		if (user == null) {
			return res;
		}
		
		try {
			Query q = em.createNamedQuery(NamedQuerySet.INVITE_FIND_ALL);
			q.setParameter("user", user);
			res = q.getResultList();
			
		} finally {
			em.close();
		}
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Invite> getSentInvites(String invitorUsername) {
		EntityManager em = emf.createEntityManager();
		List<Invite> res = new ArrayList<>();
		
		UserDAO udao = DAOFactory.getUserDAO();
		User invitor = udao.getUser(invitorUsername);
		
		if (invitor == null) {
			return res;
		}
		
		try {
			Query q = em.createNamedQuery(NamedQuerySet.INVITE_SENT_FIND_ALL);
			q.setParameter("invitor", invitor);
			res = q.getResultList();
			
		} finally {
			em.close();
		}
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<Invite> getReceivedInvites(String inviteeUsername) {
		EntityManager em = emf.createEntityManager();
		List<Invite> res = new ArrayList<>();
		
		UserDAO udao = DAOFactory.getUserDAO();
		User invitee = udao.getUser(inviteeUsername);
		
		if (invitee == null) {
			return res;
		}
		
		try {
			Query q = em.createNamedQuery(NamedQuerySet.INVITE_RECEIVED_FIND_ALL);
			q.setParameter("invitee", invitee);
			res = q.getResultList();
			
		} finally {
			em.close();
		}
		
		return res;
	}

	public void updateInvite(Invite inv) {
		EntityManager em = emf.createEntityManager();
		
		try { 
			em.getTransaction().begin();
			
			inv.setInvitor(em.contains(inv.getInvitor()) 
					? inv.getInvitor() 
					: em.merge(inv.getInvitor()));
			inv.setInvitee(em.contains(inv.getInvitee()) 
					? inv.getInvitee() 
					: em.merge(inv.getInvitee()));
			em.persist(em.contains(inv) ? inv : em.merge(inv));
			
			em.getTransaction().commit();
		} catch(PersistenceException pe) {
			pe.printStackTrace();
			return;
		} catch(ConstraintViolationException cve) {
			cve.printStackTrace();
			return;
		} finally {
			em.close();
		}
		
		return;
	}

	@SuppressWarnings("unchecked")
	public List<Invite> getAcceptedInvites(String username) {
		EntityManager em = emf.createEntityManager();
		List<Invite> res = new ArrayList<>();
		
		UserDAO udao = DAOFactory.getUserDAO();
		User user = udao.getUser(username);
		
		if (user == null) {
			return res;
		}
		
		try {
			Query q = em.createNamedQuery(NamedQuerySet.INVITE_ACCEPTED_FIND_ALL);
			q.setParameter("user", user);
			res = q.getResultList();
			
		} finally {
			em.close();
		}
		
		return res;
	}

	public Invite getInviteByGameId(int gameId) {
		EntityManager em = emf.createEntityManager();
		Invite res = null;
		
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentGame pg = gdao.getPersistentGame(gameId);
		
		try {
			Query q = em.createNamedQuery(NamedQuerySet.INVITE_FIND_ONE_BY_GAMEID);
			q.setParameter("game", pg);
			res = (Invite) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} finally {
			em.close();
		}
		
		return res;
	}
}
