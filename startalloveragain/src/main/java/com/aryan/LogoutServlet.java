package com.aryan;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException {

	        HttpSession session = request.getSession(false);

	        if (session != null) {
	            session.invalidate(); // 🔥 destroy session
	        }

	        // Redirect safely to index page
	        response.sendRedirect("index.html");
	    }
	}
