
package com.example.backend.controller;

import com.example.backend.models.User;
import com.example.backend.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            response.sendRedirect("/login");
            return;
        }
        request.getRequestDispatcher("changePassword.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null){
            response.sendRedirect("/login");
            return;
        }
        int userId = user.getId(); // User ID có thể được lấy từ session
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        boolean success = userService.changePassword(userId, currentPassword, newPassword);
        if (success) {
            request.setAttribute("message", "Thay đổi mật khẩu thành công!");
        } else {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng hoặc lỗi hệ thống!");
        }

        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }
}
