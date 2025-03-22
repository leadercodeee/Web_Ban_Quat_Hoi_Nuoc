<%@ page import="com.example.backend.models.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Quản lý Danh Mục</title>
    <link href="stylesheet/categoryManagement.css" rel="stylesheet" type="text/css" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" />
</head>
<body>
<div class="container">
    <!-- Left -->
    <div class="left">
        <!-- Sidebar -->
    </div>
    <!-- Right -->
    <div class="right">
        <div class="navbar">
            <!-- Search -->
        </div>
        <!-- Content -->
        <div class="content">
            <h2 style="font-size: 30px; font-weight: bold; margin-bottom: 40px;">Quản lý danh mục</h2>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Mã danh mục</th>
                        <th style="width: 20%">Tên danh mục</th>
                        <th>Mô tả</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("categories");
                        for (Category category : categories) {
                    %>
                    <tr>
                        <td><%= category.getId() %></td>
                        <td><%= category.getName() %></td>
                        <td><%= category.getDescription() %></td>
                        <td style="cursor: pointer">
                            <a href="/deleteCategory?id=<%= category.getId() %>" style="color: black"><i style="margin: 0 10px" class="fa-solid fa-trash"></i></a>
                            <a href="/editCategory?id=<%= category.getId() %>" style="color: black"><i class="fa-solid fa-pen"></i></a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <div style="margin-top: 40px; display: flex; justify-content: center">
                <button class="add-btn" style="background-color: #0f5132; border-radius: 8px; color: white">
                    <a href="addCategory.jsp" style="color: white;padding: 10px 20px; display: block; text-decoration: none">Thêm danh mục</a>
                </button>
            </div>
        </div>
    </div>
</div>
</body>
<script src="<%= request.getContextPath() %>/admin/common/sidebar.js"></script>
<script src="<%= request.getContextPath() %>/admin/common/navbar.js"></script>
<script type="module">
    import activeElement from "/admin/common/common.js"
    const element = document.getElementById("item-category")
    activeElement(element)
</script>
</html>
