package com.example.backend.controller;

import com.example.backend.models.*;
import com.example.backend.services.OrderPlaceService;
import com.example.backend.services.OrderPlaceDetailService;
import com.example.backend.services.InvoiceHashService;
import com.example.backend.utils.DigitalSignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.PrivateKey;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/placeOrder")
public class OrderPlaceController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");

        if (user == null || privateKey == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User chưa đăng nhập hoặc chưa có khóa riêng.");
            return;
        }

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        String shippingAddress = request.getParameter("shippingAddress");
        String paymentMethod = request.getParameter("paymentMethod");

        double totalAmount = 0;
        for (CartItem item : cart.getItems().values()) {
            totalAmount += item.getProduct().getPrice() * item.getQuantity();
        }

        // Ngày mua và ngày giao
        LocalDateTime now = LocalDateTime.now();
        Timestamp orderTimestamp = Timestamp.valueOf(now);
        Date deliveryDate = Date.valueOf(now.plusDays(3).toLocalDate());

        // Tạo đơn hàng
        Order order = new Order();
        order.setUserId(user.getId());
        order.setPhone(user.getPhone());
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDate(orderTimestamp);
        order.setDeliveryDate(deliveryDate);
        order.setStatus("shipping");

        OrderPlaceService orderService = new OrderPlaceService();
        int orderId = orderService.saveOrder(order);
        order.setId(orderId); // để sử dụng sau

        // Lưu chi tiết đơn hàng
        OrderPlaceDetailService orderDetailService = new OrderPlaceDetailService();
        for (CartItem cartItem : cart.getItems().values()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(cartItem.getProduct().getId());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetailService.saveOrderDetail(orderDetail);
        }

        // Gán lại thông tin đơn đã lưu (để lấy đầy đủ order.getItems())
        Order savedOrder = orderService.getOrderById(orderId);
        if (savedOrder == null) {
            response.getWriter().write("❌ Không thể tìm thấy đơn hàng sau khi lưu.");
            return;
        }

        try {
            // 1. Tạo SHA-256 hash
            InvoiceHashService invoiceHashService = new InvoiceHashService();
            String orderHash = invoiceHashService.generateOrderHash(savedOrder);
            savedOrder.setHash(orderHash);

            // 2. Ký điện tử từ hash bằng private key
            String signature = DigitalSignatureUtil.sign(orderHash, privateKey);
            savedOrder.setSignature(signature);

            // 3. Lưu lại hash và chữ ký vào DB
            boolean updated = orderService.updateOrderHashAndSignature(orderId, orderHash, signature);
            if (!updated) {
                response.getWriter().write("❌ Không thể cập nhật chữ ký và hash.");
                return;
            }

            order.setHash(orderHash);
            order.setSignature(signature);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("❌ Lỗi khi tạo hash hoặc chữ ký: " + e.getMessage());
            return;
        }

        // Xóa giỏ hàng
        session.removeAttribute("cart");

        // Chuyển đến trang xác nhận
        request.setAttribute("order", order);
        request.setAttribute("cartItems", cart.getItems());
        request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
    }
}
