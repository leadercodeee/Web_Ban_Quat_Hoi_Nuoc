package com.example.backend.DAO;

import com.example.backend.models.KeyHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeyDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/your_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "your_password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Lưu public key mới vào lịch sử
    public void saveKey(String customerId, String publicKey) {
        String sql = "INSERT INTO key_history (customer_id, public_key) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            stmt.setString(2, publicKey);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Trả về danh sách lịch sử tạo khóa
    public List<KeyHistory> getKeyHistory(String customerId) {
        List<KeyHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM key_history WHERE customer_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KeyHistory history = new KeyHistory();
                history.setId(rs.getInt("id"));
                history.setCustomerId(rs.getString("customer_id"));
                history.setPublicKey(rs.getString("public_key"));
                history.setCreatedAt(rs.getTimestamp("created_at"));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    // Lấy public key mới nhất
    public String getCurrentPublicKey(String customerId) {
        String sql = "SELECT public_key FROM key_history WHERE customer_id = ? ORDER BY created_at DESC LIMIT 1";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("public_key");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
