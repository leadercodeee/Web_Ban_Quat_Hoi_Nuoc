package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.User;
import com.example.backend.utils.HashUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        return DBConnect.getInstance().getConnection();
    }

    private String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password không được để trống");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getAllUsers: " + e.getMessage());
        }
        return users;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, email, fullName, phone, dob, address, role, status, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, hashPassword(user.getPassword()));
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getPhone());
            if (user.getDob() != null && !user.getDob().isEmpty()) {
                ps.setDate(6, Date.valueOf(user.getDob()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getRole() != null ? user.getRole() : "user");
            ps.setString(9, user.getStatus() != null ? user.getStatus() : "Active");

            int affectedRows = ps.executeUpdate();
            System.out.println("addUser affected rows: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error addUser: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int affectedRows = ps.executeUpdate();
            System.out.println("deleteUser affected rows: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleteUser: " + e.getMessage());
            return false;
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getUserById: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hashPassword(newPassword));
            ps.setInt(2, userId);
            int affectedRows = ps.executeUpdate();
            System.out.println("updatePassword by id affected rows: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updatePassword by id: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET fullName = ?, username = ?, email = ?, phone = ?, dob = ?, address = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            if (user.getDob() != null && !user.getDob().isEmpty()) {
                ps.setDate(5, Date.valueOf(user.getDob()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getId());

            int affectedRows = ps.executeUpdate();
            System.out.println("updateUser affected rows: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updateUser: " + e.getMessage());
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getUserByEmail: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePasswordByEmail(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hashPassword(newPassword));
            ps.setString(2, email);
            int affectedRows = ps.executeUpdate();
            System.out.println("updatePassword by email affected rows: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updatePassword by email: " + e.getMessage());
            return false;
        }
    }

    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, email, fullName, phone, role, status, dob, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            // Ở đây không gọi hashPassword nữa, vì password đã được hash ở Controller
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole() != null ? user.getRole() : "user");
            ps.setString(7, user.getStatus() != null ? user.getStatus() : "Active");
            if (user.getDob() != null && !user.getDob().isEmpty()) {
                ps.setDate(8, Date.valueOf(user.getDob()));
            } else {
                ps.setNull(8, Types.DATE);
            }
            ps.setString(9, user.getAddress());

            int affectedRows = ps.executeUpdate();
            System.out.println("register affected rows: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error register: " + e.getMessage());
            return false;
        }
    }


    public User login(String email, String password) {
        if (email == null || password == null) {
            System.out.println("Email hoặc password là null");
            return null;
        }
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (hashedPassword.startsWith("$2a$") || hashedPassword.startsWith("$2b$")) {
                        // BCrypt check
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            return mapResultSetToUser(rs);
                        } else {
                            System.out.println("Password không khớp với hash BCrypt trong DB");
                        }
                    } else {
                        // SHA-256 check (cũ)
                        if (HashUtil.sha256(password).equals(hashedPassword)) {
                            // Optionally nâng cấp password lên BCrypt:
                            updatePassword(rs.getInt("id"), password);
                            return mapResultSetToUser(rs);
                        } else {
                            System.out.println("Password không khớp với hash SHA-256 trong DB");
                        }
                    }
                } else {
                    System.out.println("Không tìm thấy user với email: " + email);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error login: " + e.getMessage());
        }
        return null;
    }


    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("fullName"),
                rs.getString("phone"),
                rs.getString("role"),
                rs.getString("status"),
                rs.getDate("dob") != null ? rs.getDate("dob").toString() : null,
                rs.getString("address")
        );
    }
    public String getPublicKeyByUserId(int userId) throws SQLException {
        String sql = "SELECT public_key FROM users WHERE id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("public_key");
                }
            }
        }
        return null;
    }
}
