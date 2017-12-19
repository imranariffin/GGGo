package com.fdmgroup.gggo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class InviteDAOTest {
	
	private static InviteDAO idao;
	private static UserDAO udao;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
	}
	
	@Test
	public void test_CreateInvite_ConstructAndSavesInvite_GivenInvitorAndInviteeUsernames() throws DeleteInviteInvitorInviteeMismatchException {
		
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
		User imran = udao.createUser("imran.ariffin", "pazzword");
		User amir = udao.createUser("amir.ariffin", "pazzword");
		
		int n = idao.getInvites(imran).size();
		int m = idao.getInvites(amir).size();
		
		Invite inv = idao.createInvite(imran, amir);
		
		assertEquals(n + 1, idao.getInvites(imran).size());
		assertEquals(m + 1, idao.getInvites(amir).size());
		
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
	}
	
	@Test
	public void test_CreateInvite_ReturnsNullAndDidNotCreateInvite_GivenInviteeNull() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("imran.ariffin");
		User imran = udao.createUser("imran.ariffin", "pazzword");
		
		int n = idao.getInvites(imran).size();
		
		Invite inv = idao.createInvite(imran, null);
		
		assertNull(inv);
		assertEquals(n, idao.getInvites(imran).size());
		
		udao.deleteUser(imran);
	}
	
	@Test
	public void test_GetInvite_ReturnsInvite_GivenInviteId() throws DeleteInviteInvitorInviteeMismatchException {
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		assertNotNull(inv);
		assertEquals(inv, idao.getInvite(inv.getInviteId()));
		
		udao.deleteUser(invitor);
		udao.deleteUser(invitee);
	}
	
	@Test
	public void test_GetInvite_ReturnsNull_GivenNonExistingInviteId() throws DeleteInviteInvitorInviteeMismatchException {
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		idao.deleteInvite(invitor, invitee, inv);
		
		assertNotNull(inv);
		assertNull(idao.getInvite(inv.getInviteId()));
		
		udao.deleteUser(invitor);
		udao.deleteUser(invitee);
	}
	
	@Test
	public void test_DeleteInvite_RemovesInviteFromDb_GivenInvitorInviteeAndInvite() {
		
	}
	
	@Test
	public void test_DeleteInvite_ThrowsInvitorInviteeMismatchException_GivenInvitorAndInviteeSwitchedPlaceInArgument() throws DeleteInviteInvitorInviteeMismatchException {

		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		try {
//			Invitor and Invitor swapped place as arguments
			assertNotNull(invitor);
			assertNotNull(invitee);
			assertNotNull(inv);
			idao.deleteInvite(invitee, invitor, inv);

			assertTrue(invitee.getReceivedInvites().contains(inv));
			assertTrue(invitor.getSentInvites().contains(inv));

			idao.deleteInvite(invitor, invitee, inv);
			
			assertFalse(invitor.getSentInvites().contains(inv));
			assertFalse(invitee.getReceivedInvites().contains(inv));
			
		} catch (DeleteInviteInvitorInviteeMismatchException diiime) {
			assertTrue(invitor.getSentInvites().contains(inv));
			assertTrue(invitee.getReceivedInvites().contains(inv));
			
			try {
				udao.deleteUser(invitor);
				udao.deleteUser(invitee);
			} catch (DeleteInviteInvitorInviteeMismatchException diiime2) {
				diiime2.printStackTrace();
				fail();
			}
		}
	}
}
