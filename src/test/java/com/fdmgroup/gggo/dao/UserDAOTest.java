package com.fdmgroup.gggo.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.model.User;

public class UserDAOTest {

	private static UserDAO udao;
	private static User existingUser;
	private static User updateableUser;
	private static User nonExistingUser;
	private static User user1;
	private static int initNumUsers;
	
	@BeforeClass
	public static void setupOnce() {
		
		udao = DAO.getUserDAO();

		existingUser = new User("akira.touya", "whoisshindou");
		udao.postUser(existingUser);
		
		nonExistingUser = new User("nonexistinguser", "somenonexistingpassword");
		udao.deleteUser(nonExistingUser);
		
		updateableUser = new User("updateable", "updateablepassword");
		udao.postUser(updateableUser);
		
		initNumUsers = udao.getUsers().size();
	}
	
	@AfterClass
	public static void tearDownOnce() {
		udao.deleteUser(existingUser);
		udao.deleteUser(updateableUser);
	}
	
	@Test
	public void test_GetUsers_ReturnsAllUsersFromDatabase() {
		List<User> actual = udao.getUsers();
		assertEquals(initNumUsers, actual.size());
	}
	
	@Test
	public void test_PostUser_SavesGivenUserOnDatabase() {
		user1 = new User("imranariffin", "password123");
		
		assertEquals(initNumUsers, udao.getUsers().size());
		udao.postUser(user1);
		assertEquals(initNumUsers + 1, udao.getUsers().size());
		
		udao.deleteUser(user1);
	}
	
	@Test
	public void test_DeleteUser_RemovesGivenUserFromDatabase() {
		User removableUser = new User("saifujiwara", "divinemove");
		
		udao.postUser(removableUser);
		assertEquals(initNumUsers + 1, udao.getUsers().size());
		int ret = udao.deleteUser(removableUser);
		
		assertEquals(1, ret);
		assertEquals(initNumUsers, udao.getUsers().size());
	}
	
	@Test
	public void test_DeleteUser_RemovesNonExistingUser() {
		
	}
	
	@Test
	public void test_GetUserByUsername_ReturnsOneUserGivenExistingUsername() {
		User actual = udao.getUser(existingUser.getUsername());
		
		assertEquals(existingUser, actual);
	}
	
	@Test
	public void test_GetUserByUsername_ReturnsNullGivenNonExistingUsername() {
		User actual = udao.getUser(nonExistingUser.getUsername());
		
		assertNull(actual);
	}
	
	@Test
	public void test_UpdateUser_UpdatesUserGivenUsername() {
		updateableUser.setPassword("newpassword");
		
		int ret = udao.updateUser(updateableUser);
		User actual = udao.getUser(updateableUser.getUsername());
		
		assertEquals(1, ret);
		assertEquals(initNumUsers, udao.getUsers().size());
		assertEquals(updateableUser, actual);
	}
	
	@Test
	public void test_PostUser_CannotAddUserWithExistingUsername() {
		User newuser = new User(existingUser.getUsername(), "somepassword");
		
		udao.postUser(newuser);	
		
		assertEquals(initNumUsers, udao.getUsers().size());
	}
}
