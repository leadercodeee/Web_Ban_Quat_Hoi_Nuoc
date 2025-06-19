<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.backend.models.Order" %>

<%
    Order order = (Order) request.getAttribute("order");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <title>Hiển thị chữ ký số đơn hàng #<%= order != null ? order.getId() : "" %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f9f9f9;
            color: #333;
        }
        h1 {
            color: #2c3e50;
        }
        .box {
            background: #fff;
            padding: 20px 25px;
            margin-bottom: 25px;
            border-radius: 6px;
            box-shadow: 0 2px 8px rgb(0 0 0 / 0.1);
        }
        pre {
            background-color: #272822;
            color: #f8f8f2;
            padding: 15px;
            border-radius: 4px;
            overflow-x: auto;
            font-size: 14px;
            line-height: 1.4em;
            white-space: pre-wrap;
            word-wrap: break-word;
        }
        strong {
            color: #34495e;
        }
        .label {
            font-weight: bold;
            margin-bottom: 6px;
            display: block;
            font-size: 16px;
        }
    </style>
</head>
<body>

<% if (order == null) { %>
<h2>Không tìm thấy đơn hàng để hiển thị chữ ký số.</h2>
<% } else { %>
<h1>Thông tin đơn hàng #<%= order.getId() %></h1>

<div class="box">
    <span class="label">Chuỗi dữ liệu dùng để ký (toConcatenatedString):</span>
    <pre><%= order.toConcatenatedString() %></pre>
</div>

<div class="box">
    <span class="label">Chữ ký số (Base64):</span>
    <pre><%= order.getSignature() != null ? order.getSignature() : "Chưa có chữ ký số" %></pre>
</div>

<%-- Nếu có muốn hiển thị thêm public key Base64 thì bỏ comment phần dưới --%>
<%--
<div class="box">
    <span class="label">Public Key (Base64):</span>
    <pre><%= request.getAttribute("publicKeyBase64") != null ? request.getAttribute("publicKeyBase64") : "Không có" %></pre>
</div>
--%>
<% } %>

</body>
</html>
