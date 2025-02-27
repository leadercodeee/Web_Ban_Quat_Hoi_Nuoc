
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setDeliveryDate(new Date(rs.getTimestamp("delivery_date").getTime()));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }
    public Order getOrderById(String orderId) {
        Order order = null;
        String query = "SELECT * FROM orders WHERE id = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order();
                    order.setId(resultSet.getInt("id"));
                    order.setUserId(resultSet.getInt("user_id"));
                    order.setTotalAmount(resultSet.getDouble("total_amount"));
                    order.setShippingAddress(resultSet.getString("shipping_address"));
                    order.setPaymentMethod(resultSet.getString("payment_method"));
                    order.setOrderDate(resultSet.getTimestamp("order_date"));
                    order.setDeliveryDate(new Date(resultSet.getTimestamp("delivery_date").getTime()));
                    order.setStatus(resultSet.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

}
