package com.fdmgroup.goclient;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainConsoleTest {

	static PrintStream stdOut = System.out;
	static PrintStream stdErr = System.err;
	private final ByteArrayOutputStream outContent= new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent= new ByteArrayOutputStream();

	private MainConsole console;
	
	@BeforeClass
	public static void setupBeforeClass() {
		
	}
	
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
	public void test_MainConsole_StartsByWelcomingThenPromptsForLoginOrSignup() {
		console = (MainConsole) MainConsole.getInstance();
		
		String actual = new StringBuilder()
				.append("# ----- GGGO v0.1 ----- #\n")
				.append("Welcome to Good Game Go (GGGo), A 2-player Go Client on terminal!\n")
				.append("Please enjoy the game, yoroshiku onegaishimaaasu...!\n")
				.append("\n")
				.append("╔════════════════════════════════════════════╗\n")
				.append("║    A B C D E F G H J K L M N O P Q R S T   ║\n")
				.append("║  1 ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐   ║\n")
				.append("║  2 ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤   ║\n")
				.append("║  3 ├─┼─╬─●─●─●─●─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─┤   ║\n")
				.append("║  4 ├─┼─●─●─●─●─●─●─┼─┼─┼─┼─●─●─●─●─●─┼─┤   ║\n")
				.append("║  5 ├─●─●─●─╬─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  6 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  7 ├─●─●─●─┼─┼─╬─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  8 ├─●─●─●─┼─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║  9 ├─●─●─●─┼─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 10 ├─●─●─●─┼─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 11 ├─●─●─●─┼─┼─●─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 12 ├─●─●─●─┼─●─●─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 13 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 14 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 15 ├─●─●─●─┼─┼─┼─●─●─┼─┼─●─●─●─┼─┼─●─●─┤   ║\n")
				.append("║ 16 ├─┼─●─●─●─●─●─●─┼─┼─┼─┼─●─●─●─●─●─┼─┤   ║\n")
				.append("║ 17 ├─┼─┼─●─●─●─●─┼─┼─┼─┼─┼─┼─●─●─●─┼─┼─┤   ║\n")
				.append("║ 18 ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤   ║\n")
				.append("║ 19 └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘   ║\n")
				.append("║               ---  GGGo  ---               ║\n")
				.append("╚════════════════════════════════════════════╝\n")
				.append("\n")
				.append("To get started please register or login:\n")
				.append("* login: Login\n")
				.append("* signup: Signup\n")
				.append("> ")
				.toString();
//		
//		console.welcome();
//		assertEquals(actual, outContent.toString());
	}
}
