<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.backend.models.Order, com.example.backend.models.CartItem" %>
<%@ page import="java.util.Map" %>

<%
    Order order = (Order) request.getAttribute("order");
    Map<Integer, CartItem> cartItems = (Map<Integer, CartItem>) request.getAttribute("cartItems");
    Boolean signatureValid = (Boolean) request.getAttribute("signatureValid");
    Boolean signingSuccess = (Boolean) request.getAttribute("signingSuccess");
    java.security.PublicKey publicKey = (java.security.PublicKey) request.getAttribute("publicKey");
    String orderHash = (String) request.getAttribute("orderHash");
    String signatureError = (String) request.getAttribute("signatureError");

    String publicKeyBase64 = "";
    if (publicKey != null) {
        publicKeyBase64 = java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
%>

<%
    if ("true".equals(request.getParameter("hashed"))) {
%>
<p style="color: green;"><strong>✅ Đã hash đơn hàng thành công.</strong></p>
<%
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
        .status-box { margin-top: 20px; padding: 15px; border-radius: 5px; }
        .success { background-color: #e6ffe6; border: 1px solid #00cc00; }
        .error { background-color: #ffe6e6; border: 1px solid #ff0000; }
    </style>
</head>
<body>

<h1>Xác nhận đơn hàng</h1>

<table>
    <tr><th>Mã đơn hàng</th><td><%= order.getId() %></td></tr>
    <tr><th>ID người dùng</th><td><%= order.getUserId() %></td></tr>
    <tr><th>Số điện thoại</th><td><%= order.getPhone() != null ? order.getPhone() : "Không có sđt" %></td></tr>
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

<!-- Form xác nhận đơn hàng -->
<form action="confirm-order" method="post">
    <input type="hidden" name="orderId" value="<%= order.getId() %>"/>
    <button type="submit">Xác nhận đơn hàng</button>
</form>



</body>
</html>
