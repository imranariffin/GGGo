package com.fdmgroup.gggo.model;

public class NamedQuerySet {
	public static final String USER_FIND_ALL = "User.findAll";
	public static final String USER_FIND_ONE = "User.findOne";
	public static final String USER_DELETE = "User.delete";
	public static final String USER_UPDATE = "User.update";
	
	public static final String GAME_FIND_ALL = "Game.findAll";
//	public static final String GAME_FIND_ALL_BY_USERID = "Game.findAllByUserId";
	public static final String GAME_FIND_ONE = "Game.findOne";
	
	public static final String PERSISTENT_STATE_FIND_ALL = "PersistentState.findAll";
	public static final String PERSISTENT_STATE_FIND_ONE = "PersistentState.findOne";
	public static final String PERSISTENT_STATE_DELETE = "PersistentState.delete";
	
	public static final String PLACEMENT_FIND_ALL = "Placement.findAll";
	public static final String PLACEMENT_FIND_ONE = "Placement.findOne";
	
	public static final String INVITE_FIND_ALL = "Invite.findAll";
	public static final String INVITE_SENT_FIND_ALL = "Invite.Sent.findAll";
	public static final String INVITE_RECEIVED_FIND_ALL = "Invite.Received.findAll";
	public static final String INVITE_FIND_ONE = "Invite.findOne";
	public static final String INVITE_FIND_ONE_BY_GAMEID = "Invite.findOneByGameId";
	public static final String INVITE_ACCEPTED_FIND_ALL = "Invite.Accepted.findAll";
}
