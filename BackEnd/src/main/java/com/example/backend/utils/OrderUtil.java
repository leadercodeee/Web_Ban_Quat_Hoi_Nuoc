package com.example.backend.utils;

import com.example.backend.models.Order;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat; // Vẫn cần nếu bạn muốn dùng SimpleDateFormat cho định dạng ngày

public class OrderUtil {

    // Tạo chuỗi dữ liệu đại diện cho hóa đơn để hash
    // Giữ phiên bản mới
    public static String getOrderDataString(Order order) {
        // Cần xem xét lại việc .toString() cho Date/Timestamp, có thể không nhất quán
        // Nên dùng SimpleDateFormat như phiên bản cũ nếu muốn định dạng chuẩn xác
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedOrderDate = order.getOrderDate() != null
                ? timestampFormat.format(order.getOrderDate()) : "";
        String formattedDeliveryDate = order.getDeliveryDate() != null
                ? dateFormat.format(order.getDeliveryDate()) : "";

        // Kết hợp dữ liệu từ cả hai phiên bản, đảm bảo tất cả các trường cần thiết đều có
        return order.getId() + "|" + // Có thể bỏ id nếu hash trước khi id được tạo
                order.getUserId() + "|" +
                (order.getUsername() != null ? order.getUsername() : "") + "|" +
                (order.getFullName() != null ? order.getFullName() : "") + "|" +
                (order.getPhone() != null ? order.getPhone() : "") + "|" +
                order.getTotalAmount() + "|" +
                (order.getShippingAddress() != null ? order.getShippingAddress() : "") + "|" +
                (order.getPaymentMethod() != null ? order.getPaymentMethod() : "") + "|" +
                formattedOrderDate + "|" +
                formattedDeliveryDate + "|" +
                (order.getStatus() != null ? order.getStatus() : "");
    }


    // Tạo hash SHA-256 từ chuỗi dữ liệu
    // Giữ phiên bản mới
    public static String hashOrder(Order order) throws NoSuchAlgorithmException {
        String data = getOrderDataString(order);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

        // Chuyển mảng byte hash sang chuỗi hex hoặc base64
        return java.util.Base64.getEncoder().encodeToString(hashBytes);
    }
}