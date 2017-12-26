package com.fdmgroup.gggo.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.fdmgroup.gggo.dao.InviteDAO;
import com.fdmgroup.gggo.model.Invite;
import com.fdmgroup.gggo.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GGJsonTest {
	
	private static InviteDAO idao;
	
	private Invite inv;
	private Invite inv2;
	private User invitor;
	private User invitee;
	private List<Invite> receivedInvites;
	private List<Invite> sentInvites;
	
	@BeforeClass
	public static void setupOnce() {
		idao = Mockito.mock(InviteDAO.class);
	}
	
	@Before
	public void setup() {
		inv = Mockito.mock(Invite.class);
		inv2 = Mockito.mock(Invite.class);
		
		invitor = Mockito.mock(User.class);
		invitee = Mockito.mock(User.class);
		
		receivedInvites = new ArrayList<>();
		receivedInvites.add(inv);
		receivedInvites.add(inv2);
		
		sentInvites = new ArrayList<>();
		sentInvites.add(inv);
		sentInvites.add(inv2);
		
		Mockito.when(idao.getInvite(1)).thenReturn(inv);
		
		Mockito.when(inv.getInviteId()).thenReturn(1);
		Mockito.when(inv.getInvitor()).thenReturn(invitor);
		Mockito.when(inv.getInvitee()).thenReturn(invitee);
		
		Mockito.when(inv2.getInviteId()).thenReturn(2);
		Mockito.when(inv2.getInvitor()).thenReturn(invitor);
		Mockito.when(inv2.getInvitee()).thenReturn(invitee);
		
		Mockito.when(invitor.getUsername()).thenReturn("saifujiwara");
		Mockito.when(invitee.getUsername()).thenReturn("shindouhikaru");
		
		Mockito.when(invitor.getReceivedInvites()).thenReturn(new ArrayList<>());
		Mockito.when(invitor.getSentInvites()).thenReturn(sentInvites);
		Mockito.when(invitee.getReceivedInvites()).thenReturn(sentInvites);
		Mockito.when(invitee.getSentInvites()).thenReturn(new ArrayList<>());
	}
	
	@Test
	public void test_ToJson_ReturnsValidJsonString_GivenInvite() {
		
		GGJson ggjson = Mockito.spy(new GGJson()); 
		
		String expected = "{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}";
		String actual = ggjson.toJson(inv);
		
		Mockito.verify(ggjson, Mockito.times(1)).toJson(inv);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJson_ReturnsValidJsonString_GivenListOfInvite() {
		List<Invite> invites = new ArrayList<>();
		invites.add(inv);
		invites.add(inv2);
		
		GGJson ggjson = Mockito.spy(new GGJson());
		
		String expected = new StringBuilder()
			.append("[{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}")
			.append(",{\"inviteId\":2,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}]")
		.toString(); 
		String actual = ggjson.toJson(invites);
		
		Mockito.verify(ggjson, Mockito.times(1)).toJson(invites);
		Mockito.verify(ggjson, Mockito.times(1)).toJson(inv);
		Mockito.verify(ggjson, Mockito.times(1)).toJson(inv2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJson_ReturnsValidJsonString_GivenUserWhosIsInvitor() {
		GGJson ggjson = Mockito.spy(new GGJson());
		
		String expected = new StringBuilder()
			.append("{\"username\":\"saifujiwara\",\"sentInvites\":")
				.append("[{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"},")
				.append("{\"inviteId\":2,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}],")
			.append("\"receivedInvites\":")
				.append("[]")
			.append("}")
		.toString();
		String actual = ggjson.toJson(invitor);

		Mockito.verify(ggjson, Mockito.times(1)).toJson(invitor);
		Mockito.verify(ggjson, Mockito.times(1)).toJson(inv);
		Mockito.verify(ggjson, Mockito.times(1)).toJson(inv2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJson_ReturnsValidJsonString_GivenUserWhosIsInvitee() {
		GGJson ggjson = Mockito.spy(new GGJson());
		
		String expected = new StringBuilder()
			.append("{\"username\":\"shindouhikaru\",\"sentInvites\":")
				.append("[],")
			.append("\"receivedInvites\":")
				.append("[{\"inviteId\":1,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"},")
				.append("{\"inviteId\":2,\"invitor\":\"saifujiwara\",\"invitee\":\"shindouhikaru\"}]")
			.append("}")
		.toString();
		String actual = ggjson.toJson(invitee);
		
		Mockito.verify(ggjson, Mockito.times(1)).toJson(invitee);
		Mockito.verify(ggjson, Mockito.times(1)).toJson(inv);
		Mockito.verify(ggjson, Mockito.times(1)).toJson(inv2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_ToJson_ReturnsValidJsonString_GivenListOfUsers() {
		
	}
}
