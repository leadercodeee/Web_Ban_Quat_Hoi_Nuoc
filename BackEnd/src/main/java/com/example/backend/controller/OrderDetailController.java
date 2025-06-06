
package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
import com.example.backend.models.OrderDetail;
import com.example.backend.models.User;
import com.example.backend.services.OrderDetailService;
import com.example.backend.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/orderDetail")
public class OrderDetailController extends HttpServlet {

    OrderDetailService orderDetailService = new OrderDetailService();
    OrderService orderService = new OrderService(); // thêm dòng này

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");

        if (userSession == null || !"admin".equals(userSession.getRole())) {
            response.sendRedirect("/home");
            return;
        }

        String orderIdStr = request.getParameter("orderId");
        int orderId;

        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID không hợp lệ.");
            return;
        }

        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
            Order order = orderService.getOrderById(String.valueOf(orderId));

            if (order == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng.");
                return;
            }

            request.setAttribute("orderDetails", orderDetails);
            request.setAttribute("order", order); // thêm dòng này để truyền sang JSP

            request.getRequestDispatcher("/admin/orderDetail.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn dữ liệu đơn hàng", e);
        }
    }
}

