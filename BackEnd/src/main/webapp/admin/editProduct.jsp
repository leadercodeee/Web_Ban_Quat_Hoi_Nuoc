<%@ page import="com.example.backend.models.Product" %>
<%@ page import="com.example.backend.models.ProductImage" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.backend.models.Category" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chỉnh Sửa Sản Phẩm</title>
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
  <h2>Chỉnh Sửa Sản Phẩm</h2>
  <%
    Product product = (Product) request.getAttribute("product");
    if (product != null) {
  %>

  <form action="/admin/editProduct" method="POST">
    <input type="hidden" name="productId" value="<%= product.getId() %>">

    <label for="productName">Tên Sản Phẩm:</label>
    <input type="text" name="productName" value="<%= product.getName() %>" required><br><br>

    <label for="category">Danh Mục:</label>
    <select id="category" name="category" required>
      <option value="">Chọn danh mục</option>
      <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if (categories != null) {
          for (Category category : categories) {
      %>
      <option selected="<%= category.getId() == product.getCategoryId()%>" value="<%= category.getId() %>"><%= category.getName() %></option>
      <%
          }
        }
      %>
    </select>
    <br><br>

    <label for="price">Giá:</label>
    <input type="number" name="price" value="<%= product.getPrice() %>" required><br><br>

    <label for="brand">Thương Hiệu:</label>
    <input type="text" name="brand" value="<%= product.getBrand() %>" required><br><br>

    <label for="quantity">Số Lượng:</label>
    <input type="number" name="quantity" value="<%= product.getStock() %>" required><br><br>

    <label for="description">Mô Tả:</label>
    <textarea name="description"><%= product.getDescription() %></textarea><br><br>

    <label for="image1">Hình Ảnh Chính (URL):</label>
    <input type="url" name="image1" value="<%= product.getImages().size() > 0 ? product.getImages().get(0).getImageUrl() : "" %>" required><br><br>

    <label for="image2">Hình Ảnh Phụ (URL):</label>
    <input type="url" name="image2" value="<%= product.getImages().size() > 1 ? product.getImages().get(1).getImageUrl() : "" %>"><br><br>

    <label for="image3">Hình Ảnh Phụ (URL):</label>
    <input type="url" name="image3" value="<%= product.getImages().size() > 2 ? product.getImages().get(2).getImageUrl() : "" %>"><br><br>

    <label for="image4">Hình Ảnh Phụ (URL):</label>
    <input type="url" name="image4" value="<%= product.getImages().size() > 3 ? product.getImages().get(3).getImageUrl() : "" %>"><br><br>



    <button type="submit">Cập Nhật Sản Phẩm</button>
  </form>

  <%
  } else {
  %>
  <p>Không tìm thấy sản phẩm.</p>
  <%
    }
  %>
</div>
</body>
</html>
