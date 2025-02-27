
package com.example.backend.controller;

import com.example.backend.DAO.CommentDAO;
import com.example.backend.models.Comment;
import com.example.backend.models.User;
import com.example.backend.services.CommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/addComment")
public class AddCommentController extends HttpServlet {
    CommentDAO commentDAO = new CommentDAO();
    CommentService commentService = new CommentService(commentDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null){
            response.sendRedirect("/login");
            return;
        }
        String username = user.getUsername();
        int productId = Integer.parseInt(request.getParameter("productId"));
        String text = request.getParameter("text");
        System.out.println(productId);
        System.out.println(text);
        int rating = Integer.parseInt(request.getParameter("rating"));
        System.out.println(rating);
        Comment comment = new Comment();
        comment.setUsername(username);
        comment.setProductId(productId);
        comment.setText(text);
        comment.setRating(rating);
        comment.setTimestamp(new Date());
        boolean success = commentService.saveComment(comment);
        System.out.println(success);
        if (success) {
            response.sendRedirect("detail?id=" + productId);

        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to add comment.");
        }
    }
}
