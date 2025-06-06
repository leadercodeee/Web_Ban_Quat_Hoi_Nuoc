package com.example.backend.controller;

import com.example.backend.models.Order;
import com.example.backend.models.User;
import com.example.backend.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/admin/orders")
public class OrderController extends HttpServlet {
    private final OrderService orderService = new OrderService();

    public OrderController() throws SQLException {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User userSession = (session != null) ? (User) session.getAttribute("user") : null;

        if (userSession == null || !"admin".equals(userSession.getRole())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        List<Order> orders = orderService.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/admin/orderManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User userSession = (session != null) ? (User) session.getAttribute("user") : null;

        if (userSession == null || !"admin".equals(userSession.getRole())) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            // Lấy dữ liệu từ request và chuyển thành Order object
            Order order = parseOrderFromRequest(request);

            // Ký và lưu đơn hàng
            orderService.signAndSaveOrder(order);

            // Chuyển hướng sau khi thành công
            response.sendRedirect(request.getContextPath() + "/admin/orders?success=true");

        } catch (IllegalArgumentException e) {
            // Lỗi validate dữ liệu đầu vào
            request.setAttribute("error", "Dữ liệu không hợp lệ: " + e.getMessage());
            request.getRequestDispatcher("/admin/orderForm.jsp").forward(request, response);

        } catch (Exception e) {
            // Lỗi khác (ví dụ lỗi ký, lưu DB)
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi xử lý đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("/admin/orderForm.jsp").forward(request, response);
        }
    }

    /**
     * Phân tích dữ liệu từ request thành Order object, kèm validate dữ liệu.
     * @throws IllegalArgumentException nếu dữ liệu không hợp lệ
     */
    private Order parseOrderFromRequest(HttpServletRequest request) throws IllegalArgumentException {
        Order order = new Order();

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            order.setUserId(userId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("User ID phải là số nguyên.");
        }

        try {
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            order.setTotalAmount(totalAmount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tổng tiền phải là số hợp lệ.");
        }
        String phone = request.getParameter("phone");
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống.");
        }
        order.setPhone(phone);


        String shippingAddress = request.getParameter("shippingAddress");
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ giao hàng không được để trống.");
        }
        order.setShippingAddress(shippingAddress);

        String paymentMethod = request.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Phương thức thanh toán không được để trống.");
        }
        order.setPaymentMethod(paymentMethod);

        String orderDateStr = request.getParameter("orderDate");
        order.setOrderDate(parseTimestamp(orderDateStr, "Ngày đặt hàng không hợp lệ. Định dạng cần: yyyy-MM-dd HH:mm:ss"));

        String deliveryDateStr = request.getParameter("deliveryDate");
        order.setDeliveryDate(parseDate(deliveryDateStr, "Ngày giao hàng không hợp lệ. Định dạng cần: yyyy-MM-dd"));

        String status = request.getParameter("status");
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái đơn hàng không được để trống.");
        }
        order.setStatus(status);

        return order;
    }

    private Timestamp parseTimestamp(String dateStr, String errorMessage) throws IllegalArgumentException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setLenient(false);
            return new Timestamp(sdf.parse(dateStr).getTime());
        } catch (ParseException | NullPointerException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private Date parseDate(String dateStr, String errorMessage) throws IllegalArgumentException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            return new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException | NullPointerException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
