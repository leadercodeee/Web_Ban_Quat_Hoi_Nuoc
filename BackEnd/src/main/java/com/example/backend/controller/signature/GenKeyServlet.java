package com.example.backend.controller.signature;

import com.example.backend.utils.CryptoUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@WebServlet("/gen-key")
public class GenKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CryptoUtil cryptoUtil = new CryptoUtil();
        try {
            cryptoUtil.genKey();

            // Lưu vào session để tái sử dụng
            HttpSession session = req.getSession();
            session.setAttribute("cryptoUtil", cryptoUtil);

            // Hiển thị lên giao diện
            String publicKey = Base64.getEncoder().encodeToString(cryptoUtil.getPublicKey().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(cryptoUtil.getPrivateKey().getEncoded());

            req.setAttribute("publicKey", publicKey);
            req.setAttribute("privateKey", privateKey);

        } catch (NoSuchAlgorithmException e) {
            req.setAttribute("error", "Lỗi khi tạo khóa: " + e.getMessage());
        }

        req.getRequestDispatcher("tao_chu_ky.jsp").forward(req, resp);
    }
}