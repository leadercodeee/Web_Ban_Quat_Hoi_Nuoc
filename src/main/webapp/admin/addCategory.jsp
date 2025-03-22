<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thêm Danh Mục Sản Phẩm</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 20px;
      background-color: #f4f4f4;
    }
    .form-container {
      max-width: 600px;
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
    input, textarea {
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
    .error-message {
      color: red;
      font-weight: bold;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>
<div class="form-container">
  <h2>Thêm Danh Mục Sản Phẩm</h2>
  <%
    String errMessage = (String) request.getAttribute("err");
    if (errMessage != null) {
  %>
  <div class="error-message">
    <%= errMessage %>
  </div>
  <%
    }
  %>
  <form action="/addCategory" method="POST">
    <div class="form-group">
      <label for="categoryName">Tên Danh Mục</label>
      <input type="text" id="categoryName" name="categoryName" placeholder="Nhập tên danh mục" required>
    </div>
    <div class="form-group">
      <label for="categoryDescription">Mô Tả</label>
      <textarea id="categoryDescription" name="categoryDescription" rows="4" placeholder="Nhập mô tả danh mục"></textarea>
    </div>
    <button type="submit">Thêm Danh Mục</button>
  </form>
</div>
</body>
</html>
