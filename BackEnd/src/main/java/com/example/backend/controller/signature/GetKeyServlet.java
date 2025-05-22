package com.example.backend.controller.signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



@WebServlet("/getPublicKey")
public class GetKeyServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/backend";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT public_key FROM users WHERE user_id = ?")) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                response.getWriter().println(rs.getString("public_key"));
            } else {
                response.getWriter().println("Không tìm thấy khóa!");
            }
        } catch (Exception e) {
            response.getWriter().println("Lỗi khi lấy khóa: " + e.getMessage());
        }
    }
}
