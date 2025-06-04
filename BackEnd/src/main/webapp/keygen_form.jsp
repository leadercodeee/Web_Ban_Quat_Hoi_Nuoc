<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tạo cặp khóa RSA</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f9f9f9;
            padding: 40px;
            text-align: center;
        }
        h2 {
            color: #333;
        }
        button {
            padding: 12px 24px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            margin-top: 20px;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<h2>Chào mừng! Bạn muốn tạo cặp khóa RSA?</h2>
<form action="keygen" method="get">
    <button type="submit">Tạo cặp khóa</button>
</form>

</body>
</html>
