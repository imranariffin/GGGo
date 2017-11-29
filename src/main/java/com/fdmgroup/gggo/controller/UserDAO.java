package com.fdmgroup.gggo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.fdmgroup.gggo.model.User;

public class UserDAO {
	private static UserDAO inst;
	private EntityManagerFactory emf;
	
	private UserDAO() {
		emf = Persistence.createEntityManagerFactory("GGGo");
	}
	
	public static UserDAO getInstance() {
		if (inst == null) {
			inst = new UserDAO();
		}
		return inst;
	}

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

	public void postUser(User user) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			em.persist(user);
			
			em.getTransaction().commit();
		} catch(PersistenceException pe) {
			return;
		} catch(ConstraintViolationException cve) {
			return;
		} finally {
			em.close();
		}
	}

	public int deleteUser(User removableUser) {
		EntityManager em = emf.createEntityManager();
		int ret = 0;
		
		try {
			Query query = em.createNamedQuery(NamedQuerySet.USER_DELETE);
			em.getTransaction().begin();
			
			if (query != null) {
				query.setParameter("uname", removableUser.getUsername());
				ret = query.executeUpdate();	
			}
			
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		
		return ret;
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
}
