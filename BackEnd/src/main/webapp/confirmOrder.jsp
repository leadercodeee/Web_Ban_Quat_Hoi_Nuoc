<%--
  Created by IntelliJ IDEA.
  User: hongh
  Date: 20/06/2025
  Time: 2:42 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.backend.models.Order" %>

<%
  Order order = (Order) request.getAttribute("order");
  Boolean signingSuccess = (Boolean) request.getAttribute("signingSuccess");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Xác nhận thành công</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    .box {
      padding: 25px;
      border-radius: 10px;
      box-shadow: 0 0 10px #ccc;
      background-color: #f9f9f9;
      max-width: 600px;
      margin: auto;
    }
    .success { color: green; font-weight: bold; font-size: 18px; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { padding: 8px; border: 1px solid #ddd; text-align: left; }
    th { background-color: #f2f2f2; }
    .back-link {
      margin-top: 20px;
      display: inline-block;
      background-color: #1976d2;
      color: white;
      padding: 10px 15px;
      border-radius: 5px;
      text-decoration: none;
    }
  </style>
</head>
<body>

<div class="box">
  <% if (signingSuccess != null && signingSuccess) { %>
  <p class="success">✅ Đơn hàng đã được xác nhận và ký điện tử thành công!</p>

  <h3>Thông tin đơn hàng</h3>
  <table>
    <tr><th>Mã đơn hàng</th><td><%= order.getId() %></td></tr>
    <tr><th>ID người dùng</th><td><%= order.getUserId() %></td></tr>
    <tr><th>Tổng tiền</th><td><%= String.format("%,.2f", order.getTotalAmount()) %> VNĐ</td></tr>
    <tr><th>Phương thức thanh toán</th><td><%= order.getPaymentMethod() %></td></tr>
    <tr><th>Trạng thái</th><td><%= order.getStatus() %></td></tr>
  </table>
  <% } else { %>
  <p class="error" style="color: red;">❌ Ký điện tử thất bại!</p>
  <% } %>

  <a href="order-history.jsp" class="back-link">🔙 Quay về lịch sử đơn hàng</a>
</div>

</body>
</html>
