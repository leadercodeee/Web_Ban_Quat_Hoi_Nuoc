
package com.example.backend.DAO;



import com.example.backend.DB.DBConnect;
import com.example.backend.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private static CategoryDAO instance;

    private CategoryDAO() {
    }
    Connection connection = DBConnect.getInstance().getConnection();
    public static CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    // Phương thức thêm danh mục
    public boolean addCategory(Category category) {
        String query = "INSERT INTO categories (name, description) VALUES (?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());

            return ps.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name, description, created_at, updated_at FROM categories";
        try (
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Phương thức lấy danh mục theo ID
    public Category getCategoryById(int id) {
        Category category = null;
        String sql = "SELECT * FROM categories WHERE id = ?"; // Truy vấn lấy danh mục theo ID

        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            // Thiết lập tham số ID vào câu truy vấn
            ps.setInt(1, id);

            // Thực hiện truy vấn và nhận kết quả
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Ánh xạ kết quả từ ResultSet vào đối tượng Category
                    int categoryId = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");

                    // Chuyển đổi LocalDateTime từ định dạng chuỗi
                    String createdAtString = rs.getString("created_at");
                    String updatedAtString = rs.getString("updated_at");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime createdAt = LocalDateTime.parse(createdAtString, formatter);
                    LocalDateTime updatedAt = LocalDateTime.parse(updatedAtString, formatter);

                    // Tạo đối tượng Category từ dữ liệu lấy được
                    category = new Category(categoryId, name, description, createdAt, updatedAt);
                }
            }

        } catch (SQLException e) {
            // Xử lý lỗi kết nối hoặc truy vấn
            e.printStackTrace();
        }

        return category;
    }

    public boolean deleteCategory(int id) {
        String query = "DELETE FROM categories WHERE id = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Cập nhật danh mục
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ?, updated_at = NOW() WHERE id = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        Category c1 = CategoryDAO.getInstance().getCategoryById(6);
        System.out.println(c1);
    }
}
