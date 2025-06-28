package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.UserKey;
import com.example.backend.utils.KeyUtil;

import java.security.KeyPair;
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
                            rs.getString("public_key")
                    );
                }
            }
        }
        return null;
    }

    // Lưu hoặc cập nhật publicKey vào DB (KHÔNG lưu privateKey nữa)
    public void saveKey(UserKey key) throws SQLException {
        String sql = """
            INSERT INTO user_keys (user_id, public_key)
            VALUES (?, ?)
            ON DUPLICATE KEY UPDATE public_key = VALUES(public_key)
        """;
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, key.getUserId());
            stmt.setString(2, key.getPublicKey());
            stmt.executeUpdate();
        }
    }

    // Xóa khóa theo userId
    public void deleteKeyByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM user_keys WHERE user_id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // Lưu publicKey vào DB (KHÔNG truyền privateKey nữa)
    public void saveUserKey(int userId, PublicKey publicKey) throws SQLException {
        String publicKeyStr = KeyUtil.publicKeyToBase64(publicKey);
        UserKey key = new UserKey(userId, publicKeyStr);
        saveKey(key);
    }

    // Tạo cặp khóa mới, lưu publicKey vào DB và lưu privateKey ra file
    public UserKey generateAndSaveNewKeyPair(int userId) throws Exception {
        KeyPair keyPair = KeyUtil.generateKeyPair();

        // 1. Lưu publicKey vào DB
        saveUserKey(userId, keyPair.getPublic());

        // 2. Lưu privateKey ra file PEM (ví dụ: /keys/user_123/private_key.pem)
        String path = "keys/user_" + userId + "/private_key.pem";
        KeyUtil.savePrivateKeyToPemFile(keyPair.getPrivate(), path);

        return new UserKey(userId, KeyUtil.publicKeyToBase64(keyPair.getPublic()));
    }
}
