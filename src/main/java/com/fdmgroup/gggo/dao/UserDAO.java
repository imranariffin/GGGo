package com.fdmgroup.gggo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.NamedQuerySet;
import com.fdmgroup.gggo.model.User;

public class UserDAO {
	private static UserDAO inst;
	private static EntityManagerFactory emf;
	
	private UserDAO() {
	}
	
	public static UserDAO getInstance(EntityManagerFactory _emf) {
		emf = _emf;
		if (inst == null) {
			inst = new UserDAO();
		}
		return inst;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		EntityManager em = emf.createEntityManager();
		List<User> users = new ArrayList<>(); 
		
		try {
			users = em.createNamedQuery(NamedQuerySet.USER_FIND_ALL).getResultList();
		} finally {
			em.close();
		}
		
		return users;
	}

	public User createUser(String username, String password) {
		EntityManager em = emf.createEntityManager();
		User user = new User(username, password);
		try {
			em.getTransaction().begin();
			
			em.persist(user);
			
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
		return user;
	}

	public User getUser(String uname) {
		EntityManager em = emf.createEntityManager();
		User user;
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.USER_FIND_ONE);
			query.setParameter("uname", uname);
			user = (User) query.getSingleResult();
		} catch(NoResultException nre) {
			return null;
		} finally {
			em.close();
		}
		
		return user;
	}

	public int updateUser(User updateableUser) {
		EntityManager em = emf.createEntityManager();
		int ret = 0;
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.USER_UPDATE);
			
			em.getTransaction().begin();
			
			query.setParameter("uname", updateableUser.getUsername());
			query.setParameter("newpw", updateableUser.getPassword());
			ret = query.executeUpdate();
			
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		
		return ret;
	}

	public int deleteUser(User user) throws DeleteInviteInvitorInviteeMismatchException {
		EntityManager em = emf.createEntityManager();
		int ret = 0;

		if (user == null) {
			return ret;
		}
		
		InviteDAO idao = DAOFactory.getInviteDAO();
		
		List<Invite> sentInvites = user.getSentInvites();
		while (!sentInvites.isEmpty()) {
			Invite inv = sentInvites.get(0);
			User invitor = user;
			User invitee = inv.getInvitee();
			idao.deleteInvite(invitor, invitee, inv);
		}
		
		List<Invite> receivedInvites = user.getReceivedInvites();
		while (!receivedInvites.isEmpty()) {
			Invite inv = receivedInvites.get(0);
			User invitor = inv.getInvitor();
			User invitee = user;
			idao.deleteInvite(invitor, invitee, inv);
		}
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.USER_DELETE);
			em.getTransaction().begin();
			
			if (query != null) {
				query.setParameter("uname", user.getUsername());
				ret = query.executeUpdate();	
			}
			
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		
		return ret;
	}
	
	public int deleteUser(String uname) throws DeleteInviteInvitorInviteeMismatchException {
		User user = getUser(uname);
		return deleteUser(user);
	}
}
