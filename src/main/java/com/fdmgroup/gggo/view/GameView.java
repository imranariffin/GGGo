package com.fdmgroup.gggo.view;

import java.util.Scanner;
import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.GoUtils;
import com.fdmgroup.gggo.dao.DAOFactory;
import com.fdmgroup.gggo.dao.GameDAO;
import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class GameView {

	public void main() {
		
		Scanner in = new Scanner(System.in);
		GameDAO gdao = DAOFactory.getPersistentGameDAO();
		InviteDAO idao = DAOFactory.getInviteDAO();
		UserDAO udao = DAOFactory.getUserDAO();
		
		String password = "pazzword"; 
		User invitor = udao.getUser("invitor");
		invitor = invitor == null ? udao.createUser("invitor", password) : invitor;
		User invitee = udao.getUser("invitee");
		invitee = invitee == null ? udao.createUser("invitee", password) : invitee;
		
		Invite inv = idao.createInvite(invitor, invitee);
		
		Game game = gdao.createGame(inv);
		
		GoCommandHandler cmdHandler = new GoCommandHandler(game);
		
		print(welcome());
		
		while (!game.isFinished()) {
			
			print(GoUtils.toString(game.getBoard()));
			print(console());
			String input = in.nextLine();
			
			switch (input) {
				case "pass":
					cmdHandler.handlePass();
					break;
				case "resign":
					cmdHandler.handleResign();
					break;
				case "back":
					cmdHandler.handleBack();
					break;
				case "first":
					cmdHandler.handleJumpToFirst();
					break;
				case "next":
					cmdHandler.handleForward();
					break;
				case "last":
					cmdHandler.handleJumpToLast();
					break;
				case "exit":
					return;
				case "help":
					print(help());
					break;
				default:
					cmdHandler.handlePlacement(input);
			}
		}
	}

	String console() {
		return "GGGo:Game> ";
	}

	private void print(String msg) {
		System.out.print(msg);
	}

	String welcome() {
		return help();
	}

	String help() {
		return new StringBuilder()
				.append("GGGo:Game\n")
				.append("> [ROW],[COL]: place stone at (ROW,COL) e.g. '4,4'\n")
				.append("> back: go back to previous state of the game, if any\n")
				.append("> next: go to next state of the game, if any\n")
				.append("> first: go back to first state of the game\n")
				.append("> last: go last played played state of the game\n")
				.append("> resign: resign the game\n")
				.append("> pass: pass the turn to opponent\n")
				.append("> help: show this message\n")
				.append("> exit the game, resulting in auto-resignation\n")
				.toString();				
	}
}
