package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
import com.example.backend.services.OrderService;
import com.example.backend.utils.OrderHashUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Date;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            String shippingAddress = request.getParameter("shippingAddress");
            String paymentMethod = request.getParameter("paymentMethod");
            String status = "Pending";

            Timestamp orderDate = new Timestamp(System.currentTimeMillis());
            Date deliveryDate = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L);

            Order order = new Order();
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setShippingAddress(shippingAddress);
            order.setPaymentMethod(paymentMethod);
            order.setOrderDate(orderDate);
            order.setDeliveryDate(deliveryDate);
            order.setStatus(status);

            boolean success = orderService.createOrderWithSignature(order);
            if (success) {
                request.setAttribute("order", order);
                request.setAttribute("hashed", true);
                request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Không thể lưu đơn hàng (chưa ký).");
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý thanh toán.");
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }
}


