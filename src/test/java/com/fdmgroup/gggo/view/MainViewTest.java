package com.fdmgroup.gggo.view;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainViewTest {
	static PrintStream stdOut = System.out;
	static PrintStream stdErr = System.err;
	private final ByteArrayOutputStream outContent= new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent= new ByteArrayOutputStream();

	private static MainView mainView;
	
	@BeforeClass
	public static void setupBeforeClass() {
		mainView = new MainView();
	}
	
	@Before
	public void setupUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	@Test
	public void test_Welcome_ReturnsBeautifulWelcomeStringTextWithGoBoardAndSomeWelcomeMessages() {
		
		String expected = new StringBuilder()
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
				.toString();
		
		assertEquals(expected, mainView.welcome());
	}
}
