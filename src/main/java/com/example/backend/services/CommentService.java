
package com.example.backend.services;

import com.example.backend.DAO.CommentDAO;
import com.example.backend.models.Comment;

import java.util.List;

public class CommentService {
    private CommentDAO commentDAO;
    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }
    public boolean saveComment(Comment comment) {
        if (comment.getText().isEmpty() || comment.getRating() <= 0) {
            throw new IllegalArgumentException("Comment text or rating is invalid.");
        }
        return commentDAO.addComment(comment);
    }
    public List<Comment> getCommentsByProductId(int productId){
        return commentDAO.getCommentsByProductId(productId);
    }
}
