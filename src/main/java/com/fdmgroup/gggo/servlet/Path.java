package com.fdmgroup.gggo.servlet;

public class Path {
	public class Page {
		public static final String ROOT = "/WEB-INF/views";
		public static final String HOME = ROOT + "/home.jsp";
		public static final String INVITE_SEND = ROOT + "/send-invite.jsp";
		public static final String INVITE_VIEW = ROOT + "/view-invite.jsp";
		public static final String GAME = ROOT + "/game.jsp";
		
		public static final String SIGNUP = ROOT + "/signup.jsp";
		public static final String LOGIN = ROOT + "/login.jsp";
		public static final String LOGOUT = ROOT + "/logout.jsp";
	}
	
	public class Url {
		public static final String ROOT = "/GGGo";
		public static final String HOME = "/home";
		public static final String INVITES = "/invites";
		public static final String INVITE_SEND = "/send-invite";
		public static final String INVITE_ACCEPT = "/invite/accept";
		public static final String INVITE_REJECT = "/invite/reject";
		public static final String GAME = "/game";
		public static final String API = "/api/*";
		
		public static final String SIGNUP = "/signup";
		public static final String LOGIN = "/login";
		public static final String LOGOUT = "/logout";
	}
}
