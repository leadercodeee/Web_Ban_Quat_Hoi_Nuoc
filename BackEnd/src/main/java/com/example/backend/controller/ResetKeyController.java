package com.example.backend.controller;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ResetKeyController", urlPatterns = {"/reset-key"})
    public class ResetKeyController extends HttpServlet {
        private final UserKeyDAO userKeyDAO = new UserKeyDAO();

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("auth.jsp");
                return;
            }

            User user = (User) session.getAttribute("user");
            try {
                userKeyDAO.deleteKeyByUserId(user.getId());
                response.sendRedirect("keygen"); // Tự động tạo lại khi quay lại
            } catch (Exception e) {
                throw new ServletException("Lỗi reset khóa", e);
            }
        }
    }


