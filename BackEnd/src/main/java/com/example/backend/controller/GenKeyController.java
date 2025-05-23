package com.example.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

@WebServlet("/genkey")
public class GenKeyController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            // Lưu vào session hoặc DB, ở đây demo lưu session
            HttpSession session = request.getSession();
            session.setAttribute("publicKey", pair.getPublic());
            session.setAttribute("privateKey", pair.getPrivate());

            request.setAttribute("message", "Key pair generated successfully.");
            request.getRequestDispatcher("/genKey.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Key generation error", e);
        }
    }
}
