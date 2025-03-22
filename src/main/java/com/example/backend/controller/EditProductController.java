
package com.example.backend.controller;

import com.example.backend.models.Category;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;
import com.example.backend.models.User;
import com.example.backend.services.CategoryService;
import com.example.backend.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet("/admin/editProduct")
public class EditProductController extends HttpServlet {
    CategoryService categoryService = CategoryService.getInstance();
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
        int productId = Integer.parseInt(request.getParameter("id"));
        ProductService productService = new ProductService();
        Product product = productService.getProductById(productId);
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/admin/editProduct.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("quantity"));
        String brand = request.getParameter("brand");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        // Lấy các URL hình ảnh
        List<ProductImage> images = new ArrayList<>();
        String[] imageUrls = {
                request.getParameter("image1"),
                request.getParameter("image2"),
                request.getParameter("image3"),
                request.getParameter("image4")
        };

        // Thêm các hình ảnh vào danh sách
        boolean isPrimary = true;  // Chỉ có một hình ảnh chính
        for (String imageUrl : imageUrls) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                images.add(new ProductImage(0, productId, imageUrl, isPrimary, LocalDateTime.now(), LocalDateTime.now()));
                isPrimary = false;  // Chỉ có 1 hình ảnh chính
            }
        }

        // Cập nhật thông tin sản phẩm và hình ảnh
        Product product = new Product(productId, productName,categoryId,price,brand, stock ,description , images, LocalDateTime.now(), LocalDateTime.now());

        // Gọi phương thức để cập nhật sản phẩm và hình ảnh vào cơ sở dữ liệu
        ProductService productService = new ProductService();
        productService.updateProduct(product);

        // Chuyển hướng về danh sách sản phẩm
        response.sendRedirect("/admin/products");
    }
}
