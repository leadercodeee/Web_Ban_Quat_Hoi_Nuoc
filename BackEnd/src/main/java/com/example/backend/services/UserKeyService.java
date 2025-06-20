package com.example.backend.services;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.UserKey;
import com.example.backend.utils.DigitalSignatureUtil;

import java.security.KeyPair;

import com.example.backend.utils.RSAKeyUtil;

import java.security.PrivateKey;

public class UserKeyService {
    private UserKeyDAO dao = new UserKeyDAO();

    public void generateAndSaveKey(int userId) throws Exception {
        KeyPair keyPair = DigitalSignatureUtil.generateKeyPair();

        UserKey key = new UserKey();
        key.setUserId(userId);
        key.setPublicKey(DigitalSignatureUtil.encodePublicKey(keyPair.getPublic()));
        key.setPrivateKey(DigitalSignatureUtil.encodePrivateKey(keyPair.getPrivate()));

        dao.saveKey(key);
    }

    public PrivateKey getPrivateKeyByUserId(int userId) throws Exception {
        UserKey key = dao.getKeyByUserId(userId);
        if (key == null || key.getPrivateKey() == null) {
            throw new IllegalArgumentException("Không tìm thấy private key cho user_id = " + userId);
        }
        return RSAKeyUtil.decodePrivateKey(key.getPrivateKey());
    }
}

