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

@WebServlet("/recommendations")
public class RecommendedServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            // 🔥 Find user's most liked category
            String catSql =
                "SELECT p.category " +
                "FROM user_clicks uc " +
                "JOIN products p ON uc.product_id = p.product_id " +
                "WHERE uc.user_id = ? " +
                "GROUP BY p.category " +
                "ORDER BY SUM(uc.click_count) DESC " +
                "LIMIT 1";

            PreparedStatement cps = con.prepareStatement(catSql);
            cps.setInt(1, userId);
            ResultSet crs = cps.executeQuery();

            String category = null;
            if (crs.next()) {
                category = crs.getString("category");
            }

            // 🔥 Recommend products (lowest price per product)
            String productSql =
                "SELECT p.product_id, p.product_name, p.brand, pl.price, pl.product_link " +
                "FROM products p " +
                "JOIN product_listings pl ON p.product_id = pl.product_id " +
                "JOIN ( " +
                "   SELECT product_id, MIN(price) AS min_price " +
                "   FROM product_listings " +
                "   GROUP BY product_id " +
                ") mp ON pl.product_id = mp.product_id AND pl.price = mp.min_price " +
                (category != null ? "WHERE p.category = ? " : "") +
                "LIMIT 10";

            PreparedStatement ps = con.prepareStatement(productSql);
            if (category != null) {
                ps.setString(1, category);
            }

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

            request.setAttribute("products", list);
            request.getRequestDispatcher("recommend.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Recommendation error");
        }
    }
}
