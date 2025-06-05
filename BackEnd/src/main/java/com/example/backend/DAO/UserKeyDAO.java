package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.UserKey;
import com.example.backend.utils.KeyUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.*;

public class UserKeyDAO {

    // Lấy khóa theo userId
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
            return null;
        }
    }

    // Lưu hoặc cập nhật khóa cho user (Insert hoặc Update)
    public void saveKey(UserKey key) throws SQLException {
        String sql = "INSERT INTO user_keys (user_id, public_key, private_key) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE public_key = VALUES(public_key), private_key = VALUES(private_key)";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, key.getUserId());
            stmt.setString(2, key.getPublicKey());
            stmt.setString(3, key.getPrivateKey());

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

    /**
     * Chuyển PrivateKey và PublicKey thành Base64 rồi lưu vào DB.
     * @param userId Id của user.
     * @param privateKey PrivateKey object.
     * @param publicKey PublicKey object.
     * @throws SQLException lỗi khi truy vấn DB.
     */
    public void saveUserKey(int userId, PrivateKey privateKey, PublicKey publicKey) throws SQLException {
        String privateKeyStr = KeyUtil.privateKeyToBase64(privateKey);
        String publicKeyStr = KeyUtil.publicKeyToBase64(publicKey);

        UserKey key = new UserKey(userId, publicKeyStr, privateKeyStr);
        saveKey(key);
    }

    /**
     * Tạo cặp khóa mới, lưu vào DB và trả về UserKey đối tượng.
     * Dùng cho reset hoặc tạo mới khóa.
     *
     * @param userId Id user cần tạo khóa
     * @return UserKey mới lưu trong DB
     * @throws Exception lỗi tạo hoặc lưu khóa
     */
    public UserKey generateAndSaveNewKeyPair(int userId) throws Exception {
        // Tạo cặp khóa mới
        var keyPair = KeyUtil.generateKeyPair();

        // Lưu vào DB
        saveUserKey(userId, keyPair.getPrivate(), keyPair.getPublic());

        // Trả về UserKey mới
        return new UserKey(userId,
                KeyUtil.publicKeyToBase64(keyPair.getPublic()),
                KeyUtil.privateKeyToBase64(keyPair.getPrivate())
        );
    }
}
