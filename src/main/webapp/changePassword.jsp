
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi Mật Khẩu</title>
    <link rel="stylesheet" href="css/changePassword.css">
</head>
<body>

<div class="container">
    <%-- Hiển thị thông báo --%>
    <% String message = (String) request.getAttribute("message"); %>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (message != null) { %>
    <div style="color: red" class="alert alert-success"><%= message %>
    </div>
    <% } %>
    <% if (error != null) { %>
    <div style="color: red" class="alert alert-danger"><%= error %>
    </div>
    <% } %>
    <h2>Đổi Mật Khẩu</h2>
    <form id="changePasswordForm" action="change-password" method="post">
        <div class="input-group">
            <label for="currentPassword">Mật khẩu hiện tại</label>
            <input type="password" id="currentPassword" name="currentPassword" required
                   placeholder="Nhập mật khẩu hiện tại">

        </div>
        <div class="input-group">
            <label for="newPassword">Mật khẩu mới</label>
            <input type="password" id="newPassword" name="newPassword" required placeholder="Nhập mật khẩu mới">

        </div>
        <div class="input-group">
            <label for="confirmPassword">Xác nhận mật khẩu mới</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required
                   placeholder="Xác nhận mật khẩu mới">

        </div>
        <button type="submit">Lưu Thay Đổi</button>
        <a href="home" style=" display: block;color: black; margin-top: 10px">Quay lại trang chủ</a>
    </form>
</div>
</body>
</html>
