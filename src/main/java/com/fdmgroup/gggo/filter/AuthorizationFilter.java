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

	List<String> excludedPaths = new ArrayList<>();
	Pattern excludingPattern = Pattern.compile(
			"(/GGGo/)|" +
			"(/GGGo/font/.*)|" + 
			"(/GGGo/css/.*)|" + 
			"(/GGGo/js/.*)|" + 
			"(/GGGo/api/.*)|" + 
			"(/GGGo/img/.*)|" + 
			"(/GGGo/signup[/]*)|" + 
			"(/GGGo/login[/]*)"
	);
	@Override
	public void destroy() {		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {

		System.out.println("\n=====\nAuthFilter");
		System.out.println(((HttpServletRequest) req).getRequestURI());
		
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String path = request.getRequestURI();
		Matcher m = excludingPattern.matcher(path);
		if (
				(session == null || session.getAttribute(Attributes.Session.CURRENT_USER) == null) 
//				&& !excludedPaths.contains(path)
				&& !m.matches()
				
		) {
			System.out.println(" filtered out!");
			System.out.println("====\n");		
			request.setAttribute(Attributes.Request.PLEASE_LOGIN, ErrorResponse.PLEASE_LOGIN);
			RequestDispatcher rd = request.getRequestDispatcher(Path.Page.LOGIN);
			rd.forward(req, res);
		}
		
		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		excludedPaths.add("/font/*");
		excludedPaths.add("/img/*");
		excludedPaths.add("/css/*");
		excludedPaths.add("/js/go.js");
		excludedPaths.add("/api/*");
		excludedPaths.add("/");
		excludedPaths.add("/home");
		excludedPaths.add("/signup");
		excludedPaths.add("login.jsp");
		excludedPaths.add("/login");
	}

}
