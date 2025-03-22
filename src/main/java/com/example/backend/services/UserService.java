
package com.example.backend.services;

import com.example.backend.DAO.UserDAO;
import com.example.backend.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    public boolean addUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        return userDAO.addUser(user);
    }
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    public boolean deleteUser(int userId){
        return userDAO.deleteUser(userId);
    }
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            return false;
        }
        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return userDAO.updatePassword(userId, hashedPassword);
    }
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
}
