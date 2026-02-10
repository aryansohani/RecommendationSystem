package com.aryan;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestConnectionServlet
 */
@WebServlet("/TestConnectionServlet")
public class TestConnectionServlet extends HttpServlet {

	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException {

	        try {
	            Connection con = DBConnection.getConnection();

	            if (con != null) {
	                response.getWriter().println("DB CONNECTION SUCCESS ✅");
	            } else {
	                response.getWriter().println("DB CONNECTION FAILED ❌");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().println("ERROR: " + e.getMessage());
	        }
	    }
	}