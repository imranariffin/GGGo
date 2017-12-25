package com.fdmgroup.gggo.serializer;

import java.util.List;
import java.util.Map;

import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;

public class GGJson {
	
	public static String toJson(List<Invite> invites) {
		StringBuilder json = new StringBuilder().append("[");
		
		for (int i = 0; i < invites.size(); i++) {
			Invite inv = invites.get(i);
			json.append(toJson(inv));
			if (i < invites.size() - 1) {
				json = json.append(",");
			}
		}
		json.append("]");
		
		return json.toString();
	}
	
	public static String toJson(Invite inv) {
		StringBuilder json = new StringBuilder(); 
		
		json
		.append("{")
			.append("\"inviteId\": " + inv.getInviteId() + ",")
			.append("\"invitee\": \"" + inv.getInvitee().getUsername() + "\",")
			.append("\"invitor\": \"" + inv.getInvitor().getUsername() + "\"")
		.append("}");
		
		return json.toString();
	}
	
	public static String toJson(Map<String, User> users) {
		
		StringBuilder json = new StringBuilder().append("[");
		int k = 0;
		for (String username: users.keySet()) {
			User user = users.get(username);
		
			json.append(toJson(user));
			
			if (k++ < users.keySet().size() - 1) {
				json = json.append(",");
			}
		}
		json = json.append("]");
		
		return json.toString();
	}
	
	public static String toJson(User user) {
		StringBuilder json = new StringBuilder();
		
		String receivedInvites = toJson(user.getReceivedInvites());
		String sentInvites = toJson(user.getSentInvites());
		json = json
				.append("{")
					.append("\"username\": \"" + user.getUsername() + "\",")
					.append("\"receivedInvites\": " + receivedInvites.toString() + ",")
					.append("\"sentInvites\": " + sentInvites.toString())
				.append("}");
		
		return json.toString();
	}
}
