
package com.example.backend.services;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderPlaceService {

    public int saveOrder(Order order) {
        int orderId = -1;

        // SQL query to insert a new order
        String query = "INSERT INTO orders (user_id, order_date, delivery_date, status, total_amount, shipping_address, payment_method) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnect.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the query
            statement.setInt(1, order.getUserId());

            // Set orderDate and deliveryDate
            statement.setTimestamp(2, order.getOrderDate());  // Set orderDate as Timestamp
            statement.setDate(3, order.getDeliveryDate());  // Set deliveryDate as Date (not Timestamp)

            // Ensure status is a valid enum value or string
            statement.setString(4, order.getStatus());  // Assuming status is a string (or you could use toString() if it's an enum)
            statement.setDouble(5, order.getTotalAmount());
            statement.setString(6, order.getShippingAddress());
            statement.setString(7, order.getPaymentMethod());

            // Execute the query and get the generated keys (order ID)
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1); // Get the generated order ID
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL Exception
        }

        return orderId; // Return the generated order ID
    }
}
