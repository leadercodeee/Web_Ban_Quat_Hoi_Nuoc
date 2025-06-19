package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
import com.example.backend.services.InvoiceHashService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/confirm-hash")
public class ConfirmHashController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(String.valueOf(orderId));

        if (order != null) {
            InvoiceHashService hashService = new InvoiceHashService();
            String hash = hashService.generateOrderHash(order);
            orderDAO.updateHash(orderId, hash);

            resp.sendRedirect("order-confirmation?orderId=" + orderId);
        } else {
            resp.getWriter().println("❌ Không tìm thấy đơn hàng.");
        }
    }
}
