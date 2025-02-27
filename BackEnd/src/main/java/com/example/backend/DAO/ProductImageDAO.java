
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.ProductImage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public List<ProductImage> getImagesByProductId(int productId) throws SQLException {
        String query = "SELECT * FROM product_images WHERE product_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();

        List<ProductImage> images = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String imageUrl = rs.getString("image_url");
            boolean isPrimary = rs.getBoolean("is_primary");
            Timestamp createdAt = rs.getTimestamp("created_at");
            Timestamp updatedAt = rs.getTimestamp("updated_at");

            ProductImage image = new ProductImage(id, productId, imageUrl, isPrimary, createdAt.toLocalDateTime(), updatedAt.toLocalDateTime());
            images.add(image);
        }

        return images;
    }
    public void addImage(int productId, String imageUrl, boolean isPrimary) {
        String sql = "INSERT INTO product_images (product_id, image_url, is_primary) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setString(2, imageUrl);
            stmt.setBoolean(3, isPrimary);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
