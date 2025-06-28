package com.example.backend.services;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DAO.OrderItemDAO;
import com.example.backend.models.Order;
import com.example.backend.models.OrderItem;
import com.example.backend.utils.HashUtil;

import java.util.Comparator;
import java.util.List;

public class InvoiceHashService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    public String generateHashString(Order order, List<OrderItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("ORDER_ID:").append(order.getId()).append("|");
        sb.append("USER_ID:").append(order.getUserId()).append("|");
        sb.append("ORDER_DATE:").append(order.getOrderDate()).append("|");
        sb.append("TOTAL_AMOUNT:").append(order.getTotalAmount()).append("|");
        sb.append("SHIPPING_ADDRESS:").append(order.getShippingAddress()).append("|");
        sb.append("PAYMENT_METHOD:").append(order.getPaymentMethod()).append("|");

        sb.append("PRODUCTS:[");
        items.sort(Comparator.comparing(OrderItem::getProductId));
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            sb.append("{ID:").append(item.getProductId())
                    .append(",QTY:").append(item.getQuantity())
                    .append(",PRICE:").append(item.getPrice()).append("}");
            if (i < items.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public String generateOrderHash(Order order) {
        String rawData = order.toConcatenatedString();
        return HashUtil.sha256(rawData);
    }

    public void updateAllOrdersWithoutHash() {
        List<Order> orders = orderDAO.getOrdersWithoutHash();
        for (Order order : orders) {
            String hash = generateOrderHash(order);
            orderDAO.updateHash(order.getId(), hash);
        }
    }
}
