
package com.example.backend.services;

import com.example.backend.DAO.UserDAO;

public class ResetPasswordService {
    private UserDAO userDao;

    public ResetPasswordService() {
        userDao = new UserDAO();
    }

    public boolean updatePassword(String email, String newPassword, String confirmPassword) {
        if (newPassword.equals(confirmPassword)) {
            userDao.updatePassword(email, newPassword);
            return true;
        }
        return false;
    }
}
