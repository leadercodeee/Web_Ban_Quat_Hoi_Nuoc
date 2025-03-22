
package com.example.backend.controller;

import com.example.backend.DAO.ProductImageDAO;
import com.example.backend.models.Cart;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {
    ProductImageDAO productImageDAO = new ProductImageDAO();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin sản phẩm từ request
        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        double price = Double.parseDouble(request.getParameter("price"));
        String brand = request.getParameter("brand");
        int stock = Integer.parseInt(request.getParameter("stock"));
        String description = request.getParameter("description");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        try {
            List<ProductImage> images = productImageDAO.getImagesByProductId(productId);

            Product product = new Product(productId, name, categoryId, price, brand, stock, description, LocalDateTime.now(), LocalDateTime.now());
            product.setImages(images);
            // Lấy giỏ hàng từ session
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            cart.addItem(product, quantity);
            response.getWriter().write("Sản phẩm đã được thêm vào giỏ hàng.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
