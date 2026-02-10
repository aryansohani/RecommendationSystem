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

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String search = request.getParameter("search");
        String category = request.getParameter("category");

        List<Product> products = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql =
                "SELECT p.product_id, p.product_name, p.brand, p.category, " +
                "pl.price, pl.product_link " +
                "FROM products p " +
                "JOIN product_listings pl ON p.product_id = pl.product_id " +
                "JOIN (SELECT product_id, MIN(price) min_price " +
                "      FROM product_listings GROUP BY product_id) mp " +
                "ON pl.product_id = mp.product_id AND pl.price = mp.min_price " +
                "WHERE 1=1 ";

            if (search != null && !search.isBlank()) {
                sql += "AND p.product_name LIKE ? ";
            }

            if (category != null && !category.isBlank()) {
                sql += "AND p.category = ? ";
            }

            PreparedStatement ps = con.prepareStatement(sql);

            int index = 1;
            if (search != null && !search.isBlank()) {
                ps.setString(index++, "%" + search + "%");
            }
            if (category != null && !category.isBlank()) {
                ps.setString(index++, category);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setLink(rs.getString("product_link"));
                products.add(p);
            }

            request.setAttribute("products", products);
            request.getRequestDispatcher("products.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading products");
        }
    }
}