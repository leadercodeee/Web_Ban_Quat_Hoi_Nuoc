package com.example.backend.models;

import java.time.LocalDate;
import java.util.Date;

public class Invoice {
    private String orderId;
    private String customerId;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String status;
    private String signature;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setDigitalSignature(String signature) {
        this.signature = signature;
    }

    public String getMaDonHang() {
        return orderId;
    }

    public String getMaKhachHang() {
        return customerId;
    }

    public void setSignedAt(Date date) {
        this.signature = date.toString();
    }

    public String getTongTien() {
        return String.valueOf(totalAmount);
    }
}
