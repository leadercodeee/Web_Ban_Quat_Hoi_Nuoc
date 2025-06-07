package com.example.backend.utils;

import com.example.backend.models.Order;
import com.example.backend.models.OrderItem;

public class SignatureDataBuilder {
    public static String forOrder(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append(order.getOrderId()).append("|")
                .append(order.getUserId()).append("|")
                .append(order.getPhone()).append("|")
                .append(order.getTotalAmount()).append("|")
                .append(order.getShippingAddress()).append("|")
                .append(order.getPaymentMethod()).append("|")
                .append(order.getOrderDate()).append("|")
                .append(order.getDeliveryDate()).append("|")
                .append(order.getStatus());

        for (OrderItem item : order.getItems()) {
            sb.append("|")
                    .append(item.getProductName()).append("|")
                    .append(item.getQuantity()).append("|")
                    .append(item.getUnitPrice()).append("|")
                    .append(item.getTotalPrice());
        }

        return sb.toString();
    }
}
