package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
import com.example.backend.services.InvoiceHashService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/confirm-order")
public class ConfirmOrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final InvoiceHashService hashService = new InvoiceHashService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderDAO.getOrderById(orderIdStr);
            if (order == null) {
                response.getWriter().write("❌ Không tìm thấy đơn hàng.");
                return;
            }

            String hash = hashService.generateOrderHash(order);
            orderDAO.updateHash(orderId, hash);

            // Chuyển hướng về lại trang xác nhận với thông báo
            response.sendRedirect("order-detail.jsp?orderId=" + orderId + "&status=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("❌ Lỗi xử lý xác nhận đơn hàng.");
        }
    }
}
