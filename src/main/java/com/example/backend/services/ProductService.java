
package com.example.backend.services;

import com.example.backend.DAO.ProductDAO;
import com.example.backend.DB.DBConnect;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    ProductDAO productDAO = new ProductDAO();
    public List<Product> getAllProducts(){
        return productDAO.getAllProducts();
    }
    public Product getProductById(int id){
        return productDAO.getProductById(id);
    }
    public int addProduct(Product product){
        return productDAO.addProduct(product);
    }
    public boolean deleteProduct(int productId) {
        return productDAO.deleteProductById(productId);
    }
    public List<Product> getTop3Products() {
        try {
            return productDAO.getTop3Products();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateProduct(Product product) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Kết nối cơ sở dữ liệu
            conn = DBConnect.getInstance().getConnection();

            // Cập nhật thông tin sản phẩm
            String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, brand = ?, category_id = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getBrand());
            ps.setInt(6, product.getCategoryId());
            ps.setInt(7, product.getId());
            ps.executeUpdate();

            // Cập nhật hình ảnh
            updateProductImages(product);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng kết nối
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProductImages(Product product) {
        // Xóa các hình ảnh cũ của sản phẩm
        String deleteSQL = "DELETE FROM product_images WHERE product_id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteSQL)) {
            ps.setInt(1, product.getId());
            ps.executeUpdate();

            String insertSQL = "INSERT INTO product_images (product_id, image_url, is_primary, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertPs = conn.prepareStatement(insertSQL)) {
                for (ProductImage image : product.getImages()) {
                    if (image.getImageUrl() != null && !image.getImageUrl().isEmpty()) {
                        insertPs.setInt(1, product.getId());
                        insertPs.setString(2, image.getImageUrl());
                        insertPs.setBoolean(3, image.isPrimary());
                        insertPs.setObject(4, image.getCreatedAt());
                        insertPs.setObject(5, image.getUpdatedAt());
                        insertPs.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Product> getProductsByCategoryId(int categoryId){
        return productDAO.getProductsByCategoryId(categoryId);
    }
    public List<Product> get5ProductsByCategoryId(int categoryId){
        return productDAO.get5ProductsByCategoryId(categoryId);
    }
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }
        return productDAO.searchProducts(keyword);
    }
}
