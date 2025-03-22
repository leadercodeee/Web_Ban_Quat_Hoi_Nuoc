
package com.example.backend.controller;

import com.example.backend.services.ForgotPasswordService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    ForgotPasswordService forgotPasswordService = new ForgotPasswordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("forgotpassword.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        // Gọi service để xử lý logic quên mật khẩu
        boolean isSent = forgotPasswordService.sendResetLink(email);

        if (isSent) {
            response.sendRedirect("recode-password?email=" + email);
        } else {
            response.getWriter().write("Email hoặc số điện thoại không tồn tại.");
        }
    }

}
