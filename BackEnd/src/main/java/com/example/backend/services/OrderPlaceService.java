package com.example.backend.services;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DB.DBConnect;
import com.example.backend.models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderPlaceService {

    public int saveOrder(Order order) {
        int orderId = -1;

        // Kết hợp cả hai phiên bản: thêm 'phone' và 'signature' vào câu lệnh SQL
        String query = "INSERT INTO orders (user_id, order_date, delivery_date, status, total_amount, shipping_address, payment_method, signature) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // Thêm 1 dấu hỏi chấm cho 'signature'

        try (Connection connection = DBConnect.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, order.getUserId());
            statement.setTimestamp(2, order.getOrderDate());  // Set orderDate as Timestamp
            statement.setDate(3, order.getDeliveryDate());  // Set deliveryDate as Date (not Timestamp)

            // Ensure status is a valid enum value or string
            statement.setString(4, order.getStatus());  // Assuming status is a string (or you could use toString() if it's an enum)
            statement.setDouble(5, order.getTotalAmount());
            statement.setString(6, order.getShippingAddress());
            statement.setString(7, order.getPaymentMethod());
            // Gán chữ ký số Base64 vào tham số thứ 9 (vì đã thêm phone vào vị trí 2)
            statement.setString(8, order.getSignature());


            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderId;
    }


    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = DBConnect.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setDeliveryDate(rs.getDate("delivery_date"));
                    order.setStatus(rs.getString("status"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setShippingAddress(rs.getString("shipping_address"));
                    order.setPaymentMethod(rs.getString("payment_method"));
                    order.setSignature(rs.getString("signature"));  // Lấy chữ ký số

                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public void updateOrderHash(int orderId, String orderHash) {
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateHash(orderId, orderHash);
    }

}