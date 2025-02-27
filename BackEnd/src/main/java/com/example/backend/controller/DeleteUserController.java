
package com.example.backend.controller;

import com.example.backend.DAO.UserDAO;
import com.example.backend.models.User;
import com.example.backend.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/deleteUser")
public class DeleteUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy id của user từ tham số query string
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
        String userId = request.getParameter("id");

        if (userId != null && !userId.isEmpty()) {
            // Chuyển đổi id sang số nguyên
            try {
                int id = Integer.parseInt(userId);

                // Xóa người dùng thông qua UserDAO
                UserService userService = new UserService();
                boolean result = userService.deleteUser(id);

                if (result) {
                    // Nếu xóa thành công, chuyển hướng về trang danh sách người dùng
                    response.sendRedirect("/admin/users");
                } else {
                    // Nếu xóa không thành công, hiển thị thông báo lỗi
                    request.setAttribute("errorMessage", "Xóa người dùng thất bại.");
                    request.getRequestDispatcher("/admin/users").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // Nếu id không hợp lệ
                response.sendRedirect("/admin/users");
            }
        } else {
            response.sendRedirect("/admin/users");
        }
    }
}
