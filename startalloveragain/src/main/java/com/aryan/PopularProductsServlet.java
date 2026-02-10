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

/**
 * Servlet implementation class PopularProductsServlet
 */
@WebServlet("/popular")
public class PopularProductsServlet extends HttpServlet {
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {

	        List<Product> list = new ArrayList<>();

	        try (Connection con = DBConnection.getConnection()) {

	            String sql =
	                "SELECT p.product_id, p.product_name, p.brand, " +
	                "       MIN(pl.price) AS price, pl.product_link " +
	                "FROM user_clicks uc " +
	                "JOIN products p ON uc.product_id = p.product_id " +
	                "JOIN product_listings pl ON p.product_id = pl.product_id " +
	                "GROUP BY p.product_id, p.product_name, p.brand, pl.product_link " +
	                "ORDER BY SUM(uc.click_count) DESC " +
	                "LIMIT 10";

	            PreparedStatement ps = con.prepareStatement(sql);
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
	        request.getRequestDispatcher("popular.jsp").forward(request, response);
	    }
	}
