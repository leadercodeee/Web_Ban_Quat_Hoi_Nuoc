<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.backend.utils.DigitalSignatureUtil" %>
<%@ page import="java.security.PublicKey" %>

<%
    String data = request.getParameter("data");
    String signature = request.getParameter("signature");
    String publicKeyStr = request.getParameter("publicKey");

    Boolean result = null;

    if (data != null && signature != null && publicKeyStr != null) {
        try {
            PublicKey publicKey = DigitalSignatureUtil.decodePublicKey(publicKeyStr);
            result = DigitalSignatureUtil.verify(data, signature, publicKey);
        } catch (Exception e) {
            result = false;
            request.setAttribute("error", e.getMessage());
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Kiểm tra chữ ký số</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        textarea { width: 100%; height: 100px; margin-bottom: 15px; font-family: monospace; }
        .btn { padding: 10px 20px; background: #007bff; color: white; border: none; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .result { font-weight: bold; font-size: 1.2em; margin-top: 20px; }
        .valid { color: green; }
        .invalid { color: red; }
        .error { color: darkred; }
    </style>
</head>
<body>

<h1>🔐 Kiểm tra chữ ký số đơn hàng</h1>

<form method="post">
    <label><strong>1. Chuỗi dữ liệu đã ký (toConcatenatedString):</strong></label><br>
    <textarea name="data" required><%= data != null ? data : "" %></textarea>

    <label><strong>2. Chữ ký số (Base64):</strong></label><br>
    <textarea name="signature" required><%= signature != null ? signature : "" %></textarea>

    <label><strong>3. Public Key (Base64):</strong></label><br>
    <textarea name="publicKey" required><%= publicKeyStr != null ? publicKeyStr : "" %></textarea>

    <button type="submit" class="btn">🔍 Kiểm tra chữ ký</button>
</form>

<%
    if (result != null) {
        if (result) {
%>
<div class="result valid">✅ Chữ ký số HỢP LỆ</div>
<%
} else {
%>
<div class="result invalid">❌ Chữ ký số KHÔNG hợp lệ</div>
<%
        }
    }

    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<div class="error">Lỗi: <%= error %></div>
<%
    }
%>

</body>
</html>
