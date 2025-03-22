<%@ page import="com.example.backend.models.User" %>
<%@ page import="com.example.backend.services.UserService" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh sửa thông tin người dùng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input[type="text"], input[type="email"], input[type="tel"], textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #0f5132;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0c4128;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Chỉnh sửa thông tin người dùng</h2>
    <form action="/admin/editUser" method="POST">
        <input type="hidden" name="userId" value="${user.getId()}">
        <div class="form-group">
            <label for="fullName">Họ và tên:</label>
            <input type="text" id="fullName" name="fullName" value="${user.fullName}" required>
        </div>

        <div class="form-group">
            <label for="username">Tên tài khoản:</label>
            <input type="text" id="username" name="username" value="${user.username}" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}" required>
        </div>

        <div class="form-group">
            <label for="phone">Số điện thoại:</label>
            <input type="tel" id="phone" name="phone" value="${user.phone}" required>
        </div>
        <%
            User user = (User) request.getAttribute("user");
            String dob = user.getDob();
            String[] dobParts = dob.split("-");
            String formattedDob = dobParts[2] + "/" + dobParts[1] + "/" + dobParts[0];
        %>
        <div class="form-group">
            <label for="dob">Ngày tháng năm sinh:</label>
            <input class="dob" type="text" id="dob" name="dob" value="<%= formattedDob %>" required>
        </div>

        <div class="form-group">
            <label for="address">Địa chỉ:</label>
            <textarea id="address" name="address" rows="4" required>${user.address}</textarea>
        </div>

        <button type="submit">Lưu thay đổi</button>
    </form>
</div>
</body>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script>
    // Kiểm tra query string để hiển thị thông báo
    const urlParams = new URLSearchParams(window.location.search);
    const success = urlParams.get('success');

    if (success === 'true') {
        alert('Thêm người dùng thành công!');
        window.location.href = '/admin/users';
    } else if (success === 'false') {
        alert('Thêm người dùng thất bại. Vui lòng thử lại!');
    }
    $(document).ready(function() {
        var currentYear = new Date().getFullYear();
        // Initialize Datepicker
        $("#dob").datepicker({
            dateFormat: "dd/mm/yy",  // Định dạng ngày hiển thị
            changeMonth: true,
            yearRange: "1990:" + currentYear,
            changeYear: true
        });

        // Chuyển đổi ngày trước khi gửi dữ liệu
        $("#addUserForm").submit(function(event) {
            event.preventDefault();  // Ngừng gửi form mặc định

            // Lấy giá trị từ input date
            var dob = $("#dob").val();

            // Chuyển đổi ngày từ dd/mm/yyyy sang yyyy-mm-dd
            var dobParts = dob.split('/');
            var formattedDate = dobParts[2] + '-' + dobParts[1] + '-' + dobParts[0];

            // Cập nhật giá trị ngày theo định dạng yyyy-mm-dd
            $("#dob").val(formattedDate);

            // Bây giờ bạn có thể gửi form đi, ví dụ qua AJAX hoặc submit form
            this.submit();  // Hoặc dùng AJAX để gửi dữ liệu
        });
    });
    function formatDate(dateString) {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return day + '/' + month + '/' + year;
    }
    window.onload = function() {
        const dobCells = document.querySelectorAll('.dob');
        dobCells.forEach(function(cell) {
            const formattedDate = formatDate(cell.textContent);
            cell.textContent = formattedDate;
        });
    };
</script>
</html>
