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
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    private final OrderDAO orderDAO;
    private final UserKeyDAO userKeyDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.userKeyDAO = new UserKeyDAO();
    }

    public OrderService(OrderDAO orderDAO, UserKeyDAO userKeyDAO) {
        this.orderDAO = orderDAO;
        this.userKeyDAO = userKeyDAO;
    }

    public List<Order> getAllOrders() {
        try {
            return orderDAO.getAllOrders();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách đơn hàng", e);
            return new ArrayList<>();
        }
    }

    public Order getOrderById(String orderId){
        try {
            return orderDAO.getOrderById(orderId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy order theo id: " + orderId, e);
            return null;
        }
    }

    public void signAndSaveOrder(Order order) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("Order không được null");
        }

        // Bước 1: Tạo hash của đơn hàng
        String hash = OrderUtil.hashOrder(order);

        // Bước 2: Lấy khóa đang active của user
        UserKey userKey = userKeyDAO.getKeyByUserId(order.getUserId());
        if (userKey == null) {
            throw new RuntimeException("Người dùng chưa có khóa!");
        }

        // Bước 3: Chuyển privateKey từ Base64 sang đối tượng PrivateKey
        PrivateKey privateKey = DigitalSignatureUtil.decodePrivateKey(userKey.getPrivateKey());

        // Bước 4: Ký hash của đơn hàng
        String signature = DigitalSignatureUtil.sign(hash, privateKey);

        // Bước 5: Gán chữ ký vào order
        order.setSignature(signature);

        // Bước 6: Lưu hoặc cập nhật order
        if (order.getId() > 0) {
            boolean updated = orderDAO.updateSignature(order.getId(), signature);
            if (!updated) {
                throw new RuntimeException("Không thể cập nhật chữ ký cho đơn hàng id: " + order.getId());
            }
            LOGGER.info("Cập nhật chữ ký thành công cho đơn hàng id: " + order.getId());
        } else {
            int newId = orderDAO.saveOrder(order);
            if (newId <= 0) {
                throw new RuntimeException("Không thể lưu đơn hàng mới");
            }
            order.setId(newId);
            LOGGER.info("Lưu đơn hàng mới thành công với id: " + newId);
        }
    }
    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

}
