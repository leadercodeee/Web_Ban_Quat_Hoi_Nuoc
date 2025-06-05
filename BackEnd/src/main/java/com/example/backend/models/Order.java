package com.example.backend.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Order {
    private int id;
    private int userId;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;
    private Timestamp orderDate;
    private Date deliveryDate;
    private String status;
    private String signature; // chữ ký số (Base64)

    // NEW FIELDS
    private String username;
    private String fullName;
    private String phone;

    public Order() {}

    public Order(int id, int userId, double totalAmount, String shippingAddress, String paymentMethod,
                 Timestamp orderDate, Date deliveryDate, String status) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    // --- Getters & Setters ---

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public double getTotalAmount() { return totalAmount; }

    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getShippingAddress() { return shippingAddress; }

    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMethod() { return paymentMethod; }

    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Timestamp getOrderDate() { return orderDate; }

    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public Date getDeliveryDate() { return deliveryDate; }

    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getSignature() { return signature; }

    public void setSignature(String signature) { this.signature = signature; }

    // NEW GETTERS & SETTERS
    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    // --- Chuỗi định danh dùng để ký số ---
    public String toConcatenatedString() {
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedOrderDate = orderDate != null ? timestampFormat.format(orderDate) : "";
        String formattedDeliveryDate = deliveryDate != null ? dateFormat.format(deliveryDate) : "";

        return id + "|" +
                userId + "|" +
                (username != null ? username : "") + "|" +
                (fullName != null ? fullName : "") + "|" +
                (phone != null ? phone : "") + "|" +
                totalAmount + "|" +
                (shippingAddress != null ? shippingAddress : "") + "|" +
                (paymentMethod != null ? paymentMethod : "") + "|" +
                formattedOrderDate + "|" +
                formattedDeliveryDate + "|" +
                (status != null ? status : "");
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", totalAmount=" + totalAmount +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", status='" + status + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
