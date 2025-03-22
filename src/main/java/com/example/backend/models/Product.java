
package com.example.backend.models;

import java.time.LocalDateTime;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private int categoryId;
    private double price;
    private String brand;
    private int stock; // Số lượng sản phẩm
    private String description;
    private List<ProductImage> images; // Thêm danh sách hình ảnh sản phẩm
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor không tham số (nếu cần)
    public Product() {
    }

    // Constructor đầy đủ
    public Product(int id, String name, int categoryId, double price, String brand, int stock, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.brand = brand;
        this.stock = stock;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor sử dụng khi thêm sản phẩm (không cần ID, createdAt, updatedAt)
    public Product(String name, int categoryId, double price, String brand, int stock, String description) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.brand = brand;
        this.stock = stock;
        this.description = description;
    }

    public Product(int id, String name, int categoryId, double price, String brand, int stock, String description, List<ProductImage> images, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.brand = brand;
        this.stock = stock;
        this.description = description;
        this.images = images;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
