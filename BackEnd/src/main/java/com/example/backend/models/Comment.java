
package com.example.backend.models;

import java.util.Date;

public class Comment {
    private int id;
    private int productId;
    private String username;
    private String text;
    private int rating;
    private Date timestamp;

    // Getters and Setters
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", productId=" + productId +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", timestamp=" + timestamp +
                '}';
    }
}
