
package com.example.backend.controller;

import com.example.backend.models.*;
import com.example.backend.services.OrderPlaceService;
import com.example.backend.services.OrderPlaceDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
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
            // Retrieve order information from form
            String shippingAddress = request.getParameter("shippingAddress");
            String paymentMethod = request.getParameter("paymentMethod");

            // Calculate totalAmount from cart items
            double totalAmount = 0;
            for (CartItem item : cart.getItems().values()) {
                totalAmount += item.getProduct().getPrice() * item.getQuantity();
            }

            // Get current date and time for orderDate
            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp orderTimestamp = Timestamp.valueOf(currentDateTime); // Convert to Timestamp

            // Get delivery date (3 days after the current date)
            LocalDateTime deliveryDateTime = currentDateTime.plusDays(3);
            Date deliveryDate = Date.valueOf(deliveryDateTime.toLocalDate()); // Convert to Date

            // Create Order
            Order order = new Order();
            order.setUserId(user.getId()); // Set the user ID (should come from session or authentication)
            order.setTotalAmount(totalAmount);
            order.setShippingAddress(shippingAddress);
            order.setPaymentMethod(paymentMethod);
            order.setOrderDate(orderTimestamp); // Set the Timestamp for orderDate
            order.setDeliveryDate(deliveryDate); // Set the Date for deliveryDate
            order.setStatus("shipping"); // Assuming "shipping" is a valid status

            OrderPlaceService orderService = new OrderPlaceService();
            int orderId = orderService.saveOrder(order);

            OrderPlaceDetailService orderDetailService = new OrderPlaceDetailService();
            Map<Integer, CartItem> cartItems = cart.getItems(); // Lấy các item từ cart

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
            // Redirect to cart if the cart is empty
            response.sendRedirect("cart.jsp");
        }
    }
}
