package com.example.backend.services;

import com.example.backend.DAO.UserDAO;
import com.example.backend.models.User;

public class ResetPasswordService {
    private final UserDAO userDao;

    public ResetPasswordService() {
        this.userDao = new UserDAO();
    }

    /**
     * Cập nhật mật khẩu mới nếu hợp lệ
     *
     * @param email           Email người dùng
     * @param newPassword     Mật khẩu mới
     * @param confirmPassword Xác nhận mật khẩu mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updatePassword(String email, String newPassword, String confirmPassword) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email không được để trống.");
            return false;
        }

        if (newPassword == null || newPassword.length() < 6) {
            System.out.println("Mật khẩu phải có ít nhất 6 ký tự.");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Xác nhận mật khẩu không khớp.");
            return false;
        }

        User existingUser = userDao.getUserByEmail(email);
        if (existingUser == null) {
            System.out.println("Không tìm thấy người dùng với email này.");
            return false;
        }

        return userDao.updatePassword(existingUser.getId(), newPassword);
    }
}
