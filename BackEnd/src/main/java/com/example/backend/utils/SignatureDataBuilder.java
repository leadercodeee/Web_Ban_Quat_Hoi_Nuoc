package com.example.backend.utils;

import com.example.backend.models.Order;

public class SignatureDataBuilder {
    public static String forOrder(Order order) {
        return order.getUserId() + "|" +
                order.getTotalAmount() + "|" +
                order.getShippingAddress() + "|" +
                order.getPaymentMethod() + "|" +
                order.getOrderDate() + "|" +
                order.getDeliveryDate();
    }
}
