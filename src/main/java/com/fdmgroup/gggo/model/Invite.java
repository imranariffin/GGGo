package com.fdmgroup.gggo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name=GoEntities.TBL_GO_INVITE)
@NamedQueries({
	@NamedQuery(
			name=NamedQuerySet.INVITE_FIND_ALL, 
			query="select inv from Invite inv where inv.invitee = :user or inv.invitor = :user"),
	@NamedQuery(
			name=NamedQuerySet.INVITE_FIND_ONE, 
			query="select inv from Invite inv where inv.inviteId = :invid"),
})
public class Invite {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int inviteId;

	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private User invitor;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private User invitee;
	
//	@OneToOne(orphanRemoval=true, fetch=FetchType.EAGER)
//	private PersistentGame game;
	
	public Invite() {}

	public User getInvitor() {
		return invitor;
	}

	public void setInvitor(User invitor) {
		this.invitor = invitor;
	}

	public User getInvitee() {
		return invitee;
	}

	public void setInvitee(User invitee) {
		this.invitee = invitee;
	}

//	public PersistentGame getGame() {
//		return game;
//	}
//
//	public void setGame(PersistentGame game) {
//		this.game = game;
//	}

	public int getInviteId() {
		return inviteId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + inviteId;
		result = prime * result + ((invitee == null) ? 0 : invitee.hashCode());
		result = prime * result + ((invitor == null) ? 0 : invitor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invite other = (Invite) obj;
		if (inviteId != other.inviteId)
			return false;
		if (invitee == null) {
			if (other.invitee != null)
				return false;
		} else if (!invitee.equals(other.invitee))
			return false;
		if (invitor == null) {
			if (other.invitor != null)
				return false;
		} else if (!invitor.equals(other.invitor))
			return false;
		return true;
	}
}
