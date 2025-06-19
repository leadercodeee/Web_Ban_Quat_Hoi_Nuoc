package com.example.backend.controller;

import com.example.backend.models.*;
import com.example.backend.services.OrderPlaceService;
import com.example.backend.services.OrderPlaceDetailService;
import com.example.backend.utils.DigitalSignatureUtil;
import com.example.backend.services.InvoiceHashService;
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
import java.util.Map;

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
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDate(orderTimestamp);
        order.setDeliveryDate(deliveryDate);
        order.setStatus("shipping");

        try {
            // Ký số
            String dataToSign = order.toConcatenatedString();
            String signature = DigitalSignatureUtil.sign(dataToSign, privateKey);
            order.setSignature(signature);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi ký số đơn hàng: " + e.getMessage(), e);
        }

        // Lưu đơn hàng
        OrderPlaceService orderService = new OrderPlaceService();
        int orderId = orderService.saveOrder(order);

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
        InvoiceHashService hashService = new InvoiceHashService();
        Order savedOrder = orderService.getOrderById(orderId);
        String orderHash = hashService.generateOrderHash(savedOrder);
        orderService.updateOrderHash(orderId, orderHash);
        // Xóa giỏ hàng
        session.removeAttribute("cart");

        // Chuyển tiếp đến trang xác nhận đơn hàng
        request.setAttribute("order", order);
        request.setAttribute("cartItems", cart.getItems());
        request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
    }
}
