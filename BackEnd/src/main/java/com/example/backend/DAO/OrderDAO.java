package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection connection = DBConnect.getInstance().getConnection();

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = """
            SELECT o.*, u.username, u.fullName, u.phone
            FROM orders o
            JOIN users u ON o.user_id = u.id
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = mapResultSetToOrderWithUser(rs);
                orders.add(order);
            }
        }
        return orders;
    }

    public Order getOrderById(String orderId) {
        Order order = null;
        String query = """
            SELECT o.*, u.username, u.fullName, u.phone
            FROM orders o
            JOIN users u ON o.user_id = u.id
            WHERE o.id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = mapResultSetToOrderWithUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public int saveOrder(Order order) throws SQLException {
        String query = """
            INSERT INTO orders 
            (user_id, total_amount, shipping_address, payment_method, order_date, delivery_date, status, signature, hashvalue) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        int generatedId = -1;

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setString(3, order.getShippingAddress());
            stmt.setString(4, order.getPaymentMethod());
            stmt.setTimestamp(5, order.getOrderDate());
            stmt.setDate(6, order.getDeliveryDate());
            stmt.setString(7, order.getStatus());
            stmt.setString(8, order.getSignature());
            stmt.setString(9, order.getHash());

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
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setShippingAddress(rs.getString("shipping_address"));
                    order.setPaymentMethod(rs.getString("payment_method"));
                    order.setDeliveryDate(rs.getDate("delivery_date"));
                    order.setStatus(rs.getString("status"));
                    order.setSignature(rs.getString("signature"));
                    order.setHash(rs.getString("hashvalue"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order mapResultSetToOrderWithUser(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setOrderDate(rs.getTimestamp("order_date"));

        Date deliveryDate = rs.getDate("delivery_date");
        order.setDeliveryDate(deliveryDate);

        order.setStatus(rs.getString("status"));
        order.setSignature(rs.getString("signature"));
        order.setHash(rs.getString("hashvalue"));

        order.setUsername(rs.getString("username"));
        order.setFullName(rs.getString("fullName"));
        order.setPhone(rs.getString("phone"));

        return order;
    }
    public boolean updateOrderSignatureAndHash(Order order) {
        String sql = "UPDATE orders SET signature = ?, hashvalue = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, order.getSignature());
            stmt.setString(2, order.getHash());
            stmt.setInt(3, order.getId());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.err.println("Update signature and hash failed: no order found with id " + order.getId());
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Exception during updateOrderSignatureAndHash: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
