<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page isELIgnored="false" %>  <!-- Enable EL -->
<html>
<head>
    <title>Xác minh chữ ký đơn hàng</title>
</head>
<body>
<h2>Xác minh chữ ký đơn hàng</h2>

<form action="verifySignature" method="post">
    <label>Order&nbsp;ID:</label>
    <input type="number" name="orderId" required />   <!-- number cho chắc kiểu -->
    <br/><br/>

    <label>Public&nbsp;Key (Base64):</label><br/>
    <textarea name="publicKeyBase64" rows="6" cols="80" required></textarea>
    <br/><br/>

    <input type="submit" value="Xác&nbsp;minh"/>
</form>

<h3>Kết quả: </h3>

</body>
</html>
