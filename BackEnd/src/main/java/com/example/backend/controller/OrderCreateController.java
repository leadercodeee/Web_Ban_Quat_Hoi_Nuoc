package com.example.backend.controller;

import com.example.backend.models.CartItem;
import com.example.backend.models.Order;
import com.example.backend.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@WebServlet("/order/create")
public class OrderCreateController extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            // Lấy userId từ session (giả sử user đã đăng nhập)
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                response.sendRedirect("/login");
                return;
            }

            // Lấy thông tin đơn hàng từ form hoặc session giỏ hàng
            String shippingAddress = request.getParameter("shippingAddress");
            String paymentMethod = request.getParameter("paymentMethod");

            // Giả sử có trong session giỏ hàng Map<Integer, CartItem>
            Map<Integer, CartItem> cartItems = (Map<Integer, CartItem>) session.getAttribute("cartItems");
            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect("/cart");
                return;
            }

            // Tính tổng tiền
            double totalAmount = cartItems.values().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();

            // Tạo order mới
            Order order = new Order();
            order.setUserId(userId);
            order.setShippingAddress(shippingAddress);
            order.setPaymentMethod(paymentMethod);
            order.setTotalAmount(totalAmount);
            order.setOrderDate(new Timestamp(new Date().getTime()));
            // Ví dụ đặt ngày giao sau 3 ngày
            order.setDeliveryDate(new java.sql.Date(System.currentTimeMillis() + 3L * 24 * 3600 * 1000));
            order.setStatus("Pending");

            // Ký số và lưu order
            orderService.signAndSaveOrder(order);

            // Đưa dữ liệu sang trang xác nhận
            request.setAttribute("order", order);
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("/orderConfirmation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Lỗi xử lý đơn hàng");
        }
    }
}
