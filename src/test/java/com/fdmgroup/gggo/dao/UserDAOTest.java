package com.fdmgroup.gggo.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.User;

public class UserDAOTest {

	private static UserDAO udao;
	private static User existingUser;
	private static User updateableUser;
	private static User nonExistingUser;
	private static User user1;
	private static int initNumUsers;
	
	@BeforeClass
	public static void setupOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao = DAOFactory.getUserDAO();
	}
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("imranariffin");
		udao.deleteUser("saifujiwara");
		
		existingUser = udao.createUser("akira.touya", "whoisshindou");
		
		nonExistingUser = new User("nonexistinguser", "somenonexistingpassword");
		udao.deleteUser(nonExistingUser);
		
		updateableUser = udao.createUser("updateable", "updateablepassword");
		
		initNumUsers = udao.getUsers().size();
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("imranariffin");
		udao.deleteUser("existingUser");
		udao.deleteUser("nonexistinguser");
		udao.deleteUser("akira.touya");
		udao.deleteUser("updateable");
	}
	
	@AfterClass
	public static void tearDownOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser(existingUser);
		udao.deleteUser(updateableUser);
	}
	
	@Test
	public void test_GetUsers_ReturnsAllUsersFromDatabase() {
		List<User> actual = udao.getUsers();
		assertEquals(initNumUsers, actual.size());
	}
	
	@Test
	public void test_CreateUser_SavesGivenUserOnDatabase() throws DeleteInviteInvitorInviteeMismatchException {
		
		user1 = udao.createUser("imranariffin", "password123");
		assertEquals(initNumUsers + 1, udao.getUsers().size());
		
		udao.deleteUser(user1);
	}
	
	@Test
	public void test_DeleteUser_RemovesGivenUserFromDatabase() throws DeleteInviteInvitorInviteeMismatchException {
		User removableUser = udao.createUser("saifujiwara", "divinemove");
		int n = udao.getUsers().size();

		int ret = udao.deleteUser(removableUser);
		
		assertEquals(1, ret);
		assertEquals(n - 1, udao.getUsers().size());
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
		udao.createUser(existingUser.getUsername(), "somepassword");
		int n = udao.getUsers().size();
		assertEquals(n, udao.getUsers().size());
	}
}
