<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.example.backend.models.Order" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="stylesheet/orderManagement.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- Thêm thư viện jQuery -->
</head>
<body>
<div class="container">
    <!-- Left -->
    <div class="left">
        <!-- Sidebar -->
        <!-- logo -->
    </div>
    <!-- Right -->
    <div class="right">
        <!-- Navbar -->
        <div class="navbar">
            <!-- Search -->
        </div>
        <!-- Content -->
        <div class="content">
            <h2 style="font-size: 30px; font-weight: bold; margin-bottom: 40px;">Quản lý đơn hàng</h2>
            <div class="tablef-container">
                <table>
                    <thead>
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Mã khách hàng</th>
                        <th>Tổng tiền</th>
                        <th>Địa chỉ giao hàng</th>
                        <th>Phương thức thanh toán</th>
                        <th>Ngày đặt hàng</th>
                        <th>Ngày giao hàng</th>
                        <th>Trạng thái đơn hàng</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Order> orders = (List<Order>) request.getAttribute("orders");
                        for (Order order : orders) {
                    %>
                    <tr>
                        <td><%= order.getId() %>
                        </td>
                        <td><%= order.getUserId() %>
                        </td>
                        <%
                            java.text.NumberFormat currencyFormatter =
                                    java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                        %>
                        <td><%= currencyFormatter.format(order.getTotalAmount()) %> đ</td>
                        <td><%= order.getShippingAddress() %>
                        </td>
                        <td><%= order.getPaymentMethod() %>
                        </td>
                        <%
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        %>
                        <td id="orderDate"><%= dateFormat.format(order.getOrderDate())%>
                        </td>
                        <td id="delivery"><%= dateFormat.format(order.getDeliveryDate())%>
                        </td>
                        <td>
                            <select id="order-status-<%= order.getId() %>" name="order-status"
                                    onchange="updateOrderStatus(<%= order.getId() %>, this.value)">
                                <option value="confirmed" <%= order.getStatus().equals("confirmed") ? "selected" : "" %>>
                                    Đã xác nhận
                                </option>
                                <option value="processing" <%= order.getStatus().equals("processing") ? "selected" : "" %>>
                                    Đang xử lý
                                </option>
                                <option value="shipping" <%= order.getStatus().equals("shipping") ? "selected" : "" %>>
                                    Đang vận chuyển
                                </option>
                                <option value="delivered" <%= order.getStatus().equals("delivered") ? "selected" : "" %>>
                                    Đã giao hàng
                                </option>
                                <option value="cancelled" <%= order.getStatus().equals("cancelled") ? "selected" : "" %>>
                                    Đã hủy
                                </option>
                            </select>
                        </td>
                        <td style="text-align: center; cursor: pointer"><a style="color: black"
                                href="<%= request.getContextPath() %>/admin/orderDetail?orderId=<%= order.getId() %>">
                            <i class="fa-solid fa-eye"></i>
                        </a></td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
<script src="<%= request.getContextPath() %>/admin/common/sidebar.js?v=1.0"></script>
<script src="<%= request.getContextPath() %>/admin/common/navbar.js?v=1.0"></script>

<script>
    function updateOrderStatus(orderId, status) {
        $.ajax({
            url: '/admin/updateOrderStatus',  // URL tới servlet
            type: 'POST',
            data: {
                orderId: orderId,
                status: status
            },
            success: function (response) {
                alert("Cập nhật trạng thái đơn hàng thành công!");
            },
            error: function (xhr, status, error) {
                alert("Có lỗi xảy ra khi cập nhật trạng thái!");
            }
        });
    }
    function formatDate(dateString) {
        var date = new Date(dateString); // Chuyển chuỗi ngày thành đối tượng Date
        var day = date.getDate().toString().padStart(2, '0'); // Lấy ngày và đảm bảo có 2 chữ số
        var month = (date.getMonth() + 1).toString().padStart(2, '0'); // Lấy tháng và đảm bảo có 2 chữ số
        var year = date.getFullYear(); // Lấy năm

        // Trả về ngày theo định dạng dd/mm/yyyy
        return day + '/' + month + '/' + year;
    }
    window.onload = function() {
        const deliveryDate = document.querySelector("#delivery");
        const orderDate = document.querySelector("#orderDate");
        const formattedDeliveryDate = formatDate(deliveryDate.textContent);
        const formattedOrderDate = formatDate(orderDate.textContent)
        deliveryDate.textContent = formattedDeliveryDate
        orderDate.textContent = formattedOrderDate
    };
</script>
</html>
