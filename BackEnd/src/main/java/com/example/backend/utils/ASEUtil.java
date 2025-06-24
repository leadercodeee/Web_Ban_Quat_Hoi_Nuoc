package com.example.backend.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class ASEUtil {
    // Genkey
    public static String generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);  // Key size
        SecretKey secretKey = keyGen.generateKey();

        // Convert to Base 64
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public static SecretKey getAESKeyFromString(String keyStr) {
        byte[] decodedKey = Base64.getDecoder().decode(keyStr);
        return new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }


}
