package com.example.backend.services;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.Order;
import com.example.backend.models.UserKey;
import com.example.backend.utils.DigitalSignatureUtil;

import java.security.PrivateKey;

public class OrderSignatureService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final UserKeyDAO userKeyDAO = new UserKeyDAO();

    /**
     * Ký một đơn hàng bằng private key tương ứng với user_id, dựa trên hash của đơn hàng.
     */
    public boolean signOrder(Order order) {
        try {
            if (order.getHash() == null || order.getHash().isEmpty()) {
                System.err.println("Order hash is missing, cannot sign.");
                return false;
            }

            // Lấy khóa người dùng
            UserKey userKey = userKeyDAO.getKeyByUserId(order.getUserId());
            if (userKey == null || userKey.getPrivateKey() == null) {
                System.err.println("Private key not found for user_id = " + order.getUserId());
                return false;
            }

            // Decode private key
            PrivateKey privateKey = DigitalSignatureUtil.decodePrivateKey(userKey.getPrivateKey());

            // Ký dữ liệu hash
            String signature = DigitalSignatureUtil.sign(order.getHash(), privateKey);

            // Cập nhật chữ ký vào DB
            order.setSignature(signature);
            return orderDAO.updateOrderSignatureAndHash(order);

        } catch (Exception e) {
            System.err.println("Error while signing order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
