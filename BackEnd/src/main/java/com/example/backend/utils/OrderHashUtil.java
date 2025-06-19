package com.example.backend.utils;

import com.example.backend.models.Order;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class OrderHashUtil {

    /**
     * Băm chuỗi input bằng SHA-256
     */
    public static String sha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(encodedHash);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing", e);
        }
    }

    /**
     * Chuyển byte array sang hex string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * Lấy hash của hóa đơn Order
     */
    public static String getOrderHash(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order không được null");
        }
        String orderString = order.toConcatenatedString();
        return sha256Hash(orderString);
    }
}
