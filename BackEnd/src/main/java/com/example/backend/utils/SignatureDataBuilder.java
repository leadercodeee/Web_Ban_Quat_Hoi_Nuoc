package com.example.backend.utils;

import com.example.backend.models.Order;
import com.example.backend.models.OrderItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SignatureDataBuilder {
    public static String forOrder(Order o) {
        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

        StringBuilder sb = new StringBuilder()
                .append(o.getId()).append('|')
                .append(o.getUserId()).append('|')
                .append(Objects.toString(o.getPhone(),"")).append('|')
                .append(df.format(o.getTotalAmount())).append('|')
                .append(Objects.toString(o.getShippingAddress(),"")).append('|')
                .append(Objects.toString(o.getPaymentMethod(),"")).append('|')
                .append(Objects.toString(o.getOrderDate(), "")).append('|')
                .append(Objects.toString(o.getDeliveryDate(),"")).append('|')
                .append(Objects.toString(o.getStatus(),""));

        List<OrderItem> items = new ArrayList<>(o.getItems());
        items.sort(Comparator.comparing(OrderItem::getProductId));
        for (OrderItem it : items) {
            sb.append('|')
                    .append(it.getProductId()).append('|')
                    .append(it.getQuantity()).append('|')
                    .append(df.format(it.getPrice()));
        }
        return sb.toString();
    }

}
