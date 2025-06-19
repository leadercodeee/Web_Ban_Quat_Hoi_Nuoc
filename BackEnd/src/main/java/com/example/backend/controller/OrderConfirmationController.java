package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DAO.OrderDetailDAO;
import com.example.backend.DAO.UserDAO;
import com.example.backend.models.*;
import com.example.backend.utils.DigitalSignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("order-history.jsp");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(String.valueOf(orderId));
        if (order == null) {
            response.sendRedirect("order-history.jsp");
            return;
        }

        OrderDetailDAO detailDAO = new OrderDetailDAO();
        List<OrderDetail> details;
        try {
            details = detailDAO.getOrderDetailsByOrderId(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, CartItem> cartItems = new HashMap<>();
        for (OrderDetail detail : details) {
            Product product;
            try {
                product = detailDAO.getProductById(detail.getProductId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            CartItem item = new CartItem(product, detail.getQuantity());
            cartItems.put(product.getId(), item);
        }

        // Kiểm tra chữ ký
        PublicKey publicKey = null;
        boolean signatureValid = false;
        try {
            UserDAO userDAO = new UserDAO();
            if (userDAO == null) {
                throw new ServletException("Failed to initialize UserDAO");
            }
            String base64PublicKey = userDAO.getUserPublicKey(order.getUserId());
            publicKey = DigitalSignatureUtil.decodePublicKey(base64PublicKey);

            String data = order.toConcatenatedString();  // Tạo chuỗi dữ liệu giống lúc hash
            signatureValid = DigitalSignatureUtil.verify(data, order.getSignature(), publicKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Truyền dữ liệu sang JSP
        request.setAttribute("order", order);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("signatureValid", signatureValid);
        request.setAttribute("publicKey", publicKey);
        request.setAttribute("orderHash", order.getHash());

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

        // Tính hash
        try {
            com.example.backend.services.InvoiceHashService hashService = new com.example.backend.services.InvoiceHashService();
            String newHash = hashService.generateOrderHash(order);
            orderDAO.updateHash(orderId, newHash);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("❌ Lỗi khi hash đơn hàng");
            return;
        }

        // Chuyển về lại trang xác nhận để xem kết quả
        response.sendRedirect("order-confirmation?orderId=" + orderId + "&hashed=true");
    }

}
