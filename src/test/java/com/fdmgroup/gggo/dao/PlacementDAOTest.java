package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.W;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.StateDAO;
import com.fdmgroup.gggo.dao.PlacementDAO;
import com.fdmgroup.gggo.exceptions.DeleteInviteInvitorInviteeMismatchException;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;
import com.fdmgroup.gggo.model.User;

public class PlacementDAOTest {

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
	private static PersistentState ps;
	
//	private int r = 3, c = 3, t = 0;
//	private Stone st = B;
//	
	@BeforeClass
	public static void setupOnce() throws DeleteInviteInvitorInviteeMismatchException {
		udao = DAOFactory.getUserDAO();
		idao = DAOFactory.getInviteDAO();
		gdao = DAOFactory.getPersistentGameDAO();
		sdao = DAOFactory.getPersistentStateDAO();
		pdao = DAOFactory.getPlacementDAO();
		
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
		idao.deleteInvite(invitor, invitee, inv);
		gdao.deletePersistentGame(pg, inv);
		sdao.deletePersistentState(pg, ps);
		
	}
	
	@AfterClass
	public static void tearDownOnce() throws DeleteInviteInvitorInviteeMismatchException {
	}
	
	@Before
	public void setup() {
		password = "pazzword";
		invitor = udao.createUser("invitor", password);
		invitee = udao.createUser("invitee", password);
		inv = idao.createInvite(invitor, invitee);
		pg = gdao.createPersistentGame(inv);
		ps = sdao.createEmptyPersistentState(pg.getGameId());
	}
	
	@After
	public void tearDown() throws DeleteInviteInvitorInviteeMismatchException {
		sdao.deletePersistentState(pg, ps);
		gdao.deletePersistentGame(pg, inv);
		idao.deleteInvite(invitor, invitee, inv);
		udao.deleteUser("invitor");
		udao.deleteUser("invitee");
	}
	
	@Test
	public void test_CreatePlacement_ReturnsPlacementWithStateRowColAndTurnNumber_GivenSuch() {
		
		int r = 4;
		int c = 4; 
		Stone st = B;
		Placement expected = pdao.createPlacement(r, c, st, ps);
		Placement actual = pdao.getPlacement(expected.getPlacementId()); 
		
		assertEquals(expected, actual);
		assertEquals(ps, actual.getPersistentState());
	}
	
	@Test
	public void test_CreatePlacement_AppendsToStateListOfPlacements_GivenAGameAndState() {
		
		int n = ps.getPlacements().size();
		pdao.createPlacement(4, 4, B, ps);
		
		assertEquals(n + 1, ps.getPlacements().size());
	}
	
	@Test
	public void test_GetPlacements_ReturnsEmptyList_GivenPersistentStateOfEmptyGame() {
		
		List<Placement> actual = pdao.getPlacements(ps.getStateId());
		
		assertEquals(0, actual.size());
	}
	
	
	@Test
	public void test_GetPlacements_ReturnsAllPlacementWithCorrectStone_GivenState() {
		
		Placement pt1 = pdao.createPlacement(4, 4, B, ps);
		Placement pt2 = pdao.createPlacement(7, 7, W, ps);
		
		List<Placement> placements = pdao.getPlacements(ps.getStateId());
		
		assertTrue(placements.contains(pt1));
		assertTrue(placements.contains(pt2));
	}
	
	@Test
	public void test_DeletePlacement_RemovesAPlacementFromDB_GivenAGameAndAState() {
		
		Placement pt = pdao.createPlacement(4, 4, B, ps);
		
		int n = pdao.getPlacements(ps.getStateId()).size();
		pdao.deletePlacement(ps, pt);
		
		assertEquals(n - 1, pdao.getPlacements(ps.getStateId()).size());
	}
	
	@Test
	public void test_DeletePlacement_RemovesAPlacementFromPersistentStateObject_GivenAGameAndAState() {
		
		Placement pt = pdao.createPlacement(4, 4, B, ps);
		
		int n = ps.getPlacements().size();
		pdao.deletePlacement(ps, pt);
		
		assertEquals(n - 1, ps.getPlacements().size());
	}
}