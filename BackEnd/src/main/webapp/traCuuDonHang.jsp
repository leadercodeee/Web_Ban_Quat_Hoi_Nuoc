<%--
  Created by IntelliJ IDEA.
  User: hongh
  Date: 20/06/2025
  Time: 4:35 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.backend.models.Order" %>
<%@ page import="java.util.Base64" %>
<html>
<head>
  <title>Tra cứu đơn hàng</title>
</head>
<body>
<h2>Tra cứu đơn hàng theo ID</h2>

<form action="traCuuDonHang" method="get">
  <label for="orderId">Nhập ID đơn hàng:</label>
  <input type="number" name="orderId" required />
  <button type="submit">Tra cứu</button>
</form>

<%
  Order order = (Order) request.getAttribute("order");
  String orderString = (String) request.getAttribute("orderString");
  String hash = (String) request.getAttribute("hash");
  String signature = (String) request.getAttribute("signature");
  String privateKeyBase64 = (String) request.getAttribute("privateKey");
  String publicKeyBase64 = (String) request.getAttribute("publicKey");

  if (order != null) {
%>
<hr>
<h3>Thông tin đơn hàng:</h3>
<p><strong>Mã đơn hàng:</strong> <%= order.getId() %></p>
<p><strong>Khách hàng:</strong> <%= order.getFullName() %></p>
<p><strong>Phone:</strong> <%= order.getPhone() %></p>
<p><strong>Địa chỉ:</strong> <%= order.getShippingAddress() %></p>
<p><strong>Phương thức thanh toán:</strong> <%= order.getPaymentMethod() %></p>
<p><strong>Ngày đặt hàng:</strong> <%= order.getOrderDate() %></p>
<p><strong>Tổng tiền:</strong> <%= order.getTotalAmount() %></p>

<hr>
<h3>Chuỗi định danh để hash và ký:</h3>
<pre><%= orderString %></pre>

<h3>Giá trị Hash (SHA-256):</h3>
<pre><%= hash %></pre>

<h3>Chữ ký RSA (Base64):</h3>
<pre><%= signature %></pre>

<h3>Public Key:</h3>
<pre><%= publicKeyBase64 %></pre>

<h3>Private Key:</h3>
<pre><%= privateKeyBase64 %></pre>
<%
} else if (request.getParameter("orderId") != null) {
%>
<p style="color: red;">❌ Không tìm thấy đơn hàng với ID: <%= request.getParameter("orderId") %></p>
<%
  }
%>
</body>
</html>
