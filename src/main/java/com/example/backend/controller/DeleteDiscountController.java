
package com.example.backend.controller;

import com.example.backend.models.User;
import com.example.backend.services.DiscountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/deleteDiscount")
public class DeleteDiscountController extends HttpServlet {
    DiscountService discountService = new DiscountService();

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
        String discountIdParam = request.getParameter("id");
        if (discountIdParam != null) {
            try {
                int discountId = Integer.parseInt(discountIdParam);
                discountService.deleteDiscount(discountId);
                response.sendRedirect("/admin/discounts"); // Quay lại trang danh sách giảm giá
            } catch (NumberFormatException e) {
                response.sendRedirect("/admin/discounts"); // Nếu ID không hợp lệ, chỉ quay lại trang
            }
        }
    }
}
