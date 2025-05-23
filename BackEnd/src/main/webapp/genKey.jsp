<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tạo Khóa RSA</title>
</head>
<body>
<h2>Tạo cặp khóa RSA</h2>

<form method="post" action="genKey">
    <input type="submit" value="Tạo Khóa Mới"/>
</form>

<% if (request.getAttribute("publicKey") != null) { %>
<h3>Public Key:</h3>
<textarea rows="5" cols="100"><%= request.getAttribute("publicKey") %></textarea>

<h3>Private Key:</h3>
<textarea rows="5" cols="100"><%= request.getAttribute("privateKey") %></textarea>
<% } %>

<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

</body>
</html>
