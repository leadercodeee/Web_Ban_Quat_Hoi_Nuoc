
package com.example.backend.controller;

import com.example.backend.DAO.CommentDAO;
import com.example.backend.models.Category;
import com.example.backend.models.Comment;
import com.example.backend.models.Product;
import com.example.backend.services.CategoryService;
import com.example.backend.services.CommentService;
import com.example.backend.services.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/detail")
public class DetailProductController extends HttpServlet {
    private ProductService productService = new ProductService();
    private CommentDAO commentDAO = new CommentDAO();
    private CommentService commentService = new CommentService(commentDAO);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getProductById(productId);
        List<Product> relatedProducts = productService.get5ProductsByCategoryId(product.getCategoryId());
        List<Comment> comments = commentService.getCommentsByProductId(productId);
        request.setAttribute("comments", comments);
        request.setAttribute("product", product);
        request.setAttribute("relatedProducts", relatedProducts);
        request.getRequestDispatcher("/detail.jsp").forward(request,response);
    }

}
