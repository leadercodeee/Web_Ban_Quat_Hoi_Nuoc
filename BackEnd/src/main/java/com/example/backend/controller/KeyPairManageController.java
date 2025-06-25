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
import java.sql.SQLException;
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


    private void getPublicKeys(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void createKeyPair(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
}
