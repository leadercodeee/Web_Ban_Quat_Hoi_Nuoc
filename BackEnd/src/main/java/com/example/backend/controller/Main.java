package com.example.backend.controller;

import com.example.backend.services.InvoiceHashService;

public class Main {
    public static void main(String[] args) {
        InvoiceHashService hashService = new InvoiceHashService();
        hashService.updateAllOrdersWithoutHash(); // Gọi hàm cập nhật hash cho đơn hàng chưa có
        System.out.println("Đã cập nhật hash cho các đơn hàng chưa có.");
    }
}
