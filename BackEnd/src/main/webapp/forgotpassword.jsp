
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
    <link rel="stylesheet" href="css/forgotpassword.css">
</head>
<body>
<div class="container">
    <h2>Tìm tài khoản của bạn</h2>
    <p>Vui lòng nhập email hoặc số di động để tìm kiếm tài khoản của bạn.</p>
    <form action="/forgot-password" method="POST">
        <input type="text" id="email" name="email" required placeholder="Nhập email hoặc số điện thoại của bạn">
        <button  type="submit">Tìm kiếm</button>
    </form>
    <a href="login.html" class="back-to-login">Quay lại trang đăng nhập</a>
</div>
</body>
</html>
