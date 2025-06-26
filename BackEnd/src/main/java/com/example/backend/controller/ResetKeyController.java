package com.example.backend.controller;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.User;
import com.example.backend.utils.KeyUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.KeyPair;

@WebServlet(name = "ResetKeyController", urlPatterns = {"/reset-key"})
public class ResetKeyController extends HttpServlet {

    private final UserKeyDAO userKeyDAO = new UserKeyDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("auth.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            // Xóa khóa cũ
            userKeyDAO.deleteKeyByUserId(user.getId());

            // Tạo cặp khóa mới
            KeyPair keyPair = KeyUtil.generateKeyPair();

            // Lưu khóa mới vào database (đã chuyển Base64 bên trong)
         //   userKeyDAO.saveUserKey(user.getId(), keyPair.getPrivate(), keyPair.getPublic());

            // Chuyển khóa sang định dạng PEM để hiển thị / download dễ dàng
            String privateKeyPEM = toPEM("PRIVATE KEY", keyPair.getPrivate().getEncoded());
            String publicKeyPEM = toPEM("PUBLIC KEY", keyPair.getPublic().getEncoded());

            // Đưa dữ liệu lên request
            request.setAttribute("privateKeyPEM", privateKeyPEM);
            request.setAttribute("publicKeyPEM", publicKeyPEM);
            request.setAttribute("message", "🔑 Khóa đã được tạo lại thành công.");

            // Forward sang trang hiển thị khóa (ví dụ showKey.jsp)
            request.getRequestDispatcher("showKey.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Lỗi khi reset khóa và tạo mới", e);
        }
    }

    // Hàm hỗ trợ chuyển byte[] key thành PEM format string
    private String toPEM(String type, byte[] encodedKey) {
        String base64 = java.util.Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(encodedKey);
        return "-----BEGIN " + type + "-----\n" + base64 + "\n-----END " + type + "-----";
    }
}
