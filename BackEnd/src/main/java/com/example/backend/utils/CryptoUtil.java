package com.example.backend.utils;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
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
        pw.println("-----BEGIN PUBLIC KEY-----");
        String key = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        for(int i = 0; i < key.length(); i += 64){
            int end = Math.min(i + 64, key.length());
            pw.println(key.substring(i, end));
        }
        pw.println("-----END PRIVATE KEY-----");
        pw.close();
    }
    public void exportPrivateKey(String filename) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
        pw.println("-----BEGIN PRIVATE KEY-----");
        String key = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        for(int i = 0; i < key.length(); i += 64){
            int end = Math.min(i + 64, key.length());
            pw.println(key.substring(i, end));
        }
        pw.println("-----END PRIVATE KEY-----");
        pw.close();

    }
    public void importPublicKey (String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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
    public void importPrivateKey (String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;
        String key="";

        while((line = br.readLine())!= null){
            if(line.contains("PRIVATE kEY")) continue;
            key += line;
        }

        byte [] encoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }
    public String sign(String data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(this.privateKey);
        s.update(data.getBytes());
        byte[] sign = s.sign();
        return Base64.getEncoder().encodeToString(sign);
    }
    public String signFile(String src) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(this.privateKey);

        BufferedInputStream bis= new BufferedInputStream(new FileInputStream(src));
        int i;
        byte[] read= new byte[1024];
        while ((i=bis.read(read))!=-1) {
            s.update(read,0,i);
        }
        byte[] sign = s.sign();

        return Base64.getEncoder().encodeToString(sign);
    }
    public boolean verify(String data, String sign) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initVerify(publicKey);
        s.update(data.getBytes());
        return  s.verify(Base64.getDecoder().decode(sign));
    }
    public boolean verifyFile(String src, String sign) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, IOException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initVerify(publicKey);
        BufferedInputStream bis= new BufferedInputStream(new FileInputStream(src));
        int i;
        byte[] read= new byte[1024];
        while ((i=bis.read(read))!=-1) {
            s.update(read,0,i);
        }
        return  s.verify(Base64.getDecoder().decode(sign));
    }

}
