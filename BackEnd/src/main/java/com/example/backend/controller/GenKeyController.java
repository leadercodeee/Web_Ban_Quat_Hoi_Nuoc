package com.example.backend.controller;

import com.example.backend.models.User;
import com.example.backend.services.UserKeyService;
import com.example.backend.utils.DigitalSignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@WebServlet("/gen-key")
public class GenKeyController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("auth");

        if (user == null) {
            resp.sendRedirect("auth.jsp");
            return;
        }

        UserKeyService keyService = new UserKeyService();
        try {
            keyService.generateAndSaveKey(user.getId());
            req.setAttribute("message", "Tạo khóa thành công.");
        } catch (Exception e) {
            req.setAttribute("error", "Tạo khóa thất bại: " + e.getMessage());
        }
        req.getRequestDispatcher("genKey.jsp").forward(req, resp);
    }
}
