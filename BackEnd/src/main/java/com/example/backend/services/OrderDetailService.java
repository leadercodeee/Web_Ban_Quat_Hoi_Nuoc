
package com.example.backend.services;

import com.example.backend.DAO.OrderDetailDAO;
import com.example.backend.models.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailService {
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        return orderDetailDAO.getOrderDetailsByOrderId(orderId);
    }
}
