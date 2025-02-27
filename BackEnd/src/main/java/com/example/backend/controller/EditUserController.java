
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

@WebServlet("/admin/editUser")
public class EditUserController extends HttpServlet {
    UserService userService = new UserService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
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
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/admin/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("userId"));
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");
        String[] dobParts = dob.split("/");
        String formattedDob = dobParts[2] + "-" + dobParts[1] + "-" + dobParts[0];
        User user = new User(id, username, null, email, fullName, phone, "user", "active", formattedDob, address);
        userService.updateUser(user);
        response.sendRedirect("/admin/users");
    }
}
