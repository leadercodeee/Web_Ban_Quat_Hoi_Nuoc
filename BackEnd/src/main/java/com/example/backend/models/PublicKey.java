package com.example.backend.models;

import java.sql.Timestamp;

public class PublicKey {
    private long id;
    private String publicKey;
    private Timestamp exTimestamp;
    private byte status;
    private Timestamp crTimestamp;
    private int user_id;
    String name;

    public PublicKey(long id, String publicKey, Timestamp exTimestamp, byte status, Timestamp crTimestamp, int user_id, String name) {
        this.id = id;
        this.publicKey = publicKey;
        this.exTimestamp = exTimestamp;
        this.status = status;
        this.crTimestamp = crTimestamp;
        this.user_id = user_id;
        this.name = name;
    }

    public PublicKey() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Timestamp getExTimestamp() {
        return exTimestamp;
    }

    public void setExTimestamp(Timestamp exTimestamp) {
        this.exTimestamp = exTimestamp;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Timestamp getCrTimestamp() {
        return crTimestamp;
    }

    public void setCrTimestamp(Timestamp crTimestamp) {
        this.crTimestamp = crTimestamp;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
