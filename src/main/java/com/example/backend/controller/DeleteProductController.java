
package com.example.backend.controller;

import com.example.backend.models.User;
import com.example.backend.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/deleteProduct")
public class DeleteProductController extends HttpServlet {
    ProductService productService = new ProductService();
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
        String productIdStr = request.getParameter("id");
        if (productIdStr != null && !productIdStr.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);
                // Xóa sản phẩm qua service
                boolean isDeleted = productService.deleteProduct(productId);

                if (isDeleted) {
                    response.sendRedirect("/admin/products");
                } else {
                    request.setAttribute("errorMessage", "Không thể xóa sản phẩm.");
                    request.getRequestDispatcher("/admin/productList.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
                request.getRequestDispatcher("/admin/productList.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Không có ID sản phẩm.");
            request.getRequestDispatcher("/admin/productList.jsp").forward(request, response);
        }
    }
}
