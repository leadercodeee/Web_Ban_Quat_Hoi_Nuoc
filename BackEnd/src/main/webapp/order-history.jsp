<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.backend.models.Order" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử đơn hàng</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f9f9f9;
            margin: 20px;
            color: #333;
        }
        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 0 auto 40px auto;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px 20px;
            text-align: center;
        }
        thead {
            background-color: #236313;
            color: white;
            font-weight: 600;
        }
        tbody tr:nth-child(even) {
            background-color: #f2f6fb;
        }
        tbody tr:hover {
            background-color: #d0e7ff;
            cursor: pointer;
        }
        .btn-detail {
            padding: 6px 12px;
            background-color: #236313;
            color: white;
            border-radius: 4px;
            transition: background-color 0.3s ease;
            text-decoration: none;
        }
        .btn-detail:hover {
            background-color: #236313;
        }
        p.no-orders {
            text-align: center;
            font-size: 1.1rem;
            color: #888;
            margin-top: 50px;
        }
        @media (max-width: 768px) {
            table {
                width: 95%;
            }
            th, td {
                padding: 10px;
            }
        }
    </style>
</head>
<body>

<h2>Lịch sử đơn hàng của bạn</h2>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    if (orders == null || orders.isEmpty()) {
%>
<p class="no-orders">Bạn chưa có đơn hàng nào.</p>
<%
} else {
%>
<table>
    <thead>
    <tr>
        <th>Mã đơn hàng</th>
        <th>Ngày đặt</th>
        <th>Tổng tiền</th>
        <th>Chi tiết</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Order order : orders) {
    %>
    <tr>
        <td><%= order.getId() %></td>
        <td><%= sdf.format(order.getOrderDate()) %></td>
        <td><%= String.format("%,.0f VNĐ", order.getTotalAmount()) %></td>
        <td><a class="btn-detail" href="order-confirmation?orderId=<%= order.getId() %>">Xem chi tiết</a></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>

</body>
</html>
