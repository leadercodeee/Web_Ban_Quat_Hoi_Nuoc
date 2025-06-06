package com.example.backend.services;

import com.example.backend.DAO.UserDAO;
import com.example.backend.DB.DBConnect;
import com.example.backend.models.User;
import java.sql.Connection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean addUser(User user) {
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false; // Email đã tồn tại
        }
        return userDAO.addUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user == null || !BCrypt.checkpw(currentPassword, user.getPassword())) {
            return false;
        }
        return userDAO.updatePassword(userId, newPassword);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean register(User user) {
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        return userDAO.register(user);
    }

    public boolean resetPassword(String email, String newPassword) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return false;
        }
        // Sử dụng userId thay vì email để nhất quán
        return userDAO.updatePassword(user.getId(), newPassword);
    }
    public String getPhoneByUserId(int userId) {
        String phone = null;
        String query = "SELECT phone FROM users WHERE id = ?";

        try (Connection conn = (Connection) DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    phone = rs.getString("phone");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phone;
    }
}
