<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm người dùng</title>
    <style>
        /* style.css */
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

        input[type="text"], input[type="email"], input[type="tel"],input[type="password"], textarea {
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
        input[type="date"] {
            width: 100%;  /* Chiều rộng đầy đủ */
            padding: 10px;  /* Khoảng cách nội dung bên trong */
            border: 1px solid #ccc;  /* Đường viền màu xám */
            border-radius: 4px;  /* Bo góc */
            font-size: 16px;  /* Kích thước chữ */
            background-color: #fff;  /* Nền trắng */
            color: #333;  /* Màu chữ */
            outline: none;  /* Bỏ viền mặc định khi focus */
        }

        input[type="date"]:focus {
            border-color: #0f5132;  /* Đổi màu viền khi input được focus */
            background-color: #f4f4f4;  /* Thay đổi nền khi focus */
        }

        input[type="date"]::-webkit-calendar-picker-indicator {
            background-color: #0f5132;  /* Màu cho biểu tượng lịch */
            border-radius: 50%;  /* Bo góc biểu tượng lịch */
            color: white;
            padding: 5px;  /* Khoảng cách giữa biểu tượng và viền */
        }

        input[type="date"]:hover {
            border-color: #0c4128;  /* Đổi màu viền khi hover */
        }

    </style>
</head>

<body>
<div class="container">
    <h2>Thêm người dùng mới</h2>
    <form id="/admin/addUser" method="POST">
        <div class="form-group">
            <label for="fullName">Họ và tên:</label>
            <input type="text" id="fullName" name="fullName" required>
        </div>

        <div class="form-group">
            <label for="username">Tên tài khoản:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="username">Mật khẩu:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="phone">Số điện thoại:</label>
            <input type="tel" id="phone" name="phone" pattern="[0-9]{10}" required>
        </div>

        <div class="form-group">
            <label for="dob">Ngày tháng năm sinh:</label>
            <input type="text" id="dob" name="dob" required>
        </div>


        <div class="form-group">
            <label for="address">Địa chỉ:</label>
            <textarea id="address" name="address" rows="4" required></textarea>
        </div>

        <button type="submit">Thêm người dùng</button>
    </form>
</div>

<script src="script.js"></script>
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
        // Initialize Datepicker
        $("#dob").datepicker({
            dateFormat: "dd/mm/yy",  // Định dạng ngày hiển thị
            changeMonth: true,
            changeYear: true
        });

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

</script>
</body>
</html>
