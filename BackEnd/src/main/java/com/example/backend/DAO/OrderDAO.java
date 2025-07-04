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

                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setDeliveryDate(new Date(rs.getTimestamp("delivery_date").getTime()));
                order.setStatus(rs.getString("status"));
                order.setSignature(rs.getString("signature"));
                order.setPublicKeyId(rs.getLong("publicKeyId"));

                Order order = mapResultSetToOrderWithUser(rs);

                orders.add(order);
            }
        }
        return orders;
    }
    public boolean updateOrderSignatureAndHash(int orderId, String hash, String signature) {
        String sql = "UPDATE orders SET hash = ?, signature = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hash);
            stmt.setString(2, signature);
            stmt.setInt(3, orderId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Order getOrderById(String orderId) {
        Order order = null;
        String query = """
            SELECT o.*, u.username, u.fullName, u.phone
            FROM orders o
            JOIN users u ON o.user_id = u.id
            WHERE o.id = ?
        """;


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
                    order.setSignature(resultSet.getString("signature"));
                    order.setPublicKeyId(resultSet.getLong("publicKeyId"));

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


    }



    public int saveOrder(Order order) throws SQLException {
        String query = """
            INSERT INTO orders 
            (user_id, total_amount, shipping_address, payment_method, order_date, delivery_date, status, signature, hash) 
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
                    order.setHash(rs.getString("hash"));
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
        order.setHash(rs.getString("hash"));

        order.setUsername(rs.getString("username"));
        order.setFullName(rs.getString("fullName"));
        order.setPhone(rs.getString("phone"));

        return order;
    }
    public boolean updateOrderSignatureAndHash(Order order) {
        String sql = "UPDATE orders SET signature = ?, hash = ? WHERE id = ?";
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
    // Lấy danh sách đơn hàng chưa có hash
    public List<Order> getOrdersWithoutHash() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE hash IS NULL";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setPaymentMethod(rs.getString("payment_method"));
                // thêm các field khác nếu cần
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Cập nhật hash vào DB
    public void updateHash(int orderId, String hash) {
        String sql = "UPDATE orders SET hash = ? WHERE id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hash);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
