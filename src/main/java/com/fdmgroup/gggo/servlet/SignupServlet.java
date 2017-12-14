package com.fdmgroup.gggo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.gggo.dao.UserDAO;
import com.fdmgroup.gggo.dao.DAO;
import com.fdmgroup.gggo.model.User;

import com.lambdaworks.crypto.SCryptUtil;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/signup.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if (username != null && password != null && confirmPassword != null) {
			if (!username.equals("") && !password.equals("") && !confirmPassword.equals("")) {
				UserDAO udao = DAO.getUserDAO();
				if (udao.getUser(username) == null) {
					if (password.equals(confirmPassword)) {
						
						String hashedPassword = SCryptUtil.scrypt(password, 2 << 13, 3, 7);
						User user = new User(username, hashedPassword);
						udao.postUser(user);
						session.setAttribute(SessionAttributes.CURRENT_USER, user);
						
						RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
						rd.forward(request, response);
					} else {
						sendPasswordsDoNotMatch(request, response);
					}		
				} else {
					sendUsernameNotAvailable(request, response, username);
				}
			} else {
				sendEmptyInputsError(request, response);
			}			
		} else {
			sendInvalidParametersError(request, response);
		}
	}

	private void sendPasswordsDoNotMatch(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color:red;'>Passwords not match</p>");
	}

	private void sendUsernameNotAvailable(HttpServletRequest request, HttpServletResponse response, String username) 
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color:red;'>username " + username + " not available</p>");
	}

	private void sendEmptyInputsError(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color:red;'>Inputs should not be empty</p>");
	}

	private void sendInvalidParametersError(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color:red;'>Invalid parameters given</p>");
	}

}
