package com.example.backend.models;

public class UserKey {
    private int id;
    private int userId;
    private String publicKey;

    public UserKey() {
    }

    public UserKey(int userId, String publicKey) {
        this.userId = userId;
        this.publicKey = publicKey;

    }

    // Getter/setter cho id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter/setter cho userId
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter/setter cho publicKey
    public String getPublicKey() {
        return publicKey;
    }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    // Getter/setter cho privateKey

    @Override
    public String toString() {
        return "UserKey{" +
                "id=" + id +
                ", userId=" + userId +
                ", publicKey='" + publicKey + '\'' +
                 '\'' +
                '}';
    }
}
