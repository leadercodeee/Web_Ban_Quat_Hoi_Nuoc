package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.UserKey;
import com.example.backend.utils.KeyUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.*;

public class UserKeyDAO {

    // Lấy khóa từ DB theo userId
    public UserKey getKeyByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM user_keys WHERE user_id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserKey(
                            rs.getInt("user_id"),
                            rs.getString("public_key"),
                            rs.getString("private_key")
                    );
                }
            }
        }
        return null;
    }

    // Lưu hoặc cập nhật cặp khóa vào DB
    public void saveKey(UserKey key) throws SQLException {
        String sql = """
            INSERT INTO user_keys (user_id, public_key, private_key)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE public_key = VALUES(public_key), private_key = VALUES(private_key)
        """;
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, key.getUserId());
            stmt.setString(2, key.getPublicKey());
            stmt.setString(3, key.getPrivateKey());
            stmt.executeUpdate();
        }
    }

    // Xóa khóa khỏi DB
    public void deleteKeyByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM user_keys WHERE user_id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // Lưu khóa dưới dạng Base64 vào DB
    public void saveUserKey(int userId, PrivateKey privateKey, PublicKey publicKey) throws SQLException {
        String privateKeyStr = KeyUtil.privateKeyToBase64(privateKey);
        String publicKeyStr = KeyUtil.publicKeyToBase64(publicKey);
        UserKey key = new UserKey(userId, publicKeyStr, privateKeyStr);
        saveKey(key);
    }

    // Tạo cặp khóa mới, lưu DB, và trả về đối tượng UserKey
    public UserKey generateAndSaveNewKeyPair(int userId) throws Exception {
        var keyPair = KeyUtil.generateKeyPair();
        saveUserKey(userId, keyPair.getPrivate(), keyPair.getPublic());
        return new UserKey(
                userId,
                KeyUtil.publicKeyToBase64(keyPair.getPublic()),
                KeyUtil.privateKeyToBase64(keyPair.getPrivate())
        );
    }
}
