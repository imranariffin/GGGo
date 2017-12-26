package com.fdmgroup.gggo.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.fdmgroup.gggo.model.User;
import com.fdmgroup.gggo.servlet.Attributes;

@WebListener
public class UserOnlineListener implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		System.out.println("Listening to session.add(" + e.getName() + ", " + e.getValue() + ")");
		String attribute = e.getName();
		switch (attribute) {
			case Attributes.CURRENT_USER:
				User user = (User) e.getValue();
				ServletContext context = e.getSession().getServletContext();
				handleUserLogin(context, user);
				break;
			default:
				break;
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent e) {
		System.out.println("Listening to session.remove(" + e.getName() + ", " + e.getValue() + ")");
		String attribute = e.getName();
		
		switch (attribute) {
			case Attributes.CURRENT_USER:
				User user = (User) e.getValue();
				ServletContext context = e.getSession().getServletContext();
				handleUserLogout(context, user);
				break;
			default: break;
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent e) {
		// TODO Auto-generated method stub
	}
	
	private void handleUserLogin(ServletContext context, User user) {
		@SuppressWarnings("unchecked")
		HashMap<String, User> onlineUsers = context.getAttribute("online-users") == null 
				? new HashMap<>()
				: (HashMap<String, User>) context.getAttribute("online-users");
		onlineUsers.put(user.getUsername(), user);
		context.setAttribute("online-users", onlineUsers);		
	}

	private void handleUserLogout(ServletContext context, User user) {
		@SuppressWarnings("unchecked")
		HashMap<String, User> onlineUsers = (HashMap<String, User>) context.getAttribute("online-users");
		onlineUsers.remove(user.getUsername());
		context.setAttribute("online-users", onlineUsers);
	}
}
