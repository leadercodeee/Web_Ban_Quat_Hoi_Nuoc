
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO reviews (username, product_id, comment, rating, created_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, comment.getUsername());
            preparedStatement.setInt(2, comment.getProductId());
            preparedStatement.setString(3, comment.getText());
            preparedStatement.setInt(4, comment.getRating());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(comment.getTimestamp().getTime()));
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Comment> getCommentsByProductId(int productId) {
        String sql = "SELECT * FROM reviews WHERE product_id = ? ORDER BY created_at DESC";
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setProductId(rs.getInt("product_id"));
                comment.setUsername(rs.getString("username"));
                comment.setText(rs.getString("comment"));
                comment.setRating(rs.getInt("rating"));
                comment.setTimestamp(rs.getTimestamp("created_at"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
