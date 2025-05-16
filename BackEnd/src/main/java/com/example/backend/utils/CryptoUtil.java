package com.example.backend.utils;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoUtil {
    PublicKey publicKey;
    PrivateKey privateKey;

    public void genKey() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024);
        KeyPair keyPair = gen.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }
    public void exportPublicKey(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        pw.println("-----BEGIN PRIVATE KEY-----");
        String key = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        for(int i = 0; i < key.length(); i += 64){
            int end = Math.min(i + 64, key.length());
            pw.println(key.substring(i, end));
        }
        pw.println("-----END PRIVATE KEY-----");
        pw.close();
    }
    public void importPublic (String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;
        String key="";

        while((line = br.readLine())!= null){
            if(line.contains("PUBLIC kEY")) continue;
            key += line;
        }
        byte [] encoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.publicKey = keyFactory.generatePublic(keySpec);
    }
}
