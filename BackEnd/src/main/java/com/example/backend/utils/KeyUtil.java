package com.example.backend.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

    // Tạo cặp khóa RSA
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Encode key sang base64
    public static String encodeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // Decode private key từ base64 string
    public static PrivateKey decodePrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    // Decode public key từ base64 string
    public static PublicKey decodePublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    //  Lưu private key vào file PEM (có log và tạo thư mục)
    public static void savePrivateKeyToPemFile(PrivateKey privateKey, String filePath) throws IOException {
        String base64PrivateKey = encodeKey(privateKey);
        String pem = "-----BEGIN PRIVATE KEY-----\n" +
                wrapBase64String(base64PrivateKey) +
                "-----END PRIVATE KEY-----\n";

        try {
            Files.createDirectories(Paths.get(filePath).getParent()); // Tạo thư mục nếu chưa có
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(pem);
                System.out.println("[KeyUtil] Private key đã được lưu vào file: " + filePath);
            }
        } catch (IOException e) {
            System.err.println(" [KeyUtil] Không thể lưu private key vào file: " + filePath);
            e.printStackTrace();
            throw e;
        }
    }

    // Đọc private key từ file PEM
    public static PrivateKey loadPrivateKeyFromPem(String pemFilePath) throws Exception {
        String pem = readPemFile(pemFilePath)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        return decodePrivateKey(pem);
    }

    // Chuyển public key thành PEM string
    public static String convertPublicKeyToPem(PublicKey publicKey) {
        String base64PublicKey = encodeKey(publicKey);
        return "-----BEGIN PUBLIC KEY-----\n" +
                wrapBase64String(base64PublicKey) +
                "-----END PUBLIC KEY-----\n";
    }

    // Đọc file PEM
    public static String readPemFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    // Helper: chia dòng base64 thành 64 ký tự mỗi dòng
    private static String wrapBase64String(String base64) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < base64.length()) {
            int end = Math.min(index + 64, base64.length());
            builder.append(base64, index, end).append("\n");
            index = end;
        }
        return builder.toString();
    }

    // Public key dạng Base64 (dùng lưu DB)
    public static String publicKeyToBase64(PublicKey key) {
        return encodeKey(key);
    }

    //  Đọc lại từ file
    public static PrivateKey loadPrivateKeyFromPemFile(String filePath) throws Exception {
        try {
            String pem = new String(Files.readAllBytes(Paths.get(filePath)));
            String base64Key = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (IOException e) {
            System.err.println(" [KeyUtil] Lỗi đọc file private key: " + filePath);
            e.printStackTrace();
            throw e;
        }
    }
}
