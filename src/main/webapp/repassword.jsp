
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu</title>
    <link rel="stylesheet" href="css/repassword.css">
</head>
<body>
<div class="container">
    <h2>Đặt lại mật khẩu<i></i></h2>
    <form id="resetPasswordForm" action="/reset-password" method="post">
        <div class="password-container">
            <input type="password" id="newPassword" name="newPassword" required placeholder="Mật khẩu mới">

        </div>
        <div class="password-container">
            <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Nhập lại mật khẩu">

        </div>
        <button type="submit">Quay về trang đăng nhập</button>
    </form>
    <p id="errorMessage" style="color: red; font-size: 14px; margin-top: 10px; display: none;"></p>


</div>
</body>
</html>
