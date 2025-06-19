package com.example.backend.controller;

import com.example.backend.DAO.OrderDAO;
import com.example.backend.models.Order;
import com.example.backend.utils.OrderHashUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/order-hash-generate")
public class OrderHashGenerateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            request.setAttribute("error", "orderId không được để trống");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "orderId phải là số");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(orderIdStr);
        if (order == null) {
            request.setAttribute("error", "Không tìm thấy đơn hàng");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Tạo chuỗi để hash
        String dataToHash = order.toConcatenatedString();

        // Tạo hash bằng SHA-256
        String hashValue = OrderHashUtil.sha256Hash(dataToHash);
        order.setHash(hashValue);

        // Cập nhật hash vào DB
        boolean updated = orderDAO.updateOrderSignatureAndHash(order); // bạn có thể bỏ signature nếu chưa có

        if (!updated) {
            request.setAttribute("error", "Cập nhật hash vào đơn hàng thất bại");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("order", order);
        request.setAttribute("orderHash", hashValue);
        request.getRequestDispatcher("orderHashView.jsp").forward(request, response);
    }
}
