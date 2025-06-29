<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Chữ ký điện tử</title>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background: #f2f6fc;
      margin: 0;
      padding: 0;
    }

    .container {
      max-width: 900px;
      margin: 40px auto;
      background: #ffffff;
      border-radius: 8px;
      padding: 30px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.1);
    }

    h2 {
      text-align: center;
      margin-bottom: 30px;
      color: #333;
    }

    .form-group {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
    }

    .form-group label {
      flex: 0 0 180px;
      font-weight: 600;
      color: #444;
    }

    .form-group input[type="text"] {
      flex: 1;
      padding: 10px;
      border-radius: 4px;
      border: 1px solid #ccc;
    }

    .form-group button {
      margin-left: 10px;
      padding: 10px 16px;
      background-color: #007BFF;
      border: none;
      color: white;
      border-radius: 4px;
      cursor: pointer;
    }

    .form-group button:hover {
      background-color: #0056b3;
    }

    .panels {
      display: flex;
      justify-content: space-between;
      gap: 20px;
      margin-bottom: 20px;
    }

    .panel {
      flex: 1;
      display: flex;
      flex-direction: column;
    }

    .panel label {
      font-weight: bold;
      margin-bottom: 8px;
      color: #333;
    }

    .panel textarea {
      flex: 1;
      min-height: 180px;
      resize: vertical;
      padding: 10px;
      font-family: monospace;
      border: 1px solid #ccc;
      border-radius: 4px;
      margin-bottom: 10px;
    }

    .panel button {
      align-self: flex-end;
      padding: 8px 16px;
      background-color: #28a745;
      border: none;
      color: white;
      border-radius: 4px;
      cursor: pointer;
    }

    .panel button:hover {
      background-color: #1e7e34;
    }

    .arrow {
      align-self: center;
      font-size: 30px;
      color: #666;
      margin: 0 10px;
    }

    .action-button {
      width: 100%;
      padding: 12px;
      font-size: 16px;
      font-weight: bold;
      background-color: #17a2b8;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    .action-button:hover {
      background-color: #117a8b;
    }

    .help-icon {
      position: fixed;
      bottom: 20px;
      right: 20px;
      font-size: 22px;
      font-weight: bold;
      background: black;
      color: white;
      border-radius: 50%;
      width: 30px;
      height: 30px;
      text-align: center;
      line-height: 30px;
      cursor: pointer;
    }
  </style>
</head>
<body>

<div class="container">
  <h2>Chữ ký điện tử</h2>

  <div class="form-group">
    <label for="realFileInput">File chứa khóa riêng tư:</label>
    <input type="file" id="privateKeyPath" placeholder="Chưa chọn file" readonly style="width: 70%; padding: 5px;">
    <input type="file" id="realFileInput" accept=".key,.pem" style="display: none;" onchange="handlePrivateKeyUpload(event)">
  </div>


  <div class="panels">
    <div class="panel">
      <label>File dữ liệu đơn hàng</label>
      <textarea id="orderData" placeholder="Nội dung dữ liệu cần ký..."></textarea>
      <button type="button">Tải file lên</button>
    </div>

    <div class="arrow">➜</div>

    <div class="panel">
      <label>Chữ ký</label>
      <textarea id="signature" placeholder="Kết quả chữ ký sẽ hiển thị tại đây..."></textarea>
      <button type="button">Lưu chữ ký</button>
    </div>
  </div>

  <button class="action-button">Ký</button>

  <div class="help-icon">?</div>
</div>

<div class="help-icon" onclick="showModal()">?</div>

<div id="helpModal" class="modal">
  <div class="modal-content">
    <span class="close" onclick="closeModal()">&times;</span>
    <h2><u>Hướng dẫn sử dụng</u></h2>
    <p><b>Bước 1:</b> Nhấn nút <b>Nhập</b> để tải file có khóa riêng tư lên.</p>
    <p><b>Bước 2:</b> Nhấn nút <b>Tải File lên</b> để tải file chứa dữ liệu đơn hàng.</p>
    <p><b>Bước 3:</b> Nhấn nút <b>Ký</b> để thực hiện ký nội dung đơn hàng.</p>
    <p><b>Bước 4:</b> Nhấn nút <b>Lưu Chữ ký</b> và chọn thư mục để thực hiện lưu file chữ ký về máy.</p>
    <p class="success">Chúc bạn thành công!</p>
  </div>
</div>

<style>
  .help-icon {
    position: fixed;
    bottom: 20px;
    right: 30px;
    background-color: black;
    color: white;
    font-size: 20px;
    font-weight: bold;
    padding: 8px 13px;
    border-radius: 50%;
    cursor: pointer;
    box-shadow: 0 2px 6px rgba(0,0,0,0.4);
    z-index: 999;
  }

  .modal {
    display: none;
    position: fixed;
    z-index: 1000;
    padding-top: 60px;
    left: 0; top: 0;
    width: 100%; height: 100%;
    overflow: auto;
    background-color: rgba(0,0,0,0.5);
  }

  .modal-content {
    background-color: #fff;
    margin: auto;
    padding: 30px;
    border-radius: 10px;
    width: 60%;
    box-shadow: 0 5px 15px rgba(0,0,0,0.3);
    font-family: 'Segoe UI', sans-serif;
    color: #333;
  }

  .modal-content h2 {
    text-align: center;
    margin-bottom: 20px;
  }

  .modal-content p {
    font-size: 16px;
    margin: 10px 0;
  }

  .modal-content .success {
    font-size: 20px;
    font-weight: bold;
    color: blue;
    text-align: center;
    margin-top: 25px;
  }

  .close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
  }

  .close:hover {
    color: black;
  }
</style>

<script>
  function showModal() {
    document.getElementById("helpModal").style.display = "block";
  }

  function closeModal() {
    document.getElementById("helpModal").style.display = "none";
  }

  window.onclick = function(event) {
    const modal = document.getElementById("helpModal");
    if (event.target === modal) {
      modal.style.display = "none";
    }
  }
</script>

<script src="js/footer.js"></script>
<script src="js/header.jsp"></script>
<script src="js/footer.js"></script>
</body>
</html>
