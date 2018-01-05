package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.StateDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;
import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.controller.Stone;

public class StateDAOTest {
	
	private static UserDAO udao;
	private static InviteDAO idao;
	private static GameDAO gdao;
	private static StateDAO sdao;
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
	public void test_CreateEmptyPersistentState_ReturnsStateWithEmptyPlacementList_GivenGameId() {
		PersistentState ps = sdao.createEmptyPersistentState(pg.getGameId());
		
		assertEquals(0, ps.getPlacements().size());
	}
	
	@Test
	public void test_CreatePersistentState_ReturnsStateWithPlacementsOfSizeOne_GivenGameIdTurnNumberAndPlacementInfo() {
		int r = 3, c = 3, t = 0;
		PersistentState ps = sdao.createPersistentState(pg.getGameId(), r, c, t, B);
		
		assertEquals(1, ps.getPlacements().size());
		assertEquals(r, ps.getPlacements().get(0).getRowNumber());
		assertEquals(c, ps.getPlacements().get(0).getColNumber());
		assertEquals(B, ps.getPlacements().get(0).getStone());
	}
	
	@Test
	public void test_CreatePersistentState_SavesStateAndItsPlacements_GivenStateWithPlacements() {
		int r = 3, c = 3, t = 0;
		PersistentState ps = sdao.createPersistentState(pg.getGameId(), r, c, t, B);
		Placement pt = pdao.createPlacement(4, 4, W, ps);
		
		PersistentState actual = sdao.getPersistentState(ps.getStateId());
		
		assertEquals(2, actual.getPlacements().size());
		assertTrue(actual.getPlacements().contains(pt));
	}
	
	@Test
	public void test_GetPersistentStateList_ReturnsEmpty_GivenEmptyGame() {
		assertEquals(0, sdao.getPersistentStateList(pg.getGameId()).size());
	}
	
	@Test
	public void test_DeletePersistentState_RemovesAStateFromDatabase_GivenGameAndState() {
		int r = 3, c = 3, t = 0;
		PersistentState ps = sdao.createPersistentState(pg.getGameId(), r, c, t, B);
		
		int n = sdao.getPersistentStateList(pg.getGameId()).size();
		sdao.deletePersistentState(pg, ps);
		
		assertEquals(n - 1, sdao.getPersistentStateList(pg.getGameId()).size());
	}
	
	
	@Test
	public void test_DeletePersistentState_RemovesAStateFromGameObject_GivenGameAndState() {
		int r = 3, c = 3, t = 0;
		PersistentState ps = sdao.createPersistentState(pg.getGameId(), r, c, t, B);
		
		int n = sdao.getPersistentStateList(pg.getGameId()).size();
		sdao.deletePersistentState(pg, ps);
		
		assertEquals(n - 1, pg.getPersistentStates().size());
	}
	
	@Test
	public void test_DeletePersistentState_CatchesNoResultException_GivenStateId() {
	}
	
	private boolean assertContains(List<State> actual, State item) {
		for (Object e: actual) {
			if (e.equals(item)) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void 
	test_CreateState_CreatesPersistentStateInDb_GivenGameIPosJPosTurnNumberAndStone() {
		Invite invite = idao.createInvite(invitor, invitee);
		Game game = gdao.createGame(invite);
		PersistentGame pg = gdao.getPersistentGame(game.getGameId());
		int r = 3, c = 3, t = 0;
		Stone st = B;
		
		int n = sdao.getPersistentStateList(pg.getGameId()).size();
		
		State ps = sdao.createState(game, 3, 3, t, B);
		
		assertEquals(n + 1, sdao.getPersistentStateList(pg.getGameId()).size());
	}
	
//	@Test
//	public void test_CreateState_CreatesAStateBasedOnCurrentPlacementAndPreviousPlacements() {
//		Invite invite = idao.createInvite(invitor, invitee);
//		Game game = gdao.createGame(invite);
//		PersistentGame pg = gdao.getPersistentGame(game.getGameId());
//		int t = 0;
//		
//		int n = sdao.getPersistentStateList(pg.getGameId()).size();
//		
//		State ps1 = sdao.createState(game, 3, 3, t, B);
//		State ps2 = sdao.createState(game, 6, 6, t + 1, W);
//		State ps3 = sdao.createState(game, 6, 6, t + 2, B);
//		State ps4 = sdao.createState(game, 6, 6, t + 3, W);
//		
//		assertEquals(1, pdao.getPlacements(ps1.getStateId()).size());
////		assertEquals(2, pdao.getPlacements(ps2.getStateId()).size());
//		assertEquals(3, pdao.getPlacements(ps3.getStateId()).size());
//		assertEquals(4, pdao.getPlacements(ps4.getStateId()).size());
//	}
}