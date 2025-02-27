<%@ page import="com.example.backend.models.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Sản Phẩm</title>
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
</head>
<body>
<div class="form-container">
    <h2>Thêm Sản Phẩm</h2>
    <form action="/admin/addProduct" method="POST">
        <div class="form-group">
            <label for="productName">Tên Sản Phẩm</label>
            <input type="text" id="productName" name="productName" required>
        </div>
        <div class="form-group">
            <label for="category">Danh Mục</label>
            <select id="category" name="category" required>
                <option value="">Chọn danh mục</option>
                <%
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null) {
                        for (Category category : categories) {
                %>
                <option value="<%= category.getId() %>"><%= category.getName() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div class="form-group">
            <label for="price">Giá (VND)</label>
            <input type="number" id="price" name="price" required>
        </div>
        <div class="form-group">
            <label for="brand">Thương Hiệu</label>
            <input type="text" id="brand" name="brand" required>
        </div>
        <div class="form-group">
            <label for="quantity">Số Lượng</label>
            <input type="number" id="quantity" name="quantity" required>
        </div>
        <div class="form-group">
            <label for="description">Mô Tả</label>
            <textarea id="description" name="description" rows="4"></textarea>
        </div>
        <div class="form-group">
            <label for="image1">Hình Ảnh Chính (URL)</label>
            <input type="url" id="image1" name="image1" required>
        </div>
        <div class="form-group">
            <label for="image2">Hình Ảnh Phụ  (URL)</label>
            <input type="url" id="image2" name="image2">
        </div>
        <div class="form-group">
            <label for="image3">Hình Ảnh Phụ (URL)</label>
            <input type="url" id="image3" name="image3">
        </div>
        <div class="form-group">
            <label for="image4">Hình Ảnh Phụ (URL)</label>
            <input type="url" id="image4" name="image4">
        </div>
        <button type="submit">Thêm Sản Phẩm</button>
    </form>
</div>
</body>
</html>
