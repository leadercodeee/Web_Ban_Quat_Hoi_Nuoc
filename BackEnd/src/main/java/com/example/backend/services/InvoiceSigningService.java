package com.example.backend.services;

import com.example.backend.models.Invoice;
import com.example.backend.utils.RSASignatureUtil;

import java.security.KeyPair;
import java.util.Date;

public class InvoiceSigningService {

    // Lấy dữ liệu từ hoá đơn để tạo chuỗi ký
    public static String generateInvoiceData(Invoice inv) {
        return String.join("|",
                inv.getOrderId(),
                inv.getCustomerId(),
                String.valueOf(inv.getTotalAmount()),
                java.util.Objects.toString(inv.getShippingAddress(), ""),
                java.util.Objects.toString(inv.getPaymentMethod(), ""),
                java.util.Objects.toString(inv.getOrderDate(), ""),
                java.util.Objects.toString(inv.getDeliveryDate(), ""),
                java.util.Objects.toString(inv.getStatus(), "")
        );
    }


    public void signInvoice(Invoice invoice, KeyPair keyPair) throws Exception {
        String data = generateInvoiceData(invoice);
        String signature = RSASignatureUtil.sign(data, keyPair.getPrivate());
        invoice.setDigitalSignature(signature);
        invoice.setSignedAt(new Date());
    }
}
