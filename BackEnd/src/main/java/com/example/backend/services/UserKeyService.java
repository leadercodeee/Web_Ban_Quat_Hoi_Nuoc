package com.example.backend.services;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.UserKey;
import com.example.backend.utils.DigitalSignatureUtil;

import java.security.KeyPair;

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
}
