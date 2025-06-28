package com.example.backend.controller;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.User;
import com.example.backend.models.UserKey;
import com.example.backend.utils.KeyUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.KeyPair;

@WebServlet(name = "KeyGenController", urlPatterns = {"/keygen"})
public class KeyGenController extends HttpServlet {

    private final UserKeyDAO userKeyDAO = new UserKeyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                // Đọc lại private key từ file
                String privateKeyPath = "src/main/webapp/WEB-INF/keys/user_" + userId + "_private.pem";
                String privateKeyPEM = KeyUtil.readPemFile(privateKeyPath);

                request.setAttribute("privateKeyPEM", privateKeyPEM);
                request.setAttribute("publicKeyPEM", existingKey.getPublicKey());
                session.setAttribute("privateKeyPEM", privateKeyPEM);
                session.setAttribute("publicKeyPEM", existingKey.getPublicKey());

                request.getRequestDispatcher("/keygen.jsp").forward(request, response);
                return;
            }

            // Nếu chưa có thì tạo mới
            KeyPair keyPair = KeyUtil.generateKeyPair();

            // Lưu private key vào file
            String privateKeyPath = "src/main/webapp/WEB-INF/keys/user_" + userId + "_private.pem";
            KeyUtil.savePrivateKeyToPemFile(keyPair.getPrivate(), privateKeyPath);

            //  Chuyển public key thành PEM
            String publicKeyPEM = KeyUtil.convertPublicKeyToPem(keyPair.getPublic());

            //  Lưu chỉ public key vào DB
            UserKey userKey = new UserKey(userId, publicKeyPEM); // Không lưu private key
            userKeyDAO.saveKey(userKey);

            // Đặt thuộc tính để hiển thị
            String privateKeyPEM = KeyUtil.readPemFile(privateKeyPath);
            request.setAttribute("privateKeyPEM", privateKeyPEM);
            request.setAttribute("publicKeyPEM", publicKeyPEM);
            session.setAttribute("privateKeyPEM", privateKeyPEM);
            session.setAttribute("publicKeyPEM", publicKeyPEM);

            request.getRequestDispatcher("/keygen.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Lỗi tạo khóa", e);
        }
    }
}
