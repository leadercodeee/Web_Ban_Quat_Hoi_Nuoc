package com.example.backend.services;

import com.example.backend.DAO.UserDAO;
import com.example.backend.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean addUser(User user) {
        // Kiểm tra trùng email trước khi thêm
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false; // Email đã tồn tại
        }
        return userDAO.addUser(user); // Mã hóa đã được xử lý ở DAO
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
        return userDAO.updatePassword(userId, newPassword); // DAO sẽ tự mã hóa
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean register(User user) {
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false; // Email đã tồn tại
        }
        return userDAO.register(user); // Mã hóa trong DAO
    }

    public boolean resetPassword(String email, String newPassword) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return false;
        }
        userDAO.updatePassword(email, newPassword); // DAO sẽ mã hóa
        return true;
    }
}
