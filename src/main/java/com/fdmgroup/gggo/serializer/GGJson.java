package com.fdmgroup.gggo.serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fdmgroup.gggo.controller.Game;
import com.fdmgroup.gggo.controller.State;
import com.fdmgroup.gggo.controller.Stone;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class GGJson {

	public String toJsonInviteList(List<Invite> invites) {
		StringBuilder json = new StringBuilder().append("[");
		
		for (int i = 0; i < invites.size(); i++) {
			Invite inv = invites.get(i);
			json.append(toJsonInvite(inv));
			if (i < invites.size() - 1) {
				json = json.append(",");
			}
		}
		json.append("]");
		
		return json.toString();
	}
	
	public String toJsonInvite(Invite inv) {
		StringBuilder json = new StringBuilder(); 
		
		json
		.append("{")
			.append("\"inviteId\":" + inv.getInviteId() + ",")
			.append("\"invitor\":\"" + inv.getInvitor().getUsername() + "\",")
			.append("\"invitee\":\"" + inv.getInvitee().getUsername() + "\"")
		.append("}");
		
		return json.toString();
	}
	
	public String toJsonUserList(List<User> users) {
		
		StringBuilder json = new StringBuilder().append("[");
		int k = 0;
		for (User user: users) {
		
			json = json.append(toJsonUser(user));
			
			if (k++ < users.size() - 1) {
				json = json.append(",");
			}
		}
		json = json.append("]");
		
		return json.toString();
	}
	
	public String toJsonUser(User user) {
		StringBuilder json = new StringBuilder();
		
		String receivedInvites = toJsonInviteList(user.getReceivedInvites());
		String sentInvites = toJsonInviteList(user.getSentInvites());
		json = json
				.append("{")
					.append("\"username\":\"" + user.getUsername() + "\",")
					.append("\"sentInvites\":" + sentInvites.toString() + ",")
					.append("\"receivedInvites\":" + receivedInvites.toString())
				.append("}");
		
		return json.toString();
	}

	public String toJsonGameList(List<Game> userGameList) {
		StringBuilder json = new StringBuilder().append("[");
		
		for (Game game: userGameList) {
			json.append("{")
				.append("\"gameId\": \"" + game.getGameId() + "\",")
				.append("\"black\": \"" + game.getBlack().getUsername() + "\",")
				.append("\"white\": \"" + game.getWhite().getUsername() + "\",")
				.append("\"states\": [");
			for (State s: game.getStates()) {
				json.append("{")
					.append("\"stateId\": " + s.getStateId() + ",")
					.append("\"turn\": " + s.getTurn() + ",")
					.append("\"board\": " + "[");
				Stone[][] board = s.getBoard();
				for (int i = 0; i < board.length; i++) {
					json.append("[");
					for (int j = 0; j < board.length; j++) {
						String st = board[i][j].toString(); 
						json.append(st);
						if (j < board.length - 1) json.append(",");
					}
					json.append("]");
					if (i < board.length - 1) json.append(",");
				}
				json.append("}");
				
				State last = game.getStates().get(game.getStates().size() - 1);
				if (!s.equals(last)) json.append(",");
			}
				json.append("],")
				.append("\"futureStates\": [");
				
				json.append("]")
			.append("}");
			
			Game last = userGameList.get(userGameList.size() - 1);
			if (!game.equals(last)) {
				json.append(",");
			}
		}
		json.append("]");
				
		return json.toString();
	}

	public String toJsonStateList(List<State> states) {
		StringBuilder json = new StringBuilder().append("[");
		
		for (State s: states) {
		
			json = json.append(toJsonState(s));
			
			State last = states.get(states.size() - 1);
			if (!s.equals(last)) { json = json.append(","); }
		}
		json = json.append("]");
		
		return json.toString();
	}

	private String toJsonState(State s) {
		return "";
	}
}
