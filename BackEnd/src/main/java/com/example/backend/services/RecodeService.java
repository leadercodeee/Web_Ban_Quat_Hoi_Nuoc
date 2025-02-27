
package com.example.backend.services;

import com.example.backend.DAO.OtpDAO;

public class RecodeService {
    private OtpDAO otpDao;

    public RecodeService() {
        otpDao = new OtpDAO();
    }
    public boolean verifyOtp(String email, String otp) {
        return otpDao.verifyOtp(email, otp);
    }
}
