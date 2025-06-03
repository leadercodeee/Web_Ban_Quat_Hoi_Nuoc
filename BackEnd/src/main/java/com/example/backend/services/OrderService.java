package com.example.backend.services;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
import com.example.backend.models.UserKey;
import com.example.backend.utils.DigitalSignatureUtil;
import com.example.backend.utils.OrderUtil;

import java.security.PrivateKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private UserKeyDAO userKeyDAO = new UserKeyDAO();

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

    public void signAndSaveOrder(Order order) throws Exception {
        // Bước 1: Tạo hash của đơn hàng
        String hash = OrderUtil.hashOrder(order);

        // Bước 2: Lấy khóa đang active của user
        UserKey userKey = userKeyDAO.findActiveKeyByUserId(order.getUserId());

        if (userKey == null) {
            throw new RuntimeException("Người dùng chưa có khóa!");
        }

        // Bước 3: Chuyển privateKey từ Base64 sang đối tượng PrivateKey
        PrivateKey privateKey = DigitalSignatureUtil.decodePrivateKey(userKey.getPrivateKey());

        // Bước 4: Ký hash của đơn hàng
        String signature = DigitalSignatureUtil.sign(hash, privateKey);

        // Bước 5: Gán chữ ký vào order
        order.setSignature(signature);

        // Bước 6: Lưu order vào DB
        orderDAO.saveOrder(order);
    }
}
