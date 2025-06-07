package com.example.backend.utils;

import com.example.backend.models.Order;
import com.example.backend.models.OrderItem;

public class SignatureDataBuilder {
    public static String forOrder(Order order) {
        StringBuilder sb = new StringBuilder();

        // Noi dung cua hoa don
        sb.append(order.getId()).append("|")
                .append(order.getUserId()).append("|")
                .append(order.getPhone()).append("|")
                .append(order.getTotalAmount()).append("|")
                .append(order.getShippingAddress()).append("|")
                .append(order.getPaymentMethod()).append("|")
                .append(order.getOrderDate()).append("|")
                .append(order.getDeliveryDate()).append("|")
                .append(order.getStatus());

        // Danh sach cac mat hang trong hoa don
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                sb.append("|")
                        .append(item.getProductId()).append("|")
                        .append(item.getQuantity()).append("|")
                        .append(item.getPrice()); // Don gia moi san pham
            }
        }

        return sb.toString();
    }
}
