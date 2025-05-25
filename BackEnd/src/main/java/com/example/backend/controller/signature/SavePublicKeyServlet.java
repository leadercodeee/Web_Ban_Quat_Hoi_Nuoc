package com.example.backend.controller.signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/save-public-key")
public class SavePublicKeyServlet extends HttpServlet {
    private static final String SAVE_PATH = "C:/keys/public_key.pem"; // Thay đổi nếu cần

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String publicKeyBase64 = req.getParameter("publicKey");

        if (publicKeyBase64 == null || publicKeyBase64.isEmpty()) {
            req.setAttribute("error", "Public Key không được để trống.");
            req.getRequestDispatcher("tao_chu_ky.jsp").forward(req, resp);
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(SAVE_PATH))) {
            pw.println("-----BEGIN PUBLIC KEY-----");
            for (int i = 0; i < publicKeyBase64.length(); i += 64) {
                int end = Math.min(i + 64, publicKeyBase64.length());
                pw.println(publicKeyBase64.substring(i, end));
            }
            pw.println("-----END PUBLIC KEY-----");

            req.setAttribute("message", "Lưu public key thành công tại: " + SAVE_PATH);
        } catch (IOException e) {
            req.setAttribute("error", "Không thể lưu public key: " + e.getMessage());
        }

        // Trả lại key hiển thị
        req.setAttribute("publicKey", publicKeyBase64);
        req.getRequestDispatcher("tao_chu_ky.jsp").forward(req, resp);
    }
}