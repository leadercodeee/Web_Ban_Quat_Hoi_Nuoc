<%@ page import="com.example.backend.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.backend.models.ProductImage" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Document</title>
    <link rel="stylesheet" href="stylesheet/admin.css"/>
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
            <h2 style="font-size: 30px; font-weight: bold">Quản lý sản phẩm</h2>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th style="width: 20%">Tên Sản Phẩm</th>
                        <th>Mã danh Mục</th>
                        <th>Giá</th>
                        <th>Thương Hiệu</th>
                        <th>Số Lượng</th>
                        <th style="width: 30%">Mô Tả</th>
                        <th>Hình ảnh</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        // Lấy danh sách sản phẩm từ request
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        if (products != null) {
                            for (Product product : products) {
                    %>
                    <tr>
                        <td><%= product.getId() %>
                        </td>
                        <td><%= product.getName() %>
                        </td>
                        <td><%= product.getCategoryId() %>
                        </td>
                        <td><%= product.getPrice() %>
                        </td>
                        <td><%= product.getBrand() %>
                        </td>
                        <td><%= product.getStock() %>
                        </td>
                        <td><%= product.getDescription() %>
                        </td>
                        <td>
                            <div style="display: flex; flex-wrap: wrap; height: 100%; gap: 20px">
                                <%
                                    // Lấy danh sách hình ảnh của sản phẩm
                                    List<ProductImage> images = product.getImages();
                                    if (images != null && !images.isEmpty()) {
                                        for (ProductImage image : images) {
                                %>
                                <img src="<%= image.getImageUrl() %>" alt="product image"
                                     style="width: 45%;  object-fit: cover"/>
                                <%
                                    }
                                } else {
                                %>
                                <span>Không có hình ảnh</span>
                                <%
                                    }
                                %>
                            </div>

                        </td>
                        <td>
                            <div style="display: flex">
                                <a style="color: black" href="/admin/deleteProduct?id=<%= product.getId() %>"> <i
                                        style="margin: 0 10px"
                                        class="fa-solid fa-trash"></i></a>
                                <a style="color: black" href="/admin/editProduct?id=<%= product.getId() %>"> <i class="fa-solid fa-pen"></i></a>
                            </div>

                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="8">Không có sản phẩm nào để hiển thị.</td>
                    </tr>
                    <%
                        }
                    %>

                    <!-- Thêm nhiều hàng dữ liệu tại đây -->
                    </tbody>
                </table>

            </div>
            <div style=";margin-top: 40px; display: flex; justify-content: center">
                <button class="add-btn"
                        style=" margin: 0 auto ; background-color: #0f5132; border-radius: 8px; color: white">
                    <a href="/admin/addProduct"
                       style="color: white;padding: 10px 20px; display: block; text-decoration: none">Thêm sản phẩm</a>
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

    const element = document.getElementById("item-product")
    activeElement(element)
</script>
</html>
