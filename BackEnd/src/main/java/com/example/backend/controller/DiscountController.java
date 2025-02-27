
package com.example.backend.controller;

import com.example.backend.models.Discount;
import com.example.backend.models.User;
import com.example.backend.services.DiscountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/discounts")
public class DiscountController extends HttpServlet {
    private DiscountService discountService = new DiscountService();
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
        List<Discount> discounts = discountService.getAllDiscounts();
        request.setAttribute("discounts", discounts);
        request.getRequestDispatcher("/admin/discountManagement.jsp").forward(request,response);
    }
}
