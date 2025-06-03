<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Tạo khóa</title></head>
<body>
<h2>Tạo Khóa Mới</h2>
<form action="gen-key" method="post">
    <input type="submit" value="Tạo khóa RSA mới">
</form>
<% if (request.getAttribute("message") != null) { %>
<p style="color:green;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>
