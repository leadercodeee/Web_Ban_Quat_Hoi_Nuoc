
package com.example.backend.controller;


import com.example.backend.models.User;
import com.example.backend.services.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/deleteCategory")
public class DeleteCategoryController extends HttpServlet {

    private final CategoryService categoryService = CategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        if (userSession == null) {
            response.sendRedirect("/home");
        }
        if (!"admin".equals(userSession.getRole())) {
            response.sendRedirect("/home");
            return;
        }
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                boolean isDeleted = categoryService.deleteCategory(id);

                // Nếu xóa thành công, chuyển hướng về trang quản lý danh mục
                if (isDeleted) {
                    response.sendRedirect("/admin/categories");
                } else {
                    response.getWriter().println("Failed to delete category.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid category ID.");
            }
        } else {
            response.getWriter().println("Category ID is required.");
        }
    }
}
