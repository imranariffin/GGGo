package com.fdmgroup.gggo.dao;

import static com.fdmgroup.gggo.controller.Stone.E;
import static com.fdmgroup.gggo.controller.Stone.B;
import static com.fdmgroup.gggo.controller.Stone.W;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.dao.PersistentGameDAO;
import com.fdmgroup.gggo.dao.PersistentStateDAO;
import com.fdmgroup.gggo.dao.PlacementDAO;
import com.fdmgroup.gggo.exceptions.InvalidPlacementException;
import com.fdmgroup.gggo.model.PersistentGame;
import com.fdmgroup.gggo.model.PersistentState;
import com.fdmgroup.gggo.model.Placement;

public class PlacementDAOTest {

	private static PersistentGameDAO gdao;
	private static PersistentStateDAO sdao;
	private static PlacementDAO pdao;
	
	@BeforeClass
	public static void setupOnce() {
		gdao = DAO.getPersistentGameDAO();
		sdao = DAO.getPersistentStateDAO();
		pdao = DAO.getPlacementDAO();
	}
	
	@AfterClass
	public static void tearDownOnce() {
	}
	
	@Test
	public void test_CreatePlacement_ReturnsPlacementWithStateRowColAndTurnNumber_GivenSuch() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		
		int r = 4;
		int c = 4; 
		Stone st = B;
		Placement expected = pdao.createPlacement(r, c, st, ps);
		Placement actual = pdao.getPlacement(expected.getPlacementId()); 
		
		assertEquals(expected, actual);
		assertEquals(ps, actual.getPersistentState());
		assertEquals(pg, actual.getPersistentState().getPersistentGame());
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_CreatePlacement_AppendsToStateListOfPlacements_GivenAGameAndState() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		
		int n = ps.getPlacements().size();
		pdao.createPlacement(4, 4, B, ps);
		
		assertEquals(n + 1, ps.getPlacements().size());
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_GetPlacements_ReturnsEmptyList_GivenPersistentStateOfEmptyGame() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg); 
		List<Placement> actual = pdao.getPlacements(ps.getStateId());
		
		assertEquals(0, actual.size());
		
		gdao.deletePersistentGame(pg);
	}
	
	
	@Test
	public void test_GetPlacements_ReturnsAllPlacementWithCorrectStone_GivenState() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		Placement pt1 = pdao.createPlacement(4, 4, B, ps);
		Placement pt2 = pdao.createPlacement(7, 7, W, ps);
		
		List<Placement> placements = pdao.getPlacements(ps.getStateId());
		assertTrue(placements.contains(pt1));
		assertTrue(placements.contains(pt2));
	}
	
	@Test
	public void test_DeletePlacement_RemovesAPlacementFromDB_GivenAGameAndAState() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		Placement pt = pdao.createPlacement(4, 4, B, ps);
		
		
		int n = pdao.getPlacements(ps.getStateId()).size();
		pdao.deletePlacement(ps, pt);
		
		assertEquals(n - 1, pdao.getPlacements(ps.getStateId()).size());
		
		gdao.deletePersistentGame(pg);
	}
	
	@Test
	public void test_DeletePlacement_RemovesAPlacementFromPersistentStateObject_GivenAGameAndAState() {
		PersistentGame pg = gdao.createPersistentGame();
		PersistentState ps = sdao.createPersistentState(pg);
		Placement pt = pdao.createPlacement(4, 4, B, ps);
		
		
		int n = ps.getPlacements().size();
		pdao.deletePlacement(ps, pt);
		
		assertEquals(n - 1, ps.getPlacements().size());
		
		gdao.deletePersistentGame(pg);
	}
}