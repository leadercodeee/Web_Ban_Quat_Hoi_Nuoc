package com.example.backend.utils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class DigitalSignatureUtil {

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * Sinh cặp khóa RSA 2048 bit
     * @return KeyPair
     * @throws NoSuchAlgorithmException nếu không tìm thấy thuật toán RSA
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    /**
     * Ký dữ liệu đầu vào với private key, trả về chữ ký dạng Base64
     * @param data dữ liệu cần ký
     * @param privateKey private key dùng ký
     * @return chuỗi chữ ký base64
     * @throws NoSuchAlgorithmException nếu không tìm thấy thuật toán
     * @throws InvalidKeyException nếu key không hợp lệ
     * @throws SignatureException lỗi ký
     */
    public static String sign(String data, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signedBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signedBytes);
    }

    /**
     * Xác minh chữ ký từ dữ liệu, chữ ký (Base64) và public key
     * @param data dữ liệu gốc
     * @param signatureStr chữ ký Base64
     * @param publicKey public key dùng xác minh
     * @return true nếu chữ ký hợp lệ, false nếu không
     * @throws NoSuchAlgorithmException nếu không tìm thấy thuật toán
     * @throws InvalidKeyException nếu key không hợp lệ
     * @throws SignatureException lỗi xác minh
     */
    public static boolean verify(String data, String signatureStr, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
        return signature.verify(signatureBytes);
    }

    /**
     * Mã hóa PublicKey thành chuỗi Base64 để lưu trong database
     */
    public static String encodePublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * Mã hóa PrivateKey thành chuỗi Base64 để lưu trong database
     */
    public static String encodePrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * Chuyển chuỗi Base64 thành đối tượng PublicKey
     */
    public static PublicKey decodePublicKey(String base64Key) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        return kf.generatePublic(spec);
    }

    /**
     * Chuyển chuỗi Base64 thành đối tượng PrivateKey
     */
    public static PrivateKey decodePrivateKey(String base64Key) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        return kf.generatePrivate(spec);
    }
}
