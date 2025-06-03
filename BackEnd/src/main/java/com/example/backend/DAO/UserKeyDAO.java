package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.UserKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserKeyDAO {
    public void save(UserKey key) throws SQLException {
        Connection conn = DBConnect.getConnection();
        String sql = "INSERT INTO UserKeys(user_id, public_key, private_key, status) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, key.getUserId());
        ps.setString(2, key.getPublicKey());
        ps.setString(3, key.getPrivateKey());
        ps.setString(4, "active");
        ps.executeUpdate();
        conn.close();
    }
    public UserKey findActiveKeyByUserId(int userId) {
        String sql = "SELECT * FROM UserKeys WHERE user_id = ? AND status = 'active' ORDER BY created_at DESC LIMIT 1";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserKey key = new UserKey();
                key.setId(rs.getInt("id"));
                key.setUserId(rs.getInt("user_id"));
                key.setPublicKey(rs.getString("public_key"));
                key.setPrivateKey(rs.getString("private_key"));
                key.setCreatedAt(rs.getTimestamp("created_at"));
                key.setRevokedAt(rs.getTimestamp("revoked_at"));
                key.setStatus(rs.getString("status"));
                return key;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
