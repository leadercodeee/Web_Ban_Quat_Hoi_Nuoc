package com.example.backend.services;

import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.UserKey;
import com.example.backend.utils.KeyUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;

public class UserKeyService {

    private static final String KEY_DIR;

    static {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            KEY_DIR = "keys/";  // Đường dẫn tương đối cho Windows
        } else {
            KEY_DIR = "/opt/keys/";  // Đường dẫn tuyệt đối cho Linux/macOS
        }
    }

    private final UserKeyDAO dao = new UserKeyDAO();

    public void generateAndSaveKey(int userId) throws Exception {
        KeyPair keyPair = KeyUtil.generateKeyPair();

        // Tạo thư mục nếu chưa có
        Files.createDirectories(Paths.get(KEY_DIR));

        // Lưu private key vào file PEM
        String pemPath = KEY_DIR + "user_" + userId + "_private.pem";
        System.out.println("🔧 [DEBUG] Lưu private key tại: " + pemPath);
        KeyUtil.savePrivateKeyToPemFile(keyPair.getPrivate(), pemPath);

        // Lưu public key vào DB
        String publicKeyPem = KeyUtil.convertPublicKeyToPem(keyPair.getPublic());
        UserKey userKey = new UserKey(userId, publicKeyPem);
        dao.saveKey(userKey);
    }

    public PrivateKey getPrivateKeyByUserId(int userId) throws Exception {
        String pemPath = KEY_DIR + "user_" + userId + "_private.pem";
        return KeyUtil.loadPrivateKeyFromPemFile(pemPath);
    }
}
