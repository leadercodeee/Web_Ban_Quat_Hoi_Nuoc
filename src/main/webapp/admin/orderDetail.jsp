<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.backend.models.Order" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.backend.models.OrderDetail" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="stylesheet/orderDetail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }

        /* Tiêu đề */
        h2 {
            color: #333;
            text-align: center;
        }

        /* Thiết lập bảng đẹp hơn */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        /* Style cho tiêu đề của bảng */
        th {
            background-color: #0f5132;
            color: white;
            padding: 12px 15px;
            text-align: left;
        }

        /* Style cho các ô trong bảng */
        td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        /* Khi hover vào hàng */
        tr:hover {
            background-color: #f1f1f1;
        }

        /* Thiết lập cho các đường viền */
        th, td {
            border: 1px solid #ddd;
        }

        /* Style cho liên kết "Back to Home" */
        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #0f5132;
            color: white;
            text-decoration: none;
            font-size: 16px;
            border-radius: 5px;
        }

        /* Khi hover vào liên kết */
        a:hover {
            background-color: #0c4128;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- Left Sidebar -->
    <div class="left">
        <!-- Sidebar content -->
    </div>
    <!-- Right Content -->
    <div class="right">
        <!-- Navbar -->
        <div class="navbar">
            <!-- Navbar content -->
        </div>
        <!-- Content -->
        <div class="content">
            <h2>Chi tiết đơn hàng</h2>

            <table border="1">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Mã đơn hàng</th>
                    <th>Mã sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                </tr>
                </thead>
                <tbody>
                <%
                    // Lấy danh sách chi tiết đơn hàng từ request
                    List<OrderDetail> orderDetailsList = (List<OrderDetail>) request.getAttribute("orderDetails");
                    for (OrderDetail orderDetails : orderDetailsList) {
                %>
                <tr>
                    <td><%= orderDetails.getId() %></td>
                    <td><%= orderDetails.getOrderId() %></td>
                    <td><%= orderDetails.getProductId() %></td>
                    <td><%= orderDetails.getQuantity() %></td>
                    <td><%= orderDetails.getPrice() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>

            <br>
            <a href="/admin/orders">Quay trở lại</a>

        </div>
    </div>
</div>

<script>
    function goBack() {
        window.history.back();
    }
</script>

</body>
</html>
