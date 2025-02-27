<%@ page import="com.example.backend.models.Category" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh sửa danh mục sản phẩm</title>
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

        input[type="text"], textarea {
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
    <h2>Chỉnh sửa danh mục sản phẩm</h2>

    <!-- Form xử lý chỉnh sửa danh mục -->
    <%
        Category category = (Category) request.getAttribute("category");

    %>
    <form action="/editCategory" method="POST">
        <!-- Truyền ID danh mục để thực hiện chỉnh sửa -->
        <input type="hidden" name="categoryId"  value="<%= category.getId() %>">

        <div class="form-group">
            <label for="categoryName">Tên danh mục:</label>
            <!-- Hiển thị tên danh mục hiện tại -->
            <input type="text" id="categoryName" name="categoryName" value="<%= category.getName() %>" required>
        </div>

        <div class="form-group">
            <label for="categoryDescription">Mô tả:</label>
            <!-- Hiển thị mô tả hiện tại của danh mục -->
            <textarea id="categoryDescription" name="categoryDescription" rows="4" required>${category.getDescription()}</textarea>
        </div>

        <button type="submit">Lưu thay đổi</button>
    </form>
</div>
</body>
</html>
