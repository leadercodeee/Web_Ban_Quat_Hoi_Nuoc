<%--
  Created by IntelliJ IDEA.
  User: hongh
  Date: 23/05/2025
  Time: 10:38 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>Sign Order</title></head>
<body>
<h2>Sign Order</h2>
<form action="sign" method="post">
  <textarea name="orderData" rows="5" cols="40" placeholder="Enter order details..."></textarea><br>
  <input type="submit" value="Sign Order">
</form>
<p>${signature}</p>
</body>
</html>
