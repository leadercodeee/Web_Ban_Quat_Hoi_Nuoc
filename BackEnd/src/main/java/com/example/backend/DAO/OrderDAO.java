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
                order.setSignature(rs.getString("signature")); // ✅ Thêm dòng này
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
                    order.setSignature(resultSet.getString("signature")); // ✅ Thêm dòng này
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public int saveOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (user_id, total_amount, shipping_address, payment_method, order_date, delivery_date, status, signature) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setString(3, order.getShippingAddress());
            stmt.setString(4, order.getPaymentMethod());
            stmt.setTimestamp(5, order.getOrderDate());
            stmt.setDate(6, order.getDeliveryDate());
            stmt.setString(7, order.getStatus());
            stmt.setString(8, order.getSignature()); // ✅ Đã có

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }
        }

        return generatedId;
    }

    public boolean updateSignature(int orderId, String signature) {
        String sql = "UPDATE orders SET signature = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, signature);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
