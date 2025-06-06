package com.example.backend.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

<<<<<<< HEAD
    // Load PrivateKey từ file PEM
=======
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filePath)))
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

<<<<<<< HEAD
    // Load PublicKey từ file PEM
=======
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    public static PublicKey loadPublicKey(String filePath) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filePath)))
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

<<<<<<< HEAD
    // Tạo cặp khóa RSA mới với kích thước 2048 bit
=======
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
<<<<<<< HEAD

    // Chuyển PrivateKey sang Base64 String (PKCS#8)
=======
    // Chuyển PrivateKey thành Base64 String
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    public static String privateKeyToBase64(PrivateKey privateKey) {
        byte[] keyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

<<<<<<< HEAD
    // Chuyển PublicKey sang Base64 String (X.509)
=======
    // Chuyển PublicKey thành Base64 String
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    public static String publicKeyToBase64(PublicKey publicKey) {
        byte[] keyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

<<<<<<< HEAD
    // Chuyển Base64 String thành PrivateKey
=======
    // Ngược lại: chuyển Base64 String thành PrivateKey
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    public static PrivateKey base64ToPrivateKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

<<<<<<< HEAD
    // Chuyển Base64 String thành PublicKey
=======
    // Ngược lại: chuyển Base64 String thành PublicKey
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    public static PublicKey base64ToPublicKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
<<<<<<< HEAD

    // Chuyển PrivateKey thành PEM format string (dùng để xuất file .pem)
    public static String privateKeyToPEM(PrivateKey privateKey) {
        String base64 = privateKeyToBase64(privateKey);
        return formatKeyToPEM(base64, "PRIVATE KEY");
    }

    // Chuyển PublicKey thành PEM format string (dùng để xuất file .pem)
    public static String publicKeyToPEM(PublicKey publicKey) {
        String base64 = publicKeyToBase64(publicKey);
        return formatKeyToPEM(base64, "PUBLIC KEY");
    }

    // Helper format Base64 thành PEM chuẩn, thêm header/footer và xuống dòng mỗi 64 ký tự
    private static String formatKeyToPEM(String base64Key, String keyType) {
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append(keyType).append("-----\n");
        int index = 0;
        while (index < base64Key.length()) {
            pem.append(base64Key, index, Math.min(index + 64, base64Key.length()));
            pem.append("\n");
            index += 64;
        }
        pem.append("-----END ").append(keyType).append("-----\n");
        return pem.toString();
    }
=======
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
}
