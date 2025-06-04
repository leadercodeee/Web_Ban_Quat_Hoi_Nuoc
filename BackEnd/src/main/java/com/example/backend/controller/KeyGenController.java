package com.example.backend.controller;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.User;
import com.example.backend.models.UserKey;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.*;
import java.util.Base64;

@WebServlet(name = "KeyGenController", urlPatterns = {"/keygen"})
public class KeyGenController extends HttpServlet {

    private final UserKeyDAO userKeyDAO = new UserKeyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra người dùng đã đăng nhập chưa
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("auth.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        try {
            // Kiểm tra nếu đã có khóa
            UserKey existingKey = userKeyDAO.getKeyByUserId(userId);
            if (existingKey != null) {
                request.setAttribute("privateKeyPEM", existingKey.getPrivateKey());
                request.setAttribute("publicKeyPEM", existingKey.getPublicKey());
                session.setAttribute("privateKeyPEM", existingKey.getPrivateKey());
                session.setAttribute("publicKeyPEM", existingKey.getPublicKey());
                request.getRequestDispatcher("/keygen.jsp").forward(request, response);
                return;
            }

            // Nếu chưa có thì tạo mới
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            String privateKeyPEM = convertToPEM(pair.getPrivate(), "PRIVATE KEY");
            String publicKeyPEM = convertToPEM(pair.getPublic(), "PUBLIC KEY");

            // Lưu vào CSDL
            UserKey userKey = new UserKey(userId, publicKeyPEM, privateKeyPEM);
            userKeyDAO.saveKey(userKey);

            // Đặt thuộc tính để hiển thị
            request.setAttribute("privateKeyPEM", privateKeyPEM);
            request.setAttribute("publicKeyPEM", publicKeyPEM);
            session.setAttribute("privateKeyPEM", privateKeyPEM);
            session.setAttribute("publicKeyPEM", publicKeyPEM);

            request.getRequestDispatcher("/keygen.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Lỗi tạo khóa", e);
        }
    }

    private String convertToPEM(Key key, String type) {
        String base64 = Base64.getEncoder().encodeToString(key.getEncoded());
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append(type).append("-----\n");
        int i = 0;
        while (i < base64.length()) {
            pem.append(base64, i, Math.min(i + 64, base64.length())).append("\n");
            i += 64;
        }
        pem.append("-----END ").append(type).append("-----");
        return pem.toString();
    }
}
