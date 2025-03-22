
package com.example.backend.controller;

import com.example.backend.services.ResetPasswordService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    String email ="";
    ResetPasswordService resetPasswordService = new ResetPasswordService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        email = request.getParameter("email");
        request.getRequestDispatcher("repassword.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        System.out.println("new password" + newPassword);
        System.out.println("confirm password" + confirmPassword);
        System.out.println(email);
        // Gọi service để cập nhật mật khẩu
        System.out.println(newPassword);

        boolean isUpdated = resetPasswordService.updatePassword(email, newPassword, confirmPassword);
        System.out.println(isUpdated);
        if (isUpdated) {
            System.out.println("Cập nhật mật khẩu thành công");
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("errorMessage", "Mật khẩu không khớp.");
            request.getRequestDispatcher("repassword.jsp").forward(request, response);
        }
    }
}
