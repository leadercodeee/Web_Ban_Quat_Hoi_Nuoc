package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.UserKey;

import java.sql.*;

public class UserKeyDAO {

    public UserKey getKeyByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM user_keys WHERE user_id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserKey(
                        rs.getInt("user_id"),
                        rs.getString("public_key"),
                        rs.getString("private_key")
                );
            }
            return null;
        }
    }

    public void saveKey(UserKey key) throws SQLException {
        String sql = "INSERT INTO user_keys (user_id, public_key, private_key) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE public_key = ?, private_key = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, key.getUserId());
            stmt.setString(2, key.getPublicKey());
            stmt.setString(3, key.getPrivateKey());
            stmt.setString(4, key.getPublicKey());
            stmt.setString(5, key.getPrivateKey());

            stmt.executeUpdate();
        }
    }

    public void deleteKeyByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM user_keys WHERE user_id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
    public boolean save(UserKey key) {
        String sql = "INSERT INTO UserKeys (userId, publicKey, privateKey) VALUES (?, ?, ?)";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, key.getUserId());
            ps.setString(2, key.getPublicKey());
            ps.setString(3, key.getPrivateKey());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserKey findActiveKeyByUserId(int userId) {
        String sql = "SELECT * FROM UserKeys WHERE userId = ? AND status = 'active'";

        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserKey key = new UserKey();
                key.setUserId(rs.getInt("userId"));
                key.setPublicKey(rs.getString("publicKey"));
                key.setPrivateKey(rs.getString("privateKey"));
                // Nếu bạn có cột status, bạn có thể set nó nữa
                return key;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy khóa active
    }
}
