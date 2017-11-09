package com.fdmgroup.goclient;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Test;

import com.fdmgroup.goboard.GoGame;

import org.junit.Before;

public class GoClientConsoleTest {
	
	private final ByteArrayOutputStream outContent= new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent= new ByteArrayOutputStream();
	
	@Before
	public void setupUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	
	@After
	public void tearDownStreams() {
		System.setOut(null);
		System.setErr(null);
	}
	
	@Test
	public void test_Console_PrintsWelcomeMessageEmptyBoard_ReturnsUserInput_GivenNewBoard() {
		GoGame goGame = new GoGame();
		GoClientConsole goConsole = new GoClientConsole(goGame, System.in, System.out);
		goConsole.welcome();
		
		String expected = "Welcome to GGGo!\n";
		
		assertEquals(expected, outContent.toString());
	}
}
