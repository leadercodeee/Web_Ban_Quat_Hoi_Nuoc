<%@ page import="com.example.backend.models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="stylesheet/userManagement.css">
    <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
    integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
    crossorigin="anonymous"
    referrerpolicy="no-referrer"
  />
  </head>
  <body>
    <div class="container">
      <!-- Left -->
      <div class="left">
        <!-- Sidebar -->
        <!-- logo -->
      </div>
      <!-- Right -->
      <div class="right">
        <!-- Navbar -->
        <div class="navbar">
          <!-- Search -->
        </div>
        <!-- Content -->
        <div class="content">
          <h2 style="font-size: 30px; font-weight: bold; margin-bottom: 40px;">Quản lý người dùng</h2>
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th style="width: 20%">Họ và tên</th>
                  <th>Tên tài khoản</th>
                  <th>Email</th>
                  <th>Số điện thoại</th>
                  <th>Ngày tháng năm sinh</th>
                  <th style="width: 20%">Địa chỉ</th>
                  <th>Thao tác</th>
                </tr>
              </thead>
              <tbody>
              <%
                List<User> users = (List<User>) request.getAttribute("users");
                if (users != null) {
                  for (User user : users) {
              %>
              <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getFullName() %></td>
                <td><%= user.getUsername() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getPhone() %></td>
                <td class="dob"><%= user.getDob() %></td>
                <td><%= user.getAddress() %></td>
                <td style="cursor: pointer">
                  <a style="color: black" href="/admin/deleteUser?id=<%= user.getId() %>">
                    <i style="margin: 0 10px" class="fa-solid fa-trash"></i>
                  </a>
                  <a style="color: black" href="/admin/editUser?id=<%= user.getId() %>">
                    <i class="fa-solid fa-pen"></i>
                  </a>
                </td>
              </tr>
              <%
                  }
                }
              %>




                <!-- Thêm nhiều hàng dữ liệu tại đây -->
              </tbody>
            </table>
          </div>
          <div style=";margin-top: 40px; display: flex; justify-content: center">
            <button class="add-btn"
                    style=" margin: 0 auto ; background-color: #0f5132; border-radius: 8px; color: white">
              <a href="/admin/addUser"
                 style="color: white;padding: 10px 20px; display: block; text-decoration: none">Thêm người dùng</a>
            </button>
          </div>
        </div>
      </div>
      </div>
  </body>
  <script src="<%= request.getContextPath() %>/admin/common/sidebar.js?v=1.0"></script>
  <script src="<%= request.getContextPath() %>/admin/common/navbar.js?v=1.0"></script>
  <script type="module">
    import activeElement from "./common/common.js"
    const element = document.getElementById("item-user")
    activeElement(element)
    function formatDate(dateString) {
      const date = new Date(dateString);
      const day = String(date.getDate()).padStart(2, '0');
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const year = date.getFullYear();
      return day + '/' + month + '/' + year;
    }
    window.onload = function() {
      const dobCells = document.querySelectorAll('.dob');
      dobCells.forEach(function(cell) {
        const formattedDate = formatDate(cell.textContent);
        cell.textContent = formattedDate;
      });
    };
  </script>
</html>
