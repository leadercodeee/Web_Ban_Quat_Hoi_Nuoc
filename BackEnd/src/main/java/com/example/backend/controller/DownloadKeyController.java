package com.example.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "DownloadKeyController", urlPatterns = {"/downloadKey"})
public class DownloadKeyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        HttpSession session = request.getSession();

        String keyPEM = null;
        String filename = null;

        if ("private".equals(type)) {
            keyPEM = (String) session.getAttribute("privateKeyPEM");
            filename = "private_key.pem";
        } else if ("public".equals(type)) {
            keyPEM = (String) session.getAttribute("publicKeyPEM");
            filename = "public_key.pem";
        }

        if (keyPEM == null) {
            response.setContentType("text/plain");
            response.getWriter().write("Chưa tạo khóa hoặc phiên hết hạn.");
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.getOutputStream().write(keyPEM.getBytes());
        response.getOutputStream().flush();
    }
}
