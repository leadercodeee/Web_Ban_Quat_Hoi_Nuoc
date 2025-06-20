package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.DAO.UserKeyDAO;
import com.example.backend.models.Order;
import com.example.backend.models.UserKey;
import com.example.backend.services.InvoiceHashService;
import com.example.backend.services.UserKeyService;
import com.example.backend.utils.RSAKeyUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

@WebServlet("/traCuuDonHang")
public class TraCuuDonHangController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final UserKeyDAO userKeyDAO = new UserKeyDAO();
    private final UserKeyService userKeyService = new UserKeyService();
    private final InvoiceHashService hashService = new InvoiceHashService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");

        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            int orderId = Integer.parseInt(orderIdParam);
            Order order = orderDAO.getOrderById(String.valueOf(orderId));

            if (order != null) {
                try {
                    // Chuỗi để hash và ký
                    String orderString = order.toConcatenatedString();

                    // Tạo hash
                    String hash = hashService.generateOrderHash(order);

                    // Lấy private key
                    PrivateKey privateKey = userKeyService.getPrivateKeyByUserId(order.getUserId());

                    // Lấy public key từ DB
                    UserKey userKey = userKeyDAO.getKeyByUserId(order.getUserId());
                    PublicKey publicKey = RSAKeyUtil.decodePublicKey(userKey.getPublicKey());

                    // Ký chuỗi
                    Signature signer = Signature.getInstance("SHA256withRSA");
                    signer.initSign(privateKey);
                    signer.update(orderString.getBytes(StandardCharsets.UTF_8));
                    byte[] signedBytes = signer.sign();
                    String signature = Base64.getEncoder().encodeToString(signedBytes);

                    // Gửi sang JSP
                    req.setAttribute("order", order);
                    req.setAttribute("orderString", orderString);
                    req.setAttribute("hash", hash);
                    req.setAttribute("signature", signature);
                    req.setAttribute("privateKey", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
                    req.setAttribute("publicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
                } catch (Exception e) {
                    req.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
                }
            }
        }

        req.getRequestDispatcher("traCuuDonHang.jsp").forward(req, resp);
    }
}
