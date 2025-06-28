package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {



            // Giả sử các tham số gửi từ form thanh toán
            int userId = Integer.parseInt(request.getParameter("userId"));
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            String shippingAddress = request.getParameter("shippingAddress");
            String paymentMethod = request.getParameter("paymentMethod");
            String status = "Pending";

            Timestamp orderDate = new Timestamp(System.currentTimeMillis());
            // Ví dụ đặt ngày giao hàng cách 3 ngày
            Date deliveryDate = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L);

            // Tạo đối tượng Order
            Order order = new Order();
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setShippingAddress(shippingAddress);
            order.setPaymentMethod(paymentMethod);
            order.setOrderDate(orderDate);
            order.setDeliveryDate(deliveryDate);
            order.setStatus(status);

            // Lưu order vào database (id tự sinh)
            OrderDAO orderDAO = new OrderDAO();
            int orderId = orderDAO.saveOrder(order);
            // Tạo chuỗi đại diện cho order để hash
            String orderString = order.toConcatenatedString();

            // Tính hash SHA-256
            String hash = OrderHashUtil.sha256Hash(orderString);

            // Gán hash cho order
            order.setHash(hash);



            if (orderId > 0) {
                // Lưu id vào order để hiển thị hoặc xử lý tiếp
                order.setId(orderId);
                // Chuyển sang trang xác nhận hoặc thông báo thành công
                request.setAttribute("order", order);
                request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
            } else {
                // Lưu không thành công
                request.setAttribute("error", "Không thể lưu đơn hàng.");
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý thanh toán.");
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }
}
