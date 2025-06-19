package com.example.backend.utils;

import com.example.backend.models.Order;

import java.text.SimpleDateFormat;

public class OrderUtil {

    // Format cố định để tránh lỗi khác format giữa client & server
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // Tạo chuỗi dữ liệu đại diện cho hóa đơn để hash (đảm bảo nhất quán)
    public static String toDataString(Order order) {
        String formattedOrderDate = order.getOrderDate() != null
                ? TIMESTAMP_FORMAT.format(order.getOrderDate()) : "";

        String formattedDeliveryDate = order.getDeliveryDate() != null
                ? DATE_FORMAT.format(order.getDeliveryDate()) : "";

        return order.getId() + "|" +
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
}
