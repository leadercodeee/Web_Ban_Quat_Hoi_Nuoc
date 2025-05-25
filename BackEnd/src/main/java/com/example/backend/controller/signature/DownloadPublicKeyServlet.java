package com.example.backend.controller.signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;


@WebServlet("/download-public-key")
public class DownloadPublicKeyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        String publicKeyBase64 = req.getParameter("publicKey");

        if (publicKeyBase64 == null || publicKeyBase64.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không có public key.");
            return;
        }

        // Chuẩn bị dữ liệu file
        StringBuilder pemBuilder = new StringBuilder();
        pemBuilder.append("-----BEGIN PUBLIC KEY-----\n");
        for (int i = 0; i < publicKeyBase64.length(); i += 64) {
            int end = Math.min(i + 64, publicKeyBase64.length());
            pemBuilder.append(publicKeyBase64, i, end).append("\n");
        }
        pemBuilder.append("-----END PUBLIC KEY-----\n");

        // Thiết lập header để trình duyệt tải về
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"public_key.pem\"");

        try (OutputStream os = resp.getOutputStream()) {
            os.write(pemBuilder.toString().getBytes());
            os.flush();
        }
    }
}