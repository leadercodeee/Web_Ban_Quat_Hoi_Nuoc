<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.backend.models.User" %>

<%
    HttpSession sessionUser = request.getSession(false);
    if (sessionUser == null || sessionUser.getAttribute("user") == null) {
        response.sendRedirect("auth.jsp");
        return;
    }
    User user = (User) sessionUser.getAttribute("user");
    String privateKeyPEM = (String) request.getAttribute("privateKeyPEM");
    String publicKeyPEM = (String) request.getAttribute("publicKeyPEM");
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>🔐 Quản lý Khóa Người Dùng</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f0f4f8;
            padding: 40px;
            color: #2c3e50;
        }

        h2 {
            color: #34495e;
        }

        .key-block {
            background: #ffffff;
            border: 1px solid #ccc;
            padding: 15px;
            border-radius: 10px;
            white-space: pre-wrap;
            word-break: break-all;
            margin-bottom: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.08);
        }

        .btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 18px;
            margin: 8px 8px 8px 0;
            font-size: 14px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: 0.3s ease;
        }

        .btn:hover {
            background-color: #216fa2;
        }

        .header-box {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 25px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        }

        .info {
            font-size: 15px;
            margin-bottom: 10px;
        }

        .form-inline {
            display: inline-block;
        }
    </style>
</head>
<body>

<div class="header-box">
    <h2>🔐 Cặp khóa RSA của bạn</h2>
    <div class="info">Xin chào, <strong><%= user.getFullName() %></strong> (Email: <%= user.getEmail() %>)</div>
</div>

<% if (privateKeyPEM != null && publicKeyPEM != null) { %>
<h3>🔑 Private Key:</h3>
<div class="key-block"><%= privateKeyPEM %></div>
<a class="btn" href="downloadKey?type=private">⬇ Tải Private Key</a>

<h3>📢 Public Key:</h3>
<div class="key-block"><%= publicKeyPEM %></div>
<a class="btn" href="downloadKey?type=public">⬇ Tải Public Key</a>

<form action="reset-key" method="post" class="form-inline">
    <button class="btn" type="submit">🔁 Báo mất khóa - Tạo lại</button>
</form>
<% } else { %>
<div class="info">Bạn chưa có cặp khóa RSA.</div>
<a class="btn" href="keygen">⚙️ Tạo cặp khóa mới</a>
<% } %>

<br><br>
<a class="btn" href="index.jsp">← Quay lại Trang chủ</a>

</body>
</html>
