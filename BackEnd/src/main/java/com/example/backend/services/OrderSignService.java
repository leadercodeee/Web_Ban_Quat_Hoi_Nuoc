package com.example.backend.services;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.Order;
import com.example.backend.models.UserKey;
import com.example.backend.utils.DigitalSignatureUtil;
import com.example.backend.utils.HashUtil;
import com.example.backend.utils.OrderUtil;

import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderSignService {

    private static final Logger LOGGER = Logger.getLogger(OrderSignService.class.getName());

    private final OrderDAO orderDAO = new OrderDAO();
    private final UserKeyDAO userKeyDAO = new UserKeyDAO();

    /**
     * Ký một đơn hàng đã tồn tại trong database theo ID
     */
    public boolean signOrder(String orderId) {
        try {
            // 1. Lấy order từ database
            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                LOGGER.warning("Order không tồn tại với id: " + orderId);
                return false;
            }

            // 2. Tạo chuỗi dữ liệu để hash
            String dataString = OrderUtil.toDataString(order);

            // 3. Tính hash SHA-256
            String hash = HashUtil.sha256(dataString);
            order.setHash(hash);

            // 4. Lấy private key từ database
            UserKey userKey = userKeyDAO.getKeyByUserId(order.getUserId());
            if (userKey == null) {
                LOGGER.warning("Chưa có khóa private key cho userId: " + order.getUserId());
                return false;
            }



            // 5. Ký hash trên byte array của hash hex string
            byte[] hashBytes = HashUtil.hexStringToByteArray(hash);
          //  String signature = DigitalSignatureUtil.signRaw(hashBytes);
          //  order.setSignature(signature);

            // 6. Cập nhật lại order trong DB với signature và hashvalue
            boolean updated = orderDAO.updateOrderSignatureAndHash(order);
            if (!updated) {
                LOGGER.warning("Cập nhật signature và hashvalue cho order id=" + orderId + " thất bại");
            }
            return updated;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi ký đơn hàng id=" + orderId, e);
            return false;
        }
    }

    /**
     * Ký và lưu đơn hàng mới
     */
    public boolean signAndSaveOrder(Order order) {
        try {
            // 1. Lưu order tạm thời với các trường chưa có hash, signature (id chưa có)
            int generatedId = orderDAO.saveOrder(order);
            if (generatedId <= 0) {
                LOGGER.warning("Lưu đơn hàng thất bại cho userId: " + order.getUserId());
                return false;
            }
            order.setId(generatedId); // cập nhật id mới nhận được

            // 2. Tạo chuỗi dữ liệu để hash với id đã có
            String dataString = OrderUtil.toDataString(order);
            LOGGER.info("Data string for hashing: " + dataString);

            // 3. Tính hash SHA-256
            String hash = HashUtil.sha256(dataString);
            LOGGER.info("Generated hash: " + hash);
            order.setHash(hash);

            // 4. Lấy private key
            UserKey userKey = userKeyDAO.getKeyByUserId(order.getUserId());
            if (userKey == null) {
                LOGGER.warning("User chưa có private key, userId: " + order.getUserId());
                return false;
            }


            // 5. Ký hash trên byte array
            byte[] hashBytes = HashUtil.hexStringToByteArray(hash);
           // String signature = DigitalSignatureUtil.signRaw(hashBytes);
          //  order.setSignature(signature);

            // 6. Cập nhật signature và hashvalue vào order đã lưu
            boolean updated = orderDAO.updateOrderSignatureAndHash(order);
            if (!updated) {
                LOGGER.warning("Cập nhật signature và hashvalue thất bại cho order id=" + generatedId);
                return false;
            }

            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi ký và lưu đơn hàng mới", e);
            return false;
        }
    }

}
