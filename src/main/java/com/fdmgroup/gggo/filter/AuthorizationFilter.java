package com.fdmgroup.gggo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.gggo.servlet.Attributes;
import com.fdmgroup.gggo.servlet.ErrorResponse;
import com.fdmgroup.gggo.servlet.Path;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

	private Pattern excludingPattern;
	
	@Override
	public void destroy() {		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String path = request.getRequestURI();
		Matcher m = excludingPattern.matcher(path);
		if (
				(session == null || session.getAttribute(Attributes.Session.CURRENT_USER) == null) 
				&& !m.matches()
				
		) {
			request.setAttribute(Attributes.Request.PLEASE_LOGIN, ErrorResponse.PLEASE_LOGIN);
			RequestDispatcher rd = request.getRequestDispatcher(Path.Page.LOGIN);
			rd.forward(req, res);
		}
		
		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		 init(Pattern.compile(
					"(/GGGo/)|" +
					"(/GGGo/font/.*)|" + 
					"(/GGGo/css/.*)|" + 
					"(/GGGo/js/.*)|" + 
					"(/GGGo/api/.*)|" + 
					"(/GGGo/img/.*)|" + 
					"(/GGGo/signup[/]*)|" + 
					"(/GGGo/login[/]*)"
			));
	}

	void init(Pattern pattern) {
		excludingPattern = pattern;
	}
}
