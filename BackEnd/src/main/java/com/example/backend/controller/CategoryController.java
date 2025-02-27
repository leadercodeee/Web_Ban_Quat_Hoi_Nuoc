
package com.example.backend.controller;


import com.example.backend.models.Category;
import com.example.backend.models.User;
import com.example.backend.services.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
@WebServlet("/admin/categories")
public class CategoryController extends HttpServlet {
    CategoryService categoryService = CategoryService.getInstance();
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
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/admin/categoryManagement.jsp").forward(request, response);
    }
}
