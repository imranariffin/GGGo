package com.fdmgroup.gggo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name=GoEntities.TBL_GO_USER)
@NamedQueries({
	@NamedQuery(name=NamedQuerySet.USER_FIND_ALL, query="select u from User u"),
	@NamedQuery(name=NamedQuerySet.USER_DELETE, query="delete from User where username = :uname"),
	@NamedQuery(name=NamedQuerySet.USER_FIND_ONE, query="select u from User u where u.username = :uname"),
	@NamedQuery(name=NamedQuerySet.USER_UPDATE, query="update User u set u.password = :newpw where u.username = :uname"),
})
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	
	@Column(name="username", nullable=false, length=50, unique=true)
	private String username;
	
	@Column(name="password", nullable=false, length=200)
	private String password;
	
	public User() { 
	}
	
	public User(String uname, String pw) {
		username = uname;
		password = pw;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + userId;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId != other.userId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
