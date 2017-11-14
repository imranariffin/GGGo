package com.fdmgroup.goclient;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import com.fdmgroup.goboard.GoGame;

import org.junit.Before;

public class GoClientConsoleTest {
	
	static PrintStream stdOut = System.out;
	static PrintStream stdErr = System.err;
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
	
	@AfterClass
	public static void resetStreams() {
		System.setOut(stdOut);
		System.setErr(stdErr);
	}
	
	@Test
	public void test_Console_PrintsWelcomeMessageEmptyBoard_ReturnsUserInput_GivenNewBoard() {
		GoGame goGame = new GoGame();
		new GoClient(goGame);
		GoClientConsole goConsole = GoClient.getConsole("MAIN");
		goConsole.welcome();
		
		String expected = "Welcome to GGGo!\n"+
				"P: Start new game\n" + 
				"V: View past games\n" + 
				"----------------------\n";
		
		assertEquals(expected, outContent.toString());
	}
}
