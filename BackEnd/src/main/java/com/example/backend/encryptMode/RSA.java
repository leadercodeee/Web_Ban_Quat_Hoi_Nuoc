package com.example.backend.encryptMode;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private SecretKey secretKey;

    public RSA() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

        // Tạo khóa AES với chiều dài 256 bit
        KeyGenerator keyGenAES = KeyGenerator.getInstance("AES");
        keyGenAES.init(256); // Hoặc 128 hoặc 192
        secretKey = keyGenAES.generateKey();
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPublicKey(byte[] publicKeyBytes) throws Exception {
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        this.publicKey = kf.generatePublic(X509publicKey);
    }

    public void setPrivateKey(byte[] privateKeyBytes) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }

    public String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // Đảm bảo dùng đúng padding
        cipher.init(Cipher.DECRYPT_MODE, privateKey); // Sử dụng Private Key
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        try {
            RSA rsa = new RSA();
            String message = "a";
//            String encryptedMessage = rsa.encrypt(message);
//            System.out.println("Encrypted message: " + encryptedMessage);
//            System.out.println("length: " + encryptedMessage.length());
            String decryptedMessage = rsa.decrypt("SFE4ak5wNDMxb3JNNkFNZE9KRFNvLzQ5YlZxQ2RtcnEycDdJMTZiVFNTd0FTZWZiMGdmUXkwTWs1WlhydG4zWlU3K09xOEptOXdjOG55OVQ0NG1hZkRTQWQxMm5UcTVxbmJqRng1UndDSEJGTVR0V3hJTS9weVFXTXFFbjVON2U3bWlidmN1emJHSWpBVnUzY1Q3blJ1aGVkTWZxNWZ4RHRQc2pPTEw1L3Q0WlV4MWM0bXZ5WlRKcGlvcmlDeFZxU1dCWVd4bnRzbXNkdE1LdlhhNjE0QWdWUFNYVlFDdU16N1NrM0g4amdjd2pSSFM3MUZva1lIdFl4SFJWa0hkQXlzVFNOTzU4V29JOGZjallpM1o4SkhBRHg1N2p3NDRJS0tid0ZSVUhPYmJKMURQdTFuVHQ2RVRXWVZQdG92M3hKNjlpRmtYTzFKR2V1L2RkSG9SZExnPT0=");
            System.out.println("Decrypted message: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}