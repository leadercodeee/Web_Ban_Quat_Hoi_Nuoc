package com.example.backend.services;

import com.example.backend.models.Invoice;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import com.example.backend.utils.RSASignatureUtil;

import java.security.KeyPair;
import java.util.Date;

public class InvoiceSigningService {
    public void signInvoice(Invoice invoice, KeyPair keyPair) throws Exception {
        String data = invoice.getMaDonHang() + invoice.getMaKhachHang() + invoice.getTongTien();
        String signature = RSASignatureUtil.sign(data, keyPair.getPrivate());
        invoice.setDigitalSignature(signature);
        invoice.setSignedAt(new Date());
    }
}
