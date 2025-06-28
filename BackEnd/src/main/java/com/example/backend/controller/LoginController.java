package com.example.backend.controller;

import com.example.backend.DAO.UserDAO;
import com.example.backend.models.User;
import com.example.backend.utils.KeyUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/auth.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email != null) email = email.trim();
        if (password != null) password = password.trim();

        System.out.println("Login attempt: email=" + email + ", password=" + password);

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(email, password);

        if (user != null) {
            System.out.println("Login success for user: " + user.getUsername());

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            try {
                // Sinh cặp khóa mới mỗi lần login (bạn có thể thay bằng lấy từ DB nếu muốn)
                KeyPair keyPair = KeyUtil.generateKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();

                session.setAttribute("privateKey", privateKey);
                session.setAttribute("publicKey", publicKey);

                // Nếu bạn muốn lưu khóa ra DB thì làm ở đây

            } catch (Exception e) {
                e.printStackTrace();
                session.invalidate();
                request.setAttribute("message", "Lỗi hệ thống, vui lòng thử lại sau.");
                request.getRequestDispatcher("auth.jsp").forward(request, response);
                return;
            }

            response.sendRedirect("/home");
        } else {
            System.out.println("Login failed for email: " + email);
            request.setAttribute("message", "Sai tài khoản hoặc mật khẩu");
            request.getRequestDispatcher("auth.jsp").forward(request, response);
        }
    }
}
