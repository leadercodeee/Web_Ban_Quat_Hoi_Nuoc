
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

@WebServlet("/admin/orders")
public class OrderController extends HttpServlet {
    OrderService orderService = new OrderService();
    @Override
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
        List<Order> orders = orderService.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/admin/orderManagement.jsp").forward(request,response);
    }
}
