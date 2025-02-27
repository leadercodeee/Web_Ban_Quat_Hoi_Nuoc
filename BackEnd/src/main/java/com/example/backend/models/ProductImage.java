
package com.example.backend.models;
import java.time.LocalDateTime;
public class ProductImage {
    private int id;
    private int productId;
    private String imageUrl;
    private boolean isPrimary; // Xác định hình ảnh chính hay không
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor, Getters, Setters
    public ProductImage() {}

    public ProductImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductImage(int id, int productId, String imageUrl, boolean isPrimary, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.isPrimary = isPrimary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
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
}
