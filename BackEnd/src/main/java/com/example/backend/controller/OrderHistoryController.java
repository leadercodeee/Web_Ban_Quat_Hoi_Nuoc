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
import java.util.List;

@WebServlet("/order-history")
public class OrderHistoryController extends HttpServlet {
    private OrderService orderService = new OrderService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (session == null || user == null) {
            response.sendRedirect("auth.jsp");
            return;
        }

        int userId = user.getId(); // ✅ Lấy ID từ user

        List<Order> orders = orderService.getOrdersByUserId(userId);

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/order-history.jsp").forward(request, response);
    }

}



