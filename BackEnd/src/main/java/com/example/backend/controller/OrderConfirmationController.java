package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DAO.OrderDetailDAO;
import com.example.backend.DAO.UserDAO;
import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.*;
import com.example.backend.services.InvoiceHashService;
import com.example.backend.services.UserKeyService;
import com.example.backend.utils.DigitalSignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/order-confirmation")
public class OrderConfirmationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect("order-history.jsp");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(orderIdStr);
        if (order == null) {
            response.sendRedirect("order-history.jsp");
            return;
        }

        try {
            // Hash
            InvoiceHashService hashService = new InvoiceHashService();
            String hash = hashService.generateOrderHash(order);
            order.setHash(hash);

            // Lấy private key người dùng từ DB
            UserKeyDAO keyDAO = new UserKeyDAO();
            UserKey userKey = keyDAO.getKeyByUserId(order.getUserId());
            PrivateKey privateKey = DigitalSignatureUtil.decodePrivateKey(userKey.getPrivateKey());

            // Ký điện tử
            String data = order.toConcatenatedString();
            String signature = DigitalSignatureUtil.sign(data, privateKey);
            order.setSignature(signature);

            // Lưu lại hash và signature
            boolean updated = orderDAO.updateOrderSignatureAndHash(order);

            // Xác minh lại chữ ký để hiện thị lên JSP
            UserDAO userDAO = new UserDAO();
            String pubKeyBase64 = userDAO.getUserPublicKey(order.getUserId());
            PublicKey publicKey = DigitalSignatureUtil.decodePublicKey(pubKeyBase64);
            boolean isValid = DigitalSignatureUtil.verify(data, signature, publicKey);

            request.setAttribute("signingSuccess", updated);
            request.setAttribute("signatureValid", isValid);
            request.setAttribute("order", order);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("signingSuccess", false);
            request.setAttribute("signatureValid", false);
            request.setAttribute("order", order);
        }

        request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect("order-history.jsp");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("order-history.jsp");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(orderIdStr);
        if (order == null) {
            response.sendRedirect("order-history.jsp");
            return;
        }

        // Băm và ký
        boolean signingSuccess = false;
        String signatureError = null;

        try {
            // 1. Tạo hash SHA-256
            com.example.backend.services.InvoiceHashService hashService = new com.example.backend.services.InvoiceHashService();
            String orderHash = hashService.generateOrderHash(order);

            // 2. Lấy khóa riêng người dùng để ký từ session
            PrivateKey privateKey = (PrivateKey) request.getSession().getAttribute("privateKey");
            String orderData = order.toConcatenatedString();
            String signature = DigitalSignatureUtil.sign(orderData, privateKey);

            // 3. Gán và cập nhật DB
            order.setHash(orderHash);
            order.setSignature(signature);

            signingSuccess = orderDAO.updateOrderSignatureAndHash(order);

        } catch (Exception e) {
            e.printStackTrace();
            signatureError = e.getMessage();
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("order", order);
        request.setAttribute("signingSuccess", signingSuccess);
        request.setAttribute("signatureError", signatureError);

        request.getRequestDispatcher("confirmOrder.jsp").forward(request, response);
    }

}
