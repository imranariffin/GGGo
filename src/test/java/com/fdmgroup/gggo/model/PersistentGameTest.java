package com.fdmgroup.gggo.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class PersistentGameTest {

	
	private static PersistentGame existingGameWithStates;
	
	@BeforeClass
	public static void setupOnce () {
	}
	
	@Test
	public void test_Equals_TwoEmptyPersistentGamesShouldBeEquals() {
		PersistentGame pg1 = new PersistentGame();
		PersistentGame pg2 = new PersistentGame();
		
		System.out.println(pg1);
		System.out.println(pg2);
		
		assertEquals(pg1, pg2);
	}
}
