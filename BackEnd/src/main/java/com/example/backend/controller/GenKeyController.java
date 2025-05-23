package com.example.backend.controller;

import com.example.backend.utils.DigitalSignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@WebServlet("/genKey")
public class GenKeyController extends HttpServlet {

    // Hiển thị trang genKey.jsp khi người dùng truy cập bằng GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("genKey.jsp").forward(request, response);
    }

    // Tạo khóa khi người dùng nhấn nút submit (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            KeyPair keyPair = DigitalSignatureUtil.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Mã hóa Base64 để hiển thị
            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            request.setAttribute("publicKey", publicKeyStr);
            request.setAttribute("privateKey", privateKeyStr);
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi tạo khóa: " + e.getMessage());
        }

        // Hiển thị lại trang JSP với kết quả
        request.getRequestDispatcher("genKey.jsp").forward(request, response);
    }
}
