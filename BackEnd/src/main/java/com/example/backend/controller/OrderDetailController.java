
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        if(userSession == null ){
            response.sendRedirect("/home");
            return;
        }
        if (!"admin".equals(userSession.getRole())) {
            response.sendRedirect("/home");
            return;
        }
        String orderId = request.getParameter("orderId");

        try {
            List<OrderDetail> orderDetails= orderDetailService.getOrderDetailsByOrderId(Integer.parseInt(orderId));
            request.setAttribute("orderDetails", orderDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Gửi dữ liệu order vào JSP

        request.getRequestDispatcher("/admin/orderDetail.jsp").forward(request, response);
    }
}
