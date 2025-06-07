package com.example.backend.services;

import com.example.backend.models.Invoice;
import com.example.backend.utils.RSASignatureUtil;

import java.security.KeyPair;
import java.util.Date;

public class InvoiceSigningService {

    public static String generateInvoiceData(Invoice invoice) {
        return invoice.getMaDonHang() + "|" +
                invoice.getMaKhachHang() + "|" +
                invoice.getTongTien();
    }

    public void signInvoice(Invoice invoice, KeyPair keyPair) throws Exception {
        String data = generateInvoiceData(invoice);
        String signature = RSASignatureUtil.sign(data, keyPair.getPrivate());
        invoice.setDigitalSignature(signature);
        invoice.setSignedAt(new Date());
    }
}
