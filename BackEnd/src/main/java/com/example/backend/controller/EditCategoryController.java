
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
@WebServlet("/editCategory")
public class EditCategoryController extends HttpServlet {
    private final CategoryService categoryService = CategoryService.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null ){
            response.sendRedirect("/home");
            return;
        }
        if (!"admin".equals(user.getRole())) {
            response.sendRedirect("/home");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        Category category = categoryService.getCategoryById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/admin/editCategory.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
        int id = Integer.parseInt(request.getParameter("categoryId"));
        String name = request.getParameter("categoryName");
        String description = request.getParameter("categoryDescription");

        // Tạo đối tượng category mới từ thông tin form
        Category category = new Category(id, name, description, null, null);

        // Cập nhật danh mục vào cơ sở dữ liệu
        boolean isUpdated = categoryService.updateCategory(category);

        if (isUpdated) {
            // Nếu cập nhật thành công, chuyển hướng về trang quản lý danh mục
            response.sendRedirect("/admin/categories");
        } else {
            // Nếu thất bại, thông báo lỗi và giữ lại thông tin trong form
            request.setAttribute("errorMessage", "Cập nhật thất bại!");
            request.getRequestDispatcher("/editCategory").forward(request, response);
        }
    }
}
