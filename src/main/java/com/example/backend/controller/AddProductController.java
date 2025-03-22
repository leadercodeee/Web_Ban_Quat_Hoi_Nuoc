
package com.example.backend.controller;

import com.example.backend.DAO.ProductDAO;
import com.example.backend.DAO.ProductImageDAO;
import com.example.backend.models.Category;
import com.example.backend.models.Product;
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
import java.util.List;

@WebServlet("/admin/addProduct")
public class AddProductController extends HttpServlet {
    CategoryService categoryService = CategoryService.getInstance();
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
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/admin/addProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        double price = Double.parseDouble(request.getParameter("price"));
        String brand = request.getParameter("brand");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String description = request.getParameter("description");
        String image1 = request.getParameter("image1");
        String image2 = request.getParameter("image2");
        String image3 = request.getParameter("image3");
        String image4 = request.getParameter("image4");

        Product product = new Product(productName, categoryId, price, brand, quantity, description);
        ProductService productService = new ProductService();
        int productId = productService.addProduct(product);

        if (productId > 0) {
            ProductImageDAO imageDAO = new ProductImageDAO();
            imageDAO.addImage(productId, image1, true); // Hình ảnh chính
            if (!image2.isEmpty()) imageDAO.addImage(productId, image2, false);
            if (!image3.isEmpty()) imageDAO.addImage(productId, image3, false);
            if (!image4.isEmpty()) imageDAO.addImage(productId, image4, false);
            response.sendRedirect("/admin/products");
        } else {
            request.setAttribute("error", "Thêm sản phẩm thất bại.");
            doGet(request, response);
        }
    }
}
