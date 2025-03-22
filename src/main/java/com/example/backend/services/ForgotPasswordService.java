package com.example.backend.services;

import com.example.backend.DAO.OtpDAO;
import com.example.backend.DAO.UserDAO;
import com.example.backend.models.User;
import com.example.backend.utils.EmailUtils;

import java.util.Random;

public class ForgotPasswordService {
    private UserDAO userDao;
    private OtpDAO otpDao;
    private EmailUtils emailUtils;
    public ForgotPasswordService() {
        userDao = new UserDAO();
        otpDao = new OtpDAO();
        emailUtils = new EmailUtils();
    }
    public boolean sendResetLink(String email) {
        User user = userDao.getUserByEmail(email);
        if (user != null) {
            // Tạo OTP và gửi email
            String otp = generateOtp();
            emailUtils.sendOtpEmail(user.getEmail(),otp);
            otpDao.saveOtp(user.getEmail(), otp);  // Lưu OTP vào database
            return true;
        }
        return false;
    }
    private String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}