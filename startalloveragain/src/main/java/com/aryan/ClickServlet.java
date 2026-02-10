package com.aryan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ClickServlet
 */
@WebServlet("/click")
public class ClickServlet extends HttpServlet {
	   protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException {

	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("userId") == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");
	        int productId = Integer.parseInt(request.getParameter("productId"));

	        try (Connection con = DBConnection.getConnection()) {

	            String sql =
	                "INSERT INTO user_clicks (user_id, product_id, click_count) " +
	                "VALUES (?, ?, 1) " +
	                "ON DUPLICATE KEY UPDATE " +
	                "click_count = click_count + 1, last_clicked = NOW()";

	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, userId);
	            ps.setInt(2, productId);
	            ps.executeUpdate();

	            // ✅ NO REDIRECT FOR LIKE
	            response.setStatus(HttpServletResponse.SC_OK);

	            // 🔍 DEBUG CONFIRMATION
	            System.out.println("LIKE SAVED → user=" + userId + ", product=" + productId);

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        }
	    }
	}
