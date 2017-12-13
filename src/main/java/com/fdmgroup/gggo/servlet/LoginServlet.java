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

import com.fdmgroup.gggo.controller.UserDAO;
import com.fdmgroup.gggo.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		UserDAO sdao = UserDAO.getInstance();
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (!username.equals("") && !password.equals("")) {
			User user = sdao.getUser(username);
			if (user != null) {
				if (user.getPassword().equals(password)) {
					session.setAttribute("currentUser", user);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
					rd.forward(request, response);
				} else {
					sendPasswordDoesNotMatchError(request, response);
				}
			} else {
				sendUsernameDoesNotExistError(request, response);
			}
		} else {
			sendPasswordAndUserNameEmptyError(request, response);
		}
	}

	private void sendPasswordDoesNotMatchError(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color:red;'>Password does not match</p>");
	}

	private void sendUsernameDoesNotExistError(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color:red;'>Username does not exist</p>");
	}

	private void sendPasswordAndUserNameEmptyError(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<p style='color:red;'>Username or password cannot be empty</p>");
	}
}
