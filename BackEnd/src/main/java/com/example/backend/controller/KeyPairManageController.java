package com.example.backend.controller;

import com.example.backend.DAO.PublicKeyDAO;
import com.example.backend.constant.SystemConstant;
import com.example.backend.models.PublicKey;
import com.example.backend.models.User;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/key_manager")
public class KeyPairManageController extends HttpServlet {
    int userId;
    PublicKeyDAO publicKeyDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (String) req.getParameter("action");
        if (action == null) {
            req.setAttribute("userId", userId = ((User) req.getSession().getAttribute(SystemConstant.AUTH)).getId());
            req.getRequestDispatcher("keypair-manager.jsp").forward(req, resp);
        } else {
            try {
                switch (action) {
                    case "create-keypair":
                        createKeyPair(req, resp);
                        break;
                    case "get-public-key":
                        getPublicKeys(req, resp);
                        break;
                    case "get-public-key-valid":
                        getPublicKeysValid(req, resp);
                        break;
                    case "update-public-key":
                        update(req, resp);
                        break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void getPublicKeysValid(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        publicKeyDAO = new PublicKeyDAO();
        List<PublicKey> entityList = publicKeyDAO.findValidPublicKeysByUserId(userId);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("publicKeys", entityList);

        // Chuyển Map thành JSON và gửi lại cho client
        String jsonResponse = new Gson().toJson(responseData);  // Sử dụng gson đã cấu hình

        // Cấu hình kiểu trả về là JSON
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write(jsonResponse);
        out.close();
    }

    private void getPublicKeys(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        publicKeyDAO = new PublicKeyDAO();
        List<PublicKey> entityList = publicKeyDAO.findByUserIdSorted(userId);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("publicKeys", entityList);
        writeJson(resp, responseData);
    }


    private void createKeyPair(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, NoSuchAlgorithmException, NoSuchAlgorithmException {
        userId = ((User) req.getSession().getAttribute(SystemConstant.AUTH)).getId();
        String name = req.getParameter("name");

        // 1. Generate RSA key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        String publicKeyBase64 = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String privateKeyBase64 = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

        // 2. Persist only the public key
        PublicKey entity = new PublicKey();
        entity.setPublicKey(publicKeyBase64);
        entity.setCrTimestamp(new Timestamp(System.currentTimeMillis()));
        entity.setStatus((byte) 1); // 1 = valid
        entity.setUser_id(userId);
        entity.setName(name != null ? name : "key-" + System.currentTimeMillis());

        publicKeyDAO = new PublicKeyDAO();
        long id = publicKeyDAO.save(entity);

        // 3. Build response
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("id", id);
        responseData.put("publicKey", publicKeyBase64);
        responseData.put("privateKey", privateKeyBase64); // gửi lại cho client để họ lưu trữ, server KHÔNG lưu private key

        writeJson(resp, responseData);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        publicKeyDAO = new PublicKeyDAO();

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            sendError(resp, "Invalid id");
            return;
        }

        PublicKey pk = publicKeyDAO.findById(id);
        if (pk == null || pk.getUser_id() != userId) {
            sendError(resp, "Public key not found or access denied");
            return;
        }

        // Parse optional params
        String name = req.getParameter("name");
        String statusStr = req.getParameter("status");
        String exTsStr = req.getParameter("exTimestamp");

        if (name != null) pk.setName(name);
        if (statusStr != null) pk.setStatus(Byte.parseByte(statusStr));
        if (exTsStr != null && !exTsStr.isEmpty()) pk.setExTimestamp(Timestamp.valueOf(exTsStr));

        boolean ok = publicKeyDAO.update(pk);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", ok ? "success" : "failed");
        writeJson(resp, responseData);
    }
    private void sendError(HttpServletResponse resp, String message) throws IOException {
        Map<String, Object> error = new HashMap<>();
        error.put("status", "error");
        error.put("message", message);
        writeJson(resp, error);
    }
    private void writeJson(HttpServletResponse resp, Map<String, Object> data) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(new Gson().toJson(data));

        }
    }
}
