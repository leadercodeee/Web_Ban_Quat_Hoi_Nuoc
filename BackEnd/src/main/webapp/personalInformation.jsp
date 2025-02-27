<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Trang Cá Nhân</title>
    <link rel="stylesheet" href="css/personalInformation.css">
</head>
<body>
    <div class="container">
        <h2>Chỉnh Sửa Trang Cá Nhân</h2>
        <form id="profileForm">
           

            <label for="Address">Địa chỉ</label>
            <input type="text" id="Address" Address="Address" required placeholder="Nhập địa chỉ bạn">

            <label for="name">Họ và Tên</label>
            <input type="text" id="name" name="name" required placeholder="Nhập tên của bạn">

            <label for="email">Địa chỉ Email</label>
            <input type="text" id="email" name="email" required placeholder="Nhập email của bạn">



            <label for="acceptTerms">             
                <a href="changePassword.jsp">Đổi mật khẩu</a>
            </label>
   
            <label for="acceptTerms">
                <input type="checkbox" id="acceptTerms" name="acceptTerms" required>
                Tôi đồng ý với các <a href="#">Điều khoản và Điều kiện</a>
            </label>

            <button onclick="window.location.href='index.html'" type="submit"  id="submitButton" disabled>Lưu Thay Đổi</button>
        </form>
        <a href="index.jsp" class="back-to-info">Quay lại trang chủ</a>
        <p id="confirmationMessage" class="hidden">Thông tin đã được cập nhật!</p>
    </div>

    <script src="js/personalInformation.js"></script>
</body>
</html>
