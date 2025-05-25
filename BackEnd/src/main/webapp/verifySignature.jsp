<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Xác minh chữ ký đơn hàng</title>
</head>
<body>
<h2>Xác minh chữ ký đơn hàng</h2>

<form action="verifySignature" method="post">
    <label>Order ID: </label>
    <input type="text" name="orderId" required><br><br>

    <label>Public Key (Base64): </label><br>
    <textarea name="publicKeyBase64" rows="5" cols="80" required></textarea><br><br>

    <input type="submit" value="Xác minh">
</form>

<% String result = (String) request.getAttribute("verificationResult");
    if (result != null) { %>
<h3>Kết quả: <%= result %></h3>
<% } %>

</body>
</html>
