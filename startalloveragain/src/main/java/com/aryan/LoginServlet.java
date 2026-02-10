package com.aryan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null ||
            email.isEmpty() || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=1");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String sql =
                "SELECT user_id, name FROM users WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ LOGIN SUCCESS
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", rs.getInt("user_id"));
                session.setAttribute("userName", rs.getString("name"));

                response.sendRedirect("products");

            } else {
                // ❌ LOGIN FAIL
                response.sendRedirect("login.jsp?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
