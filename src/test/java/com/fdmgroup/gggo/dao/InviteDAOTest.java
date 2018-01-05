package com.fdmgroup.gggo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.User;

public class InviteDAOTest {
	
	private static InviteDAO idao;
	private static UserDAO udao;
	private static GameDAO gdao;
	
	@BeforeClass
	public static void setupOnce() {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
		gdao = DAOFactory.getPersistentGameDAO();
	}
	
	@Before
	public void setup() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		udao.deleteUser("empty-invitor");
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		udao.deleteUser("imran.ariffin");
		udao.deleteUser("amir.ariffin");
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		udao.deleteUser("empty-invitor");
	}
	
	@Test
	public void test_CreateInvite_ConstructAndSavesInvite_GivenInvitorAndInviteeUsernames() throws DeleteInviteInvitorInviteeMismatchException {
		
		User imran = udao.createUser("imran.ariffin", "pazzword");
		User amir = udao.createUser("amir.ariffin", "pazzword");
		
		int n = idao.getInvites(imran).size();
		int m = idao.getInvites(amir).size();
		
		Invite inv = idao.createInvite(imran, amir);
		
		assertEquals(n + 1, idao.getInvites(imran).size());
		assertEquals(m + 1, idao.getInvites(amir).size());
	}
	
	@Test
	public void test_CreateInvite_ReturnsNullAndDidNotCreateInvite_GivenInviteeNull() 
			throws DeleteInviteInvitorInviteeMismatchException {
		User imran = udao.createUser("imran.ariffin", "pazzword");
		
		int n = idao.getInvites(imran).size();
		
		Invite inv = idao.createInvite(imran, null);
		
		assertNull(inv);
		assertEquals(n, idao.getInvites(imran).size());
		
		udao.deleteUser(imran);
	}
	
	@Test
	public void test_GetInvites_ReturnsListOfReceivedInvites_GivenUserWhoReceivedAnInvite() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv = idao.createInvite(invitor, invitee);
		
		List<Invite> invites = idao.getInvites(invitee.getUsername());
		
		assertNotNull(invites);
		assertEquals(1, invites.size());
		assertTrue(invites.contains(inv));
	}
	
	@Test
	public void test_GetInvites_ReturnsListOfReceivedInvites_GivenUserWhoSentAnInvite() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv = idao.createInvite(invitor, invitee);
		
		List<Invite> invites = idao.getInvites(invitor.getUsername());
		
		assertNotNull(invites);
		assertEquals(1, invites.size());
		assertTrue(invites.contains(inv));
	}
	
	@Test
	public void test_GetInvites_ReturnsListOfReceivedInvites_GivenUserWhoReceivedAnInviteAndSentOne() {
		String password = "pazzword";
		User imran = udao.createUser("imran.ariffin", password);
		User amir = udao.createUser("amir.ariffin", password);
		
		Invite inv1 = idao.createInvite(imran, amir);
		Invite inv2 = idao.createInvite(amir, imran);
		
		List<Invite> invites = idao.getInvites(imran.getUsername());
		
		assertNotNull(invites);
		assertEquals(2, invites.size());
		assertTrue(invites.contains(inv1));
		assertTrue(invites.contains(inv2));
	}
	
	@Test
	public void test_GetInvite_ReturnsInvite_GivenInviteId() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		assertNotNull(inv);
		assertEquals(inv, idao.getInvite(inv.getInviteId()));
		
		udao.deleteUser(invitor);
		udao.deleteUser(invitee);
	}
	
	@Test
	public void test_GetInvite_ReturnsNull_GivenNonExistingInviteId() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		idao.deleteInvite(invitor, invitee, inv);
		
		assertNotNull(inv);
		assertNull(idao.getInvite(inv.getInviteId()));
	}
	
	@Test
	public void test_GetSentInvites_ReturnsEmptyListOfInvites_GivenUserWhoSentNoInvites() {
		String username = "empty-invitor";
		String password = "pazzword";
		User invitor = udao.createUser(username, password);
		
		List<Invite> invites = idao.getSentInvites(invitor.getUsername()); 
		assertNotNull(invites);
		assertEquals(0, invites.size());
	}
	
	@Test
	public void test_GetSentInvites_ReturnsListOfInvitesOfSizeOne_GivenUserWhoAnInvite() {
		
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		Invite inv = idao.createInvite(invitor, invitee);
		
		List<Invite> invites = idao.getSentInvites(invitor.getUsername()); 
		assertNotNull(invites);
		assertEquals(1, invites.size());
		assertTrue(invites.contains(inv));
	}
	
	@Test
	public void test_GetSentInvites_ReturnsListOfInvitesOfAFewInvites_GivenUserWhoDidAFewInvites() {
		
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv1 = idao.createInvite(invitor, invitee);
		Invite inv2 = idao.createInvite(invitor, invitee);
		Invite inv3 = idao.createInvite(invitor, invitee);
		
		List<Invite> invites = idao.getSentInvites(invitor.getUsername());
		
		assertNotNull(invites);
		assertEquals(3, invites.size());
		assertTrue(invites.contains(inv1));
		assertTrue(invites.contains(inv2));
		assertTrue(invites.contains(inv3));
	}
	
	@Test
	public void test_GetSentInvites_ReturnsListOfInvitesExcludingAcceptedOnes_GivenUser() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv1 = idao.createInvite(invitor, invitee);
		Invite inv2 = idao.createInvite(invitor, invitee);
		Invite inv3 = idao.createInvite(invitor, invitee);
		
		Game game = gdao.createGame(inv3);
		
		List<Invite> invites = idao.getSentInvites(invitor.getUsername());
		
		assertNotNull(invites);
		assertEquals(2, invites.size());
		assertTrue(invites.contains(inv1));
		assertTrue(invites.contains(inv2));
		assertFalse(invites.contains(inv3));
	}
	
	@Test
	public void test_GetReceivedInvites_ReturnsEmptyList_GivenUserWhoReceivedNoInvite() {
		
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		List<Invite> invites = idao.getReceivedInvites(invitee.getUsername());
		
		assertNotNull(invites);
		assertEquals(0, invites.size());
	}
	
	@Test
	public void test_GetReceivedInvites_ReturnsListOfInvitesOfSizeOne_GivenUserWhoReceivedAnInvite() {
		
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv = idao.createInvite(invitor, invitee);
		
		List<Invite> invites = idao.getReceivedInvites(invitee.getUsername());
		
		assertNotNull(invites);
		assertEquals(1, invites.size());
		assertTrue(invites.contains(inv));
	}
	
	@Test
	public void test_GetReceivedInvites_ReturnsListWithAFewInvites_GivenUserWhoReceivedAFewInvites() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv1 = idao.createInvite(invitor, invitee);
		Invite inv2 = idao.createInvite(invitor, invitee);
		Invite inv3 = idao.createInvite(invitor, invitee);
		
		List<Invite> invites = idao.getReceivedInvites(invitee.getUsername());
		
		assertNotNull(invites);
		assertEquals(3, invites.size());
		assertTrue(invites.contains(inv1));
		assertTrue(invites.contains(inv2));
		assertTrue(invites.contains(inv3));
	}
	
	@Test
	public void test_GetReceivedInvites_ReturnsListOfInvitesExcludingAcceptedOnes_GivenUser() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv1 = idao.createInvite(invitor, invitee);
		Invite inv2 = idao.createInvite(invitor, invitee);
		Invite inv3 = idao.createInvite(invitor, invitee);
		
		Game game = gdao.createGame(inv3);
		
		List<Invite> invites = idao.getReceivedInvites(invitee.getUsername());
		
		assertNotNull(invites);
		assertEquals(2, invites.size());
		assertTrue(invites.contains(inv1));
		assertTrue(invites.contains(inv2));
		assertFalse(invites.contains(inv3));
	}
	
	@Test
	public void test_DeleteInvite_RemovesInviteFromDb_GivenInvitorInviteeAndInvite() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		Invite inv = idao.createInvite(invitor, invitee);
		
		idao.deleteInvite(invitor, invitee, inv);
		
		assertNull(idao.getInvite(inv.getInviteId()));
	}
	
	@Test
	public void test_DeleteInvite_ThrowsInvitorInviteeMismatchException_GivenInvitorAndInviteeSwitchedPlaceInArgument() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		try {
			// Invitor and Invitor swapped place as arguments
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
		}
	}
	
	@Test
	public void test_DeleteInvite_RemovesInviteOnlyNotItsInviteeNorInvitor_GivenInvitorId() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		idao.deleteInvite(invitor, invitee, inv);
		
		assertNull(idao.getInvite(inv.getInviteId()));
		assertNotNull(udao.getUser(invitor.getUsername()));
		assertNotNull(udao.getUser(invitee.getUsername()));
		assertFalse(invitor.getSentInvites().contains(inv));
		assertFalse(invitee.getReceivedInvites().contains(inv));
	}
	
	@Test
	public void test_DeleteInvite_RemovesCorrespondingPersistentGame_GivenInvitorId() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentGame pg = gdao.createPersistentGame(inv);
		
		idao.deleteInvite(invitor, invitee, inv);
		
		assertNull(gdao.getPersistentGame(pg.getGameId()));
	}
	
	
	@Test
	public void test_GetAcceptedInvites_ReturnsListOfInvitesAcceptedByEitherParty() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv1 = idao.createInvite(invitor, invitee);
		Invite inv2 = idao.createInvite(invitor, invitee);
		Invite inv3 = idao.createInvite(invitor, invitee);
		
		Game game1 = gdao.createGame(inv1);
		Game game2 = gdao.createGame(inv2);
		Game game3 = gdao.createGame(inv3);
		
		List<Invite> invites = idao.getAcceptedInvites(invitee.getUsername());
		
		assertNotNull(invites);
		assertEquals(3, invites.size());
		assertTrue(invites.contains(inv1));
		assertTrue(invites.contains(inv2));
		assertTrue(invites.contains(inv3));		
	}
	
	@Test
	public void test_GetAcceptedInvites_ReturnsListOfInvitesExcludingUnacceptedOnes() {
		String password = "pazzword";
		User invitor = udao.createUser("invitor", password);
		User invitee = udao.createUser("invitee", password);
		
		Invite inv1 = idao.createInvite(invitor, invitee);
		Invite inv2 = idao.createInvite(invitor, invitee);
		Invite inv3 = idao.createInvite(invitor, invitee);
		
		Game game1 = gdao.createGame(inv1);
		Game game2 = gdao.createGame(inv2);
		
		List<Invite> invites = idao.getAcceptedInvites(invitee.getUsername());
		
		assertNotNull(invites);
		assertEquals(2, invites.size());
		assertTrue(invites.contains(inv1));
		assertTrue(invites.contains(inv2));
		assertFalse(invites.contains(inv3));		
	}
	
	@Test
	public void test_GetInviteByGameId_ReturnsInvite_GivenGameId() 
			throws DeleteInviteInvitorInviteeMismatchException {
		
		User invitor = udao.createUser("invitor", "pazzword");
		User invitee = udao.createUser("invitee", "pazzword");
		Invite inv = idao.createInvite(invitor, invitee);
		
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		PersistentGame pg = gdao.createPersistentGame(inv);
		
		Invite expected = inv;
		Invite actual = idao.getInviteByGameId(pg.getGameId());
		
		assertNotNull(inv);
		assertEquals(expected, actual);
		
		udao.deleteUser(invitor);
		udao.deleteUser(invitee);
	}
}

