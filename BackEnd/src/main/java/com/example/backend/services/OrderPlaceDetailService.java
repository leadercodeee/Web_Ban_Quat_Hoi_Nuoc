
package com.example.backend.services;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderPlaceDetailService {

    public boolean saveOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity, price, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (Connection connection = DBConnect.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the SQL query
            preparedStatement.setInt(1, orderDetail.getOrderId());
            preparedStatement.setInt(2, orderDetail.getProductId());
            preparedStatement.setInt(3, orderDetail.getQuantity());
            preparedStatement.setDouble(4, orderDetail.getPrice());

            // Execute the query and return true if successful
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row is inserted
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception for debugging
            return false; // Return false if there was an error
        }
    }
}
