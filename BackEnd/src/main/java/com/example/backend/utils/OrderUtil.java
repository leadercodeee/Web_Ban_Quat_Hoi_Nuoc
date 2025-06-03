package com.example.backend.utils;

import com.example.backend.models.Order;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OrderUtil {

    // Tạo chuỗi dữ liệu đại diện cho hóa đơn để hash
    public static String getOrderDataString(Order order) {
        // Tùy chỉnh theo các trường quan trọng của hóa đơn
        return order.getUserId() + "|" +
                order.getTotalAmount() + "|" +
                order.getShippingAddress() + "|" +
                order.getPaymentMethod() + "|" +
                order.getOrderDate().toString() + "|" +
                order.getDeliveryDate().toString() + "|" +
                order.getStatus();
    }

    // Tạo hash SHA-256 từ chuỗi dữ liệu
    public static String hashOrder(Order order) throws NoSuchAlgorithmException {
        String data = getOrderDataString(order);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

        // Chuyển mảng byte hash sang chuỗi hex hoặc base64
        // Ở đây dùng base64 cho dễ lưu
        return java.util.Base64.getEncoder().encodeToString(hashBytes);
    }
}
