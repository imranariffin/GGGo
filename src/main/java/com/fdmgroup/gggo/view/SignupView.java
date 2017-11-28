package com.fdmgroup.gggo.view;

import java.util.Scanner;

public class SignupView {
	public boolean main() {
		Scanner in = new Scanner(System.in);
		
		String username;
		String password;
		String confirmPassword;
		
		print(console());
		print("Username: ");
		username = in.nextLine();
		if (!validUsername(username)) {
			print("Username " + username + " is invalid.\n");
			return false;
		}
		
		print(console());
		print("Password: ");
		password = in.nextLine();
		
		print(console());
		print("Confirm Password: ");
		confirmPassword = in.nextLine();
		if (!validPassword(password, confirmPassword)) {
			print("Signup Error: Passwords don't match\n");
			return false;
		}
		
		if (validUsername(username) && validPassword(password, confirmPassword)) {
			print("Successfully signed up as " + username + "\n");
			return true;
		}
		return false;
	}
	
	private void print(String msg) {
		System.out.print(msg);
	}

	private String console() {
		return new StringBuilder()
				.append("GGGo:Signup> ")
				.toString();
	}

	private boolean validPassword(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}

	private boolean validUsername(String username) {
		return true;
	}
}