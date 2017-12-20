package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.dao.PersistentGameDAO;
import com.fdmgroup.gggo.dao.PersistentStateDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;
import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.controller.Go;
import com.fdmgroup.gggo.controller.State;

public class StateDAOTest {
	
	private static UserDAO udao;
	private static InviteDAO idao;
	private static PersistentGameDAO gdao;
	private static PersistentStateDAO sdao;
	private static PlacementDAO pdao;
	
	private static String password;
	private static User invitor;
	private static User invitee;
	private static Invite inv;
	private static PersistentGame pg;
	
	@BeforeClass
	public static void setupOnce() throws Exception {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
		gdao = DAOFactory.getPersistentGameDAO();
		sdao = DAOFactory.getPersistentStateDAO();
		pdao = DAOFactory.getPlacementDAO();
		
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		idao.deleteInvite(invitor, invitee, inv);
		gdao.deletePersistentGame(pg, inv);
	}

	@AfterClass
	public static void tearDownOnce() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		password = "pazzword";
		invitor = udao.createUser("invitor", password);
		invitee = udao.createUser("invitee", password);
		inv = idao.createInvite(invitor, invitee);
		pg = gdao.createPersistentGame(inv);
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		gdao.deletePersistentGame(pg, inv);
		idao.deleteInvite(invitor, invitee, inv);
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
	}
	
	@Test
	public void test_CreatePersistentState_ReturnsStateWithZeroNumOfPlacements() {
		PersistentState ps = sdao.createPersistentState(pg);
		
		assertEquals(0, ps.getPlacements().size());
	}
	
	@Test
	public void test_CreateState_SavesStateAndItsPlacements_GivenStateWithPlacements() {
		PersistentState ps = sdao.createPersistentState(pg);
		Placement pt = pdao.createPlacement(4, 4, B, ps);
		
		PersistentState actual = sdao.getPersistentState(ps.getStateId());
		
		assertEquals(1, actual.getPlacements().size());
		assertEquals(pt, actual.getPlacements().get(0));
	}
	
	@Test
	public void test_CreateState_AppendsToGameListOfStates_GivenGame() {
		int n = pg.getPersistentStates().size();
		sdao.createPersistentState(pg);
		
		assertEquals(n + 1, pg.getPersistentStates().size());
	}
	
	@Test
	public void test_GetPersistentStateList_ReturnsEmpty_GivenEmptyGame() {
		assertEquals(0, sdao.getPersistentStateList(pg).size());
	}
	
	@Test
	public void test_DeleteState_RemovesAStateFromDatabase_GivenGameAndState() {
		PersistentState ps = sdao.createPersistentState(pg);
		
		int n = sdao.getPersistentStateList(pg).size();
		sdao.deletePersistentState(pg, ps);
		
		assertEquals(n - 1, sdao.getPersistentStateList(pg).size());
	}
	
	
	@Test
	public void test_DeleteState_RemovesAStateFromGameObject_GivenGameAndState() {
		PersistentState ps = sdao.createPersistentState(pg);
		
		int n = sdao.getPersistentStateList(pg).size();
		sdao.deletePersistentState(pg, ps);
		
		assertEquals(n - 1, pg.getPersistentStates().size());
	}
	
	@Test
	public void test_DeleteState_CatchesNoResultException_GivenStateId() {
	}
	
	private boolean assertContains(List<State> actual, State item) {
		for (Object e: actual) {
			if (e.equals(item)) {
				return true;
			}
		}
		return false;
	}
}