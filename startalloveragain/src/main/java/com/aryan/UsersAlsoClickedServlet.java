package com.aryan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UsersAlsoClickedServlet
 */
@WebServlet("/also-clicked")
public class UsersAlsoClickedServlet extends HttpServlet {
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {

	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("userId") == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        int productId;
	        try {
	            productId = Integer.parseInt(request.getParameter("productId"));
	        } catch (Exception e) {
	            response.sendRedirect("products");
	            return;
	        }

	        List<Product> list = new ArrayList<>();

	        try (Connection con = DBConnection.getConnection()) {

	            String sql =
	                "SELECT p.product_id, p.product_name, p.brand, " +
	                "       MIN(pl.price) AS price, pl.product_link " +
	                "FROM user_clicks uc1 " +
	                "JOIN user_clicks uc2 ON uc1.user_id = uc2.user_id " +
	                "JOIN products p ON uc2.product_id = p.product_id " +
	                "JOIN product_listings pl ON p.product_id = pl.product_id " +
	                "WHERE uc1.product_id = ? " +
	                "AND uc2.product_id <> ? " +
	                "GROUP BY p.product_id, p.product_name, p.brand, pl.product_link " +
	                "ORDER BY SUM(uc2.click_count) DESC " +
	                "LIMIT 10";

	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, productId);
	            ps.setInt(2, productId);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Product p = new Product();
	                p.setProductId(rs.getInt("product_id"));
	                p.setProductName(rs.getString("product_name"));
	                p.setBrand(rs.getString("brand"));
	                p.setPrice(rs.getDouble("price"));
	                p.setLink(rs.getString("product_link"));
	                list.add(p);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        request.setAttribute("products", list);
	        request.getRequestDispatcher("also-clicked.jsp").forward(request, response);
	    }
	}