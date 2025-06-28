package com.example.backend.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

public class KeyUtil {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static String publicKeyToBase64(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static String privateKeyToBase64(PrivateKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public static PrivateKey loadPrivateKeyFromPem(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String pem = reader.lines()
                .filter(line -> !line.startsWith("-----"))
                .collect(Collectors.joining());

        byte[] decoded = Base64.getDecoder().decode(pem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }
}