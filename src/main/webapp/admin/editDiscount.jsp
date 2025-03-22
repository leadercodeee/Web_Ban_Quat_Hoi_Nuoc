<%@ page import="com.example.backend.models.Discount" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa giảm giá</title>
</head>
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

    input[type="text"], input[type="email"], input[type="tel"], input[type ="number"], textarea {
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
<body>
<div class="container">
    <h2>Chỉnh sửa giảm giá</h2>

    <%
        Discount discount = (Discount) request.getAttribute("discount");
        String error = (String) request.getAttribute("error");
    %>

    <% if (error != null) { %>
    <p style="color:red;"><%= error %>
    </p>
    <% } %>

    <form action="/admin/editDiscount" method="post">

        <input type="hidden" name="id" value="<%= discount.getId() %>"/>
        <div class="form-group">
            <label for="name">Tên giảm giá:</label>
            <input type="text" id="name" name="name" value="<%= discount.getName() %>" required/>
        </div>


        <br/>
        <div class="form-group">
            <label for="discount_value">Giảm giá:</label>
            <input type="number" id="discount_value" name="discount_value" value="<%= discount.getDiscountValue() %>"
                   step="0.01" required/>
        </div>

        <br/>
        <%
            String inputStartDate = discount.getStartDate();
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = inputFormat.parse(inputStartDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedStartDate = outputFormat.format(date);
        %>
        <div class="form-group">
            <label for="start_date">Ngày bắt đầu:</label>
            <input type="text" id="start_date" name="start_date" value="<%= formattedStartDate %>" class="datepicker"
                   required/>
        </div>

        <br/>
        <%
            String inputEndDate = discount.getEndDate();
            Date endDate = inputFormat.parse(inputEndDate);
            String formattedEndDate = outputFormat.format(endDate);
        %>
        <div class="form-group">
            <label for="end_date">Ngày kết thúc:</label>
            <input type="text" id="end_date" name="end_date" value="<%= formattedEndDate %>" class="datepicker"
                   required/>
        </div>
        <br/>

        <button type="submit">Lưu thay đổi</button>
    </form>
</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script>
    $(document).ready(function () {
        $(".datepicker").datepicker({
            dateFormat: 'dd/mm/yy'
        });
    });

    function formatDate(dateString) {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return day + '/' + month + '/' + year;
    }

    window.onload = function () {
        const startDate = document.querySelector("#start_date").value
        const endDate = document.querySelector("#end_date").value
        const formattedStartDate = formatDate(startDate)
        startDate.value = formattedStartDate;
        const formattedEndDate = formatDate(endDate)
        endDate.value = formattedEndDate;
    };

</script>

</body>
</html>
