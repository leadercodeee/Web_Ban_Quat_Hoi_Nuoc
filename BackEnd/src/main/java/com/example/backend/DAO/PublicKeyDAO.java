package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.PublicKey;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicKeyDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public long save(PublicKey key) throws SQLException {
        String sql = "INSERT INTO public_key (public_key, ex_timestamp, status, cr_timestamp, user_id, name) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, key.getPublicKey());
            stmt.setTimestamp(2, key.getExTimestamp());               // có thể null
            stmt.setByte(3, key.getStatus());
            stmt.setTimestamp(4, key.getCrTimestamp());
            stmt.setInt(5, key.getUser_id());
            stmt.setString(6, key.getName());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    key.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("Insert public_key failed: no ID returned");
    }
    public PublicKey findById(long id) throws SQLException {
        String sql = "SELECT * FROM public_key WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }
    public boolean update(PublicKey key) throws SQLException {
        String sql = "UPDATE public_key SET public_key = ?, ex_timestamp = ?, status = ?, cr_timestamp = ?, user_id = ?, name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, key.getPublicKey());
            stmt.setTimestamp(2, key.getExTimestamp());
            stmt.setByte(3, key.getStatus());
            stmt.setTimestamp(4, key.getCrTimestamp());
            stmt.setInt(5, key.getUser_id());
            stmt.setString(6, key.getName());
            stmt.setLong(7, key.getId());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean deleteById(long id) throws SQLException {
        String sql = "DELETE FROM public_key WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public List<PublicKey> findByUserIdSorted(int userId) throws SQLException {
        List<PublicKey> keys = new ArrayList<>();
        String sql = "SELECT * FROM public_key WHERE user_id = ? ORDER BY cr_timestamp DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    keys.add(map(rs));
                }
            }
        }
        return keys;
    }
    public List<PublicKey> findValidPublicKeysByUserId(int userId) throws SQLException {
        List<PublicKey> keys = new ArrayList<>();
        String sql = "SELECT * FROM public_key WHERE user_id = ? AND status = 1 AND (ex_timestamp IS NULL OR ex_timestamp > CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    keys.add(map(rs));
                }
            }
        }
        return keys;
    }

    private PublicKey map(ResultSet rs) throws SQLException {
        PublicKey pk = new PublicKey();
        pk.setId(rs.getLong("id"));
        pk.setPublicKey(rs.getString("public_key"));
        pk.setExTimestamp(rs.getTimestamp("ex_timestamp"));
        pk.setStatus(rs.getByte("status"));
        pk.setCrTimestamp(rs.getTimestamp("cr_timestamp"));
        pk.setUser_id(rs.getInt("user_id"));
        pk.setName(rs.getString("name"));
        return pk;
    }


}
