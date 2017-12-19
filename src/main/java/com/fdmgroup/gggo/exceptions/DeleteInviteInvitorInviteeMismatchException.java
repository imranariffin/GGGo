package com.fdmgroup.gggo.exceptions;

public class DeleteInviteInvitorInviteeMismatchException extends Exception {
	private static final long serialVersionUID = -6221236522120619519L;
	private static final String 
		MSG = "Invitor must have sent invite and Invitee must have received Invite, not other way round"; 
	
	public DeleteInviteInvitorInviteeMismatchException() {
		super(MSG);
	}
}
