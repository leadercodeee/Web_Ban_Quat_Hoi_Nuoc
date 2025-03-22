
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OtpDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public void saveOtp(String email, String otp) {
        try {
            String sql = "INSERT INTO otp (email, otp) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, otp);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyOtp(String email, String otp) {
        try {
            String sql = "SELECT * FROM otp WHERE email = ? AND otp = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, otp);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
