package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
import com.example.backend.utils.DigitalSignatureUtil;
import com.example.backend.utils.SignatureDataBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@WebServlet("/verifySignature")
public class VerifySignatureController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderId = request.getParameter("orderId");
        String publicKeyBase64 = request.getParameter("publicKeyBase64");

        try {
            OrderDAO orderDAO = new OrderDAO();
            Order order = orderDAO.getOrderById(orderId);

            if (order == null) {
                request.setAttribute("verificationResult", "❌ Không tìm thấy đơn hàng.");
            } else {
                // 1. Chuẩn hóa dữ liệu giống lúc ký
                String dataToVerify = SignatureDataBuilder.forOrder(order);

                // 2. Giải mã PublicKey từ base64
                byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(keySpec);

                // 3. Xác minh chữ ký
                boolean isValid = DigitalSignatureUtil.verify(dataToVerify, order.getSignature(), publicKey);

                if (isValid) {
                    request.setAttribute("verificationResult", "✅ Chữ ký HỢP LỆ.");
                } else {
                    request.setAttribute("verificationResult", "❌ Chữ ký KHÔNG hợp lệ.");
                }
            }

        } catch (Exception e) {
            request.setAttribute("verificationResult", "❌ Lỗi xác minh: " + e.getMessage());
        }

        request.getRequestDispatcher("verifySignature.jsp").forward(request, response);
    }
}
