package com.fdmgroup.gggo.view;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.gggo.view.AuthConsole;

public class AuthConsoleTest {

	static PrintStream stdOut = System.out;
	static PrintStream stdErr = System.err;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private AuthConsole console;
	
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
	public void test_AuthConsole_Welcome_PromptsToRegisterOrLogin() {
		console = new AuthConsole();
		console.welcome();
		
		String expectedMsg = new StringBuilder()
				.append("To get started, please register or login:\n")
				.append("* login: Login\n")
				.append("* signup: Signup\n")
				.toString();
		
		assertEquals(expectedMsg, outContent.toString());
	}

	@Test
	public void test_AuthConsole_Login_PromptsForUserNameAndPassword() {
	}
	
	@Test
	public void test_AuthConsole_Login_PromptsForPasswordTwiceIfWrong() {
		
	}
	
	@Test
	public void test_AuthConsole_Login_LinksToMainAuthIfPasswordWrongMoreThanTwice() {
		
	}
}
