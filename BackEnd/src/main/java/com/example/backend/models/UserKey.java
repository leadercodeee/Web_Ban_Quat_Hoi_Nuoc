package com.example.backend.models;

public class UserKey {
    private int id;
    private int userId;
    private String publicKey;
    private String privateKey;

    public UserKey() {
    }

    public UserKey(int userId, String publicKey, String privateKey) {
        this.userId = userId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
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
    public String getPrivateKey() {
        return privateKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "id=" + id +
                ", userId=" + userId +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
