
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        List<OrderDetail> orderDetailsList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(resultSet.getInt("id"));
                orderDetail.setOrderId(resultSet.getInt("order_id"));
                orderDetail.setProductId(resultSet.getInt("product_id"));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setPrice(resultSet.getDouble("price"));
                orderDetailsList.add(orderDetail);
            }
        }
        return orderDetailsList;
    }
}
