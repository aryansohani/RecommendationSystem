package com.aryan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CompareServlet
 */
@WebServlet("/compare")
public class CompareServlet extends HttpServlet {
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException {

	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("userId") == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        int productId = Integer.parseInt(request.getParameter("productId"));
	        List<Product> compareList = new ArrayList<>();

	        try (Connection con = DBConnection.getConnection()) {

	            String sql =
	                "SELECT p.product_name, p.brand, w.website_name, " +
	                "pl.price, pl.product_link " +
	                "FROM product_listings pl " +
	                "JOIN products p ON pl.product_id = p.product_id " +
	                "JOIN websites w ON pl.website_id = w.website_id " +
	                "WHERE p.product_id = ? " +
	                "ORDER BY pl.price ASC";

	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, productId);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Product p = new Product();
	                p.setProductName(rs.getString("product_name"));
	                p.setBrand(rs.getString("website_name")); // using brand field to show website
	                p.setPrice(rs.getDouble("price"));
	                p.setLink(rs.getString("product_link"));
	                compareList.add(p);
	            }

	            request.setAttribute("compareList", compareList);
	            request.setAttribute("productId", productId);
	            request.getRequestDispatcher("compare.jsp").forward(request, response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().println("Compare error");
	        }
	    }
	}
