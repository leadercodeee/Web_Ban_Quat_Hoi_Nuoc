<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Giảm Giá</title>
</head>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f4f4f4;
    }
    .form-container {
        max-width: 700px;
        margin: auto;
        padding: 20px;
        background: white;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .form-container h2 {
        text-align: center;
        margin-bottom: 20px;
    }
    .form-group {
        margin-bottom: 15px;
    }
    label {
        display: block;
        font-weight: bold;
        margin-bottom: 5px;
    }
    input, textarea, select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
    button {
        width: 100%;
        padding: 10px;
        background-color: #0f5132;
        color: white;
        font-size: 16px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
    }
    button:hover {
        background-color: #0c4128;
    }
</style>
<body>
<%
    String error = (String) request.getAttribute("errorMessage");
%>
<div class="form-container">
    <h1>Thêm Giảm Giá</h1>
    <% if (error != null) { %>
    <p style="color:red;"><%= error %>
    </p>
    <% } %>
    <form action="/admin/addDiscount" method="POST">
        <div class="form-group">
            <label for="name">Tên Giảm Giá:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="discount_value">Giá trị Giảm Giá:</label>
            <input type="number" id="discount_value" name="discount_value" step="0.01" required>
        </div>
        <div class="form-group">
            <label for="start_date">Ngày Bắt Đầu:</label>
            <input type="text" id="start_date" name="start_date" required>
        </div>
        <div class="form-group">
            <label for="end_date">Ngày Kết Thúc:</label>
            <input type="text" id="end_date" name="end_date" required>
        </div>
        <div>
            <button type="submit">Thêm Giảm Giá</button>
        </div>
    </form>
</div>

</body>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script>
    $(document).ready(function() {
        // Khởi tạo datepicker cho các trường ngày
        $("#start_date, #end_date").datepicker({
            dateFormat: "dd/mm/yy", // Định dạng ngày cho các trường input
            changeMonth: true, // Cho phép chọn tháng
            changeYear: true,  // Cho phép chọn năm
                yearRange: "1900:2100" // Cho phép chọn năm từ 1900 đến 2100
        });
    });
</script>

</script>
</html>
