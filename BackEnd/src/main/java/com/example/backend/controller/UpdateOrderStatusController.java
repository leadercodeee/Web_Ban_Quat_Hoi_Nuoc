
package com.example.backend.controller;

import com.example.backend.DB.DBConnect;
import com.example.backend.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@WebServlet("/admin/updateOrderStatus")
public class UpdateOrderStatusController extends HttpServlet {
    private OrderService orderService = new OrderService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");

        // Cập nhật trạng thái đơn hàng trong cơ sở dữ liệu
        try {
            Connection conn = DBConnect.getInstance().getConnection();
            String sql = "UPDATE orders SET status = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                // Gửi phản hồi thành công
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Cập nhật trạng thái đơn hàng thành công!");
            } else {
                // Nếu không có đơn hàng nào được cập nhật
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Đơn hàng không tồn tại");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi khi cập nhật trạng thái đơn hàng");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
