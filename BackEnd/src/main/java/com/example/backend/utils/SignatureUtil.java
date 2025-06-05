package com.example.backend.utils;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class SignatureUtil {

    public static String signData(String data, PrivateKey privateKey) throws Exception {
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(privateKey);
        signer.update(data.getBytes());
        byte[] signatureBytes = signer.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }
}
