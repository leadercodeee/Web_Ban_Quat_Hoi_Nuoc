package com.example.backend.controller;

import com.example.backend.models.*;
import com.example.backend.services.OrderPlaceService;
import com.example.backend.services.OrderPlaceDetailService;
import com.example.backend.utils.DigitalSignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/placeOrder")
public class OrderPlaceController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart != null && !cart.getItems().isEmpty()) {
            String shippingAddress = request.getParameter("shippingAddress");
            String paymentMethod = request.getParameter("paymentMethod");

            double totalAmount = 0;
            for (CartItem item : cart.getItems().values()) {
                totalAmount += item.getProduct().getPrice() * item.getQuantity();
            }

            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp orderTimestamp = Timestamp.valueOf(currentDateTime);
            LocalDateTime deliveryDateTime = currentDateTime.plusDays(3);
            Date deliveryDate = Date.valueOf(deliveryDateTime.toLocalDate());

            Order order = new Order();
            order.setUserId(user.getId());
            order.setTotalAmount(totalAmount);
            order.setShippingAddress(shippingAddress);
            order.setPaymentMethod(paymentMethod);
            order.setOrderDate(orderTimestamp);
            order.setDeliveryDate(deliveryDate);
            order.setStatus("shipping");

            try {
                KeyPair keyPair = DigitalSignatureUtil.generateKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();

                String dataToSign = order.getUserId() + "|" + totalAmount + "|" + shippingAddress + "|" +
                        paymentMethod + "|" + orderTimestamp + "|" + deliveryDate;

                String signature = DigitalSignatureUtil.sign(dataToSign, privateKey);
                order.setSignature(signature);
                session.setAttribute("publicKey", publicKey);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi ký đơn hàng", e);
            }

            OrderPlaceService orderService = new OrderPlaceService();
            int orderId = orderService.saveOrder(order);

            OrderPlaceDetailService orderDetailService = new OrderPlaceDetailService();
            Map<Integer, CartItem> cartItems = cart.getItems();

            for (CartItem cartItem : cartItems.values()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(cartItem.getProduct().getId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setPrice(cartItem.getProduct().getPrice());

                orderDetailService.saveOrderDetail(orderDetail);
            }

            session.removeAttribute("cart");
            request.setAttribute("order", order);
            request.setAttribute("cartItems", cartItems);

            request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
        } else {
            response.sendRedirect("cart.jsp");
        }
    }
}
