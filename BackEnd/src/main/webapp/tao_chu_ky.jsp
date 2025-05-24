<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>TẠO CHỮ KÝ ĐIỆN TỬ</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7ecdf;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            color: #2196f3;
            padding: 20px 0 0 0;
            margin: 0;
        }

        fieldset {
            margin: 30px auto;
            width: 96%;
            padding: 20px;
            border: 1px solid #ccc;
        }

        legend {
            font-weight: bold;
            color: mediumvioletred;
            font-size: 16px;
        }

        .key-container {
            display: flex;
            justify-content: space-between;
            gap: 20px;
        }

        textarea {
            width: 100%;
            height: 150px;
            resize: none;
            font-family: monospace;
            font-size: 14px;
        }

        .key-box {
            flex: 1;
        }

        .key-box label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }

        .buttons {
            text-align: right;
            padding-top: 15px;
        }

        .buttons form {
            display: inline;
        }

        .buttons button {
            background-color: #003049;
            color: orange;
            border: none;
            padding: 10px 20px;
            font-weight: bold;
            cursor: pointer;
            margin-left: 10px;
            border-radius: 5px;
        }

        .buttons button:last-child {
            background-color: #fdb833;
            color: #003049;
        }

        .message {
            text-align: center;
            margin-top: 10px;
            font-weight: bold;
        }

        .message.success {
            color: green;
        }

        .message.error {
            color: red;
        }
    </style>
</head>
<body>
<h2>TẠO CHỮ KÝ ĐIỆN TỬ</h2>

<fieldset>
    <legend>TẠO KHÓA</legend>
    <div class="key-container">
        <div class="key-box">
            <label for="privateKey">Private Key</label>
            <textarea readonly><%= request.getAttribute("privateKey") != null ? request.getAttribute("privateKey") : "" %></textarea>
        </div>
        <div class="key-box">
            <label for="publicKey">Public Key</label>
            <textarea readonly><%= request.getAttribute("publicKey") != null ? request.getAttribute("publicKey") : "" %></textarea>
        </div>
    </div>

    <div class="buttons">
        <form action="gen-key" method="get">
            <button type="submit">Generate Key</button>
        </form>

        <form action="download-public-key" method="post">
            <input type="hidden" name="publicKey" value="<%= request.getAttribute("publicKey") != null ? ((String)request.getAttribute("publicKey")).replaceAll("\\r?\\n", "&#10;") : "" %>">
            <button type="submit">Lưu Public Key</button>
        </form>
    </div>
</fieldset>

<%
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
%>
<% if (message != null) { %>
<div class="message success"><%= message %></div>
<% } %>
<% if (error != null) { %>
<div class="message error"><%= error %></div>
<% } %>

</body>
</html>
