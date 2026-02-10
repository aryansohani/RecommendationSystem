package com.aryan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	  private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws IOException {

	        String name = request.getParameter("name");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        // Basic validation
	        if (name == null || email == null || password == null ||
	            name.isEmpty() || email.isEmpty() || password.isEmpty()) {
	            response.sendRedirect("register.jsp?error=1");
	            return;
	        }

	        try (Connection con = DBConnection.getConnection()) {

	            // Check duplicate email
	            String checkSql = "SELECT user_id FROM users WHERE email = ?";
	            PreparedStatement checkPs = con.prepareStatement(checkSql);
	            checkPs.setString(1, email);
	            ResultSet rs = checkPs.executeQuery();

	            if (rs.next()) {
	                // Email already exists
	                response.sendRedirect("register.jsp?error=1");
	                return;
	            }

	            // Insert user
	            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, password); // plain text (OK for final-year demo)

	            ps.executeUpdate();

	            // Success → login page
	            response.sendRedirect("login.jsp");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("register.jsp?error=1");
	        }
	    }
	}