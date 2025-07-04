package com.example.backend.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;
    private Timestamp orderDate;
    private Date deliveryDate;
    private String status;

    private String signature;
    private long publicKeyId;

    private String signature; // chữ ký số (Base64)
    private String hash;      // hash value của order
    // private String orderData; // Xóa
    // private String orderHash; // Xóa


    // Các trường bổ sung (nếu có)
    private String username;
    private String fullName;
    private String phone;


    public Order(int id, int userId, double totalAmount, String shippingAddress, String paymentMethod, Timestamp orderDate, Date
            deliveryDate, String status,String signature, long publicKeyId) {
    // Giữ lại phần mới
    private List<OrderItem> items;


    public Order() {}

    public Order(int id, int userId, String phone, double totalAmount, String shippingAddress, String paymentMethod,
                 Timestamp orderDate, Date deliveryDate, String status) {

        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.signature = signature;
        this.publicKeyId = publicKeyId;
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

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // Xóa các getter/setter của orderData và orderHash vì đã loại bỏ trường này
    // public String getOrderData() { return orderData; }
    // public void setOrderData(String orderData) { this.orderData = orderData; }
    // public String getOrderHash() { return orderHash; }
    // public void setOrderHash(String orderHash) { this.orderHash = orderHash; }

    // Giữ lại getter/setter cho items
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Chuỗi định danh dùng để tính hash và ký số
     */
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



    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getPublicKeyId() {
        return publicKeyId;
    }

    public void setPublicKeyId(long publicKeyId) {
        this.publicKeyId = publicKeyId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", status='" + status + '\'' +
                ", signature='" + signature + '\'' +
                ", publicKeyId=" + publicKeyId +
                '}';
    }
}

