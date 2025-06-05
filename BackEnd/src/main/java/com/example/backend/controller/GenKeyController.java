package com.example.backend.controller;

import com.example.backend.models.User;
import com.example.backend.services.UserKeyService;
import com.example.backend.utils.SignatureUtil; // Có thể cần xem xét lại import này nếu DigitalSignatureUtil là cũ
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@WebServlet("/gen-key") // Đảm bảo URL path này phù hợp với genKey.jsp của bạn
public class GenKeyController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("auth"); // Kiểm tra tên session attribute của user là gì ("user" hay "auth")

        if (user == null) {
            resp.sendRedirect("auth.jsp"); // Đảm bảo đường dẫn này đúng
            return;
        }

        UserKeyService keyService = new UserKeyService();
        try {
            // Logic tạo và lưu khóa của bạn (gọi service)
            keyService.generateAndSaveKey(user.getId()); // Hàm này sẽ sinh cặp khóa và lưu public key vào DB
            req.setAttribute("message", "Tạo khóa thành công.");
        } catch (Exception e) {
            req.setAttribute("error", "Tạo khóa thất bại: " + e.getMessage());
        }
        req.getRequestDispatcher("genKey.jsp").forward(req, resp); // Đảm bảo đường dẫn này đúng
    }
}