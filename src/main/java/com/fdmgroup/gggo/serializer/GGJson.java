package com.fdmgroup.gggo.serializer;

import java.util.List;
import java.util.Map;

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
}
