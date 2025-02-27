
package com.example.backend.controller;

import com.example.backend.DAO.UserDAO;
import com.example.backend.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String role = "USER";
        String status = "ACTIVE";
        String dob = request.getParameter("dob");
        String[] dobParts = dob.split("/");
        String formattedDob = dobParts[2] + "-" + dobParts[1] + "-" + dobParts[0];
        String address = request.getParameter("address");

        User user = new User(username, hashedPassword, email, fullName, phone, role, status, formattedDob, address);
        UserDAO userDAO = new UserDAO();

        if (userDAO.register(user)) {
            response.sendRedirect("auth.jsp?success=Account created successfully");
        } else {
            response.sendRedirect("auth.jsp?error=Account creation failed");
        }
    }
}
