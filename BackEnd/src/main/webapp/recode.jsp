
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nhập mã</title>
    <link rel="stylesheet" href="css/recode.css">
</head>
<body>
<div class="container">
    <h2>Mã của bạn đã được gửi về email hoặc số điện thoại</h2>
    <p>Vui lòng nhập mã của bạn.</p>
    <form action="/recode-password" method="POST">
        <input type="text" id="email" name="otp" required placeholder="XXXXXX">
        <button  type="submit">Tiếc tục</button>
    </form>
    <a href="auth.jsp" class="back-to-login">Quay lại trang đăng nhập</a>
</div>
</body>
</html>
