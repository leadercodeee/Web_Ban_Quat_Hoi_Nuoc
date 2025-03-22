
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

@WebServlet("/addCategory")
public class AddCategoryController extends HttpServlet {
    private final CategoryService categoryService = CategoryService.getInstance();

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
        request.getRequestDispatcher("/admin/addCategory.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("categoryName");
        String description = request.getParameter("categoryDescription");

        boolean isAdded = categoryService.addCategory(name, description);

        if (isAdded) {
            response.sendRedirect("/admin/categories");
        } else {
            String errMessage = "Thêm thất bại";
            request.setAttribute("err", errMessage);
            request.getRequestDispatcher("/addCategory").forward(request, response);
        }
    }

}
