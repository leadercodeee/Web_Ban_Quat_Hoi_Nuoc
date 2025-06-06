<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.backend.models.Order, com.example.backend.models.CartItem" %>
<%@ page import="java.util.Map" %>

<%
    Order order = (Order) request.getAttribute("order");
    Map<Integer, CartItem> cartItems = (Map<Integer, CartItem>) request.getAttribute("cartItems");
    Boolean signatureValid = (Boolean) request.getAttribute("signatureValid");
    java.security.PublicKey publicKey = (java.security.PublicKey) request.getAttribute("publicKey");

    // Convert public key to Base64 string for display
    String publicKeyBase64 = "";
    if (publicKey != null) {
        publicKeyBase64 = java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đơn hàng</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .valid { color: green; font-weight: bold; }
        .invalid { color: red; font-weight: bold; }
        pre { background: #f4f4f4; padding: 10px; overflow-x: auto; }
    </style>
</head>
<body>

<h1>Thông tin đơn hàng</h1>

<table>
    <tr><th>Mã đơn hàng</th><td><%= order.getId() %></td></tr>
    <tr><th>ID người dùng</th><td><%= order.getUserId() %></td></tr>
<<<<<<< HEAD
    <tr><th>Số điện thoại</th><td><%= order.getPhone() != null ? order.getPhone() : "Không có sđt" %></td></tr>
=======
>>>>>>> e6b19340aff8970cf777c8720f1f893458ba5e7d
    <tr><th>Tổng tiền</th><td><%= String.format("%,.2f", order.getTotalAmount()) %> VNĐ</td></tr>
    <tr><th>Địa chỉ giao hàng</th><td><%= order.getShippingAddress() %></td></tr>
    <tr><th>Phương thức thanh toán</th><td><%= order.getPaymentMethod() %></td></tr>
    <tr><th>Ngày đặt hàng</th><td><%= order.getOrderDate() %></td></tr>
    <tr><th>Ngày giao hàng dự kiến</th><td><%= order.getDeliveryDate() %></td></tr>
    <tr><th>Trạng thái</th><td><%= order.getStatus() %></td></tr>
</table>

<h2>Chi tiết sản phẩm</h2>
<table>
    <thead>
    <tr>
        <th>Tên sản phẩm</th>
        <th>Số lượng</th>
        <th>Đơn giá (VNĐ)</th>
        <th>Thành tiền (VNĐ)</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (cartItems != null) {
            for (CartItem item : cartItems.values()) {
                double price = item.getProduct().getPrice();
                int quantity = item.getQuantity();
                double total = price * quantity;
    %>
    <tr>
        <td><%= item.getProduct().getName() %></td>
        <td><%= quantity %></td>
        <td><%= String.format("%,.2f", price) %></td>
        <td><%= String.format("%,.2f", total) %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr><td colspan="4">Không có sản phẩm nào</td></tr>
    <% } %>
    </tbody>
</table>

<h2>Xác minh chữ ký số đơn hàng</h2>
<p>
    <% if (signatureValid != null && signatureValid) { %>
    <span class="valid">Chữ ký số hợp lệ ✅</span>
    <% } else { %>
    <span class="invalid">Chữ ký số không hợp lệ ❌</span>
    <% } %>
</p>

<h3>Public Key (Base64)</h3>
<pre><%= publicKeyBase64 %></pre>

</body>
</html>
