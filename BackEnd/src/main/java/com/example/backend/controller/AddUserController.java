
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
@WebServlet("/admin/addUser")
public class AddUserController extends HttpServlet {
    UserService userService = new UserService();
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
        request.getRequestDispatcher("/admin/addUser.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");
        String[] dobParts = dob.split("/");
        String formattedDob = dobParts[2] + "-" + dobParts[1] + "-" + dobParts[0];
        User user = new User(username, password, email, fullName, phone, "user", "Active", formattedDob, address);
        boolean isSuccess = userService.addUser(user);
        if (isSuccess) {
            response.sendRedirect("addUser.jsp?success=true");
        } else {
            request.setAttribute("error", "Thêm người dùng thất bại");
            request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response); // Return to form
        }
    }
}
