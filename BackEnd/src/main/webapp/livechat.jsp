<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Live Chat</title>
    <link rel="stylesheet" href="css/livechat.css">
</head>
<body>

<div class="chat-container">

    <div>
        <div class="chat-header">Nhắn Tin Trực Tiếp</div>
    <div class="chat-messages" id="chatMessages"></div>
    <div class="chat-input-container">
        <input type="text" id="chatInput" class="chat-input" placeholder="Nhập tin nhắn...">
        <button class="chat-send-button" onclick="sendMessage()">Gửi</button>
    </div>
    <a href="about.jsp" class="back-to-login">Quay lại trang chủ</a>
    </div>
    
    
</div>


<script src="js/livechat.js"></script>
</body>
</html>
