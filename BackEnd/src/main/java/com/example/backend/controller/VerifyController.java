package com.example.backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

@WebServlet("/verify")
public class VerifyController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException, IOException {
        String data = request.getParameter("data");
        String signatureStr = request.getParameter("signature");
        HttpSession session = request.getSession();
        PublicKey publicKey = (PublicKey) session.getAttribute("publicKey");

        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            boolean isVerified = signature.verify(Base64.getDecoder().decode(signatureStr));
            request.setAttribute("result", isVerified ? "Signature is VALID" : "Signature is INVALID");
        } catch (Exception e) {
            request.setAttribute("result", "Verification failed: " + e.getMessage());
        }

        request.getRequestDispatcher("/verify.jsp").forward(request, response);
    }
}
