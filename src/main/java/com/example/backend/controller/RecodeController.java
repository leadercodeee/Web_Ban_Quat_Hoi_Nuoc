
package com.example.backend.controller;

import com.example.backend.services.RecodeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/recode-password")
public class RecodeController extends HttpServlet {
    RecodeService recodeService = new RecodeService();
    String email = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        email = request.getParameter("email");
        request.getRequestDispatcher("recode.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otp = request.getParameter("otp");

        System.out.println("otp:" + otp);

        // Gọi service để xác thực OTP
        boolean isOtpValid = recodeService.verifyOtp(email, otp);
        System.out.println(isOtpValid);
        if (isOtpValid) {
            response.sendRedirect("reset-password?email=" + email);
        } else {
            request.setAttribute("errorMessage", "Mã OTP không hợp lệ.");
            request.getRequestDispatcher("recode.jsp").forward(request, response);
        }
    }
}
