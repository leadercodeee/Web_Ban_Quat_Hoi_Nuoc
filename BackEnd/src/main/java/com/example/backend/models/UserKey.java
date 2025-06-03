package com.example.backend.models;


import java.sql.Timestamp;

public class UserKey {
    private int id;
    private int userId;
    private String publicKey;
    private String privateKey;
    private Timestamp createdAt;
    private Timestamp revokedAt;
    private String status;

    // Getter và Setter cho id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho userId
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter và Setter cho publicKey
    public String getPublicKey() {
        return publicKey;
    }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    // Getter và Setter cho privateKey
    public String getPrivateKey() {
        return privateKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    // Getter và Setter cho createdAt
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Getter và Setter cho revokedAt
    public Timestamp getRevokedAt() {
        return revokedAt;
    }
    public void setRevokedAt(Timestamp revokedAt) {
        this.revokedAt = revokedAt;
    }

    // Getter và Setter cho status
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
