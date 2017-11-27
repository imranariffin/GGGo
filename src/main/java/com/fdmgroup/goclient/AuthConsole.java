package com.fdmgroup.goclient;

import com.fdmgroup.gggo.model.User;

public class AuthConsole extends Console {

	public AuthConsole() {
		super(System.in, System.out);
	}

	public User run() {
		welcome();
		
		User user = null;
		while (true) {
			String input = console();
			if (input.equals("q")) {
				return null;
			} else if (input.equals("login")) {
				return login();
			} else if (input.equals("signup")) {
				return signup();
			} else { 
				out("Wrong input. Enter 'login' or 'signup'. 'q' to quit to main menu\n");
				break;
			}
		}
		return user;
	}

	@Override
	public String console() {
		out("login/signup:\n");
		out("> ");
		return in.nextLine();
	}

	@Override
	public void welcome() {
		String welcomeMsg = new StringBuilder()
				.append("To get started, please register or login:\n")
				.append("* login: Login\n")
				.append("* signup: Signup\n")
				.toString();
		
		out(welcomeMsg);
	}

	private User signup() {
//		String username = promptUsername();
//		String password = promptPassword();
//		String confirmPassword = promptConfirmPassword();
		return null;
	}
	
	User login() {
		String username = promptUsername();
		String password = promptPassword();
		
		if (username.equals("ia")) {
			if (password.equals("password")) {
				return new User("sai", "Sai", "Fujiwara");
			}
			out("Wrong password. Please try again ...\n");
			password = promptPassword();
			if (password.equals("password")) {
				return new User("sai", "Sai", "Fujiwara");
			}				
		}
		
		if (username.equals("sai")) {
			if (password.equals("password")) {
				return new User("sai", "Sai", "Fujiwara");
			}
			out("Wrong password. Please try again ...\n");
			password = promptPassword();
			if (password.equals("password")) {
				return new User("sai", "Sai", "Fujiwara");
			}
		}
		return null;
	}
	
	String promptUsername() {
		out("Username: ");
		return in.nextLine();
	}
	
	String promptPassword() {
		out("Password: ");
		return in.nextLine();
	}
	
	String promptConfirmPassword() {
		out("Confirm password: ");
		return in.nextLine();
	}
}
