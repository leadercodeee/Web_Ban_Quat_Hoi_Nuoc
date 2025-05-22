package com.example.backend.controller.signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Base64;

@WebServlet("/regenerateKey")
public class RegenerateKeyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.getWriter().println("Bạn chưa đăng nhập!");
            return;
        }

        try {
            // Tạo khóa mới
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            String publicKeyStr = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "password");
                 PreparedStatement stmt = conn.prepareStatement("UPDATE users SET public_key = ? WHERE user_id = ?")) {

                stmt.setString(1, publicKeyStr);
                stmt.setString(2, userId);
                stmt.executeUpdate();
            }

            try (FileOutputStream fos = new FileOutputStream("private_" + userId + ".key")) {
                fos.write(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
            }

            response.getWriter().println("Khóa mới đã được tạo!");
        } catch (Exception e) {
            response.getWriter().println("Lỗi khi tạo lại khóa: " + e.getMessage());
        }
    }
}

