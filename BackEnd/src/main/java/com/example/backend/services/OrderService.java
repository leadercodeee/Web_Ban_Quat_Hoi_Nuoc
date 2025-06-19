package com.example.backend.services;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.Order;
import com.example.backend.models.UserKey;
import com.example.backend.utils.DigitalSignatureUtil;
import com.example.backend.utils.HashUtil;
import com.example.backend.utils.OrderUtil;

import java.security.PrivateKey;
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách đơn hàng", e);
            return List.of();
        }
    }

    public Order getOrderById(String orderId) {
        try {
            return orderDAO.getOrderById(orderId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy order theo id: " + orderId, e);
            return null;
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        try {
            return orderDAO.getOrdersByUserId(userId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy đơn hàng theo userId: " + userId, e);
            return List.of();
        }
    }

    public boolean createOrderWithSignature(Order order) {
        try {
            UserKey userKey = userKeyDAO.getKeyByUserId(order.getUserId());
            if (userKey == null) {
                LOGGER.warning("Không tìm thấy private key cho userId: " + order.getUserId());
                return false;
            }

            PrivateKey privateKey = DigitalSignatureUtil.decodePrivateKey(userKey.getPrivateKey());

            // Hash hóa đơn
            InvoiceHashService hashService = new InvoiceHashService();
            String orderHash = hashService.generateOrderHash(order);
            order.setHash(orderHash);

            // Ký số hash
            byte[] hashBytes = HashUtil.hexStringToByteArray(orderHash);
            String signatureBase64 = DigitalSignatureUtil.signRaw(hashBytes, privateKey);
            order.setSignature(signatureBase64);

            // Lưu đơn hàng
            int generatedId = orderDAO.saveOrder(order);
            return generatedId > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tạo order có chữ ký số", e);
            return false;
        }
    }

}
