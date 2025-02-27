
package com.example.backend.services;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    public List<Order> getAllOrders() {
        try {
            return orderDAO.getAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public Order getOrderById(String orderId){
        return orderDAO.getOrderById(orderId);
    }
}
