
package com.example.backend.controller;

import com.example.backend.DAO.ProductDAO;
import com.example.backend.DAO.ProductImageDAO;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;
import com.example.backend.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/products")
public class ProductController extends HttpServlet {
    ProductDAO productDAO = new ProductDAO();
    ProductImageDAO productImageDAO = new ProductImageDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/home");
            return;
        }

        // Kiểm tra nếu user không phải là admin
        if (!"admin".equals(user.getRole())) {
            response.sendRedirect("/home");
            return;
        }
        List<Product> productList = productDAO.getAllProducts();
        request.setAttribute("products", productList);
        request.getRequestDispatcher("/admin/admin.jsp").forward(request,response);
    }
}
