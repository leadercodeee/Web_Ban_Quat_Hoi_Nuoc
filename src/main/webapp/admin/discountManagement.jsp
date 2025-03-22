<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.example.backend.models.Order" %>
<%@ page import="com.example.backend.models.Discount" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Quản lý giảm giá</title>
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
            <h2 style="font-size: 30px; font-weight: bold; margin-bottom: 40px;">Quản lý giảm giá</h2>
            <div class="tablef-container">
                <table>
                    <thead>
                    <tr>
                        <th>Mã giảm giá</th>
                        <th>Tên giảm giá</th>
                        <th>Số tiền giảm</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày kết thúc</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
                        for (Discount discount : discounts) {
                    %>
                    <tr>
                        <td style="text-align: center"><%= discount.getId() %></td>
                        <td><%= discount.getName() %></td>
                        <td><%= discount.getDiscountValue() %></td>
                        <td class="startDate"><%= discount.getStartDate() %></td>
                        <td class="endDate"><%= discount.getEndDate() %></td>
                        <td style="cursor: pointer">
                            <a style="color: black" href="/admin/deleteDiscount?id=<%= discount.getId() %>">
                                <i class="fa-solid fa-trash"></i>
                            </a>
                            <a style="color: black" href="/admin/editDiscount?id=<%= discount.getId() %>">
                                <i class="fa-solid fa-pen"></i>
                            </a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <div style=";margin-top: 40px; display: flex; justify-content: center">
                <button class="add-btn"
                        style=" margin: 0 auto ; background-color: #0f5132; border-radius: 8px; color: white">
                    <a href="/admin/addDiscount"
                       style="color: white;padding: 10px 20px; display: block; text-decoration: none">Thêm giảm giá</a>
                </button>
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
        const startDateList = document.querySelectorAll(".startDate");
        const endDateList = document.querySelectorAll(".endDate");
        startDateList.forEach(function(cell) {
            const formattedDate = formatDate(cell.textContent);
            cell.textContent = formattedDate;
        });
        endDateList.forEach(function(cell) {
            const formattedDate = formatDate(cell.textContent);
            cell.textContent = formattedDate;
        });
    };
</script>
</html>
