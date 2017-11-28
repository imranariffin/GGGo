package com.fdmgroup.gggo.view;

import java.util.Scanner;

public class AuthView {

	public boolean main() {
		LoginView loginView = new LoginView();
		SignupView signupView = new SignupView();
		Scanner in = new Scanner(System.in);
		
		boolean ret = false;
		
		print(welcome());
		
		while (!ret) {
			print("GGGo:Signup/Login> ");
			String input = in.nextLine();
			switch (input) {
				case "login": 
					ret = loginView.main(); 
					break;
				case "signup": 
					ret = signupView.main(); 
					break;
				case "exit": 
					return false;
				case "help": 
					print(help()); 
					break;
				default: print(help());
			}
		}
		
		return ret;
	}

	private String welcome() {
		return new StringBuilder()
				.append("GGGo:Authentication> Please login or signup:\n")
				.toString();
	}

	private void print(String msg) {
		System.out.print(msg);
	}

	String help() {
		return new StringBuilder()
				.append("GGGO:Signup/Login> \n")
				.append("> login: proceed to login page\n")
				.append("> signup: proceed to signup page\n")
				.append("> exit: back to main page\n")
				.append("> help: show this page\n")
				.toString();
	}
}
