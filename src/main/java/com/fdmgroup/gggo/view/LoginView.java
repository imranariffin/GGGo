package com.fdmgroup.gggo.view;

import java.util.Scanner;

public class LoginView {

	public boolean main() {
		int numAttempt = 0;
		Scanner in = new Scanner(System.in);
		
		boolean ret = false;
		
		print(welcome());
		
		while (!ret) {
			
			if (numAttempt > 1) {
				print(loginfailed());
				break;
			}
			
			print(console());
			print("Username: ");
			String username = in.nextLine();
			
			print(console());
			print("Password: ");
			String password = in.nextLine();
			
			if (authenticate(username, password)) {
				return true;
			} else {
				print(tryagain());
			}
			
			numAttempt++;
		}
		
		return ret;
	}

	String welcome() {
		return "Please login \n";
	}

	private void print(String console) {
		System.out.print(console);
	}

	String console() {
		return new StringBuilder()
				.append("GGGo:Login> ")
				.toString();
	}

	private boolean authenticate(String username, String password) {
		return true;
	}
	
	String loginfailed() {
		return "Login failed\n";
	}

	String tryagain() {
		return "Authentication error. Please try again\n";
	}

}
