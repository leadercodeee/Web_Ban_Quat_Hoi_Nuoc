
<%@ page import="com.example.backend.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.backend.models.Category" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <meta charset="utf-8"/>
  <meta
          name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no"
  />
  <meta name="author" content="Untree.co"/>
  <link rel="shortcut icon" href="favicon.png"/>

  <meta name="description" content=""/>
  <meta name="keywords" content="bootstrap, bootstrap4"/>

  <!-- Bootstrap CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet"/>
  <link
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
          rel="stylesheet"
  />
  <link href="css/tiny-slider.css" rel="stylesheet"/>
  <link href="css/style.css" rel="stylesheet"/>
  <title>Sản phẩm</title>
</head>

<body>
<!-- Start Header/Navigation -->
<div class="header"></div>
<!-- End Header/Navigation -->

<!-- Start Hero Section -->
<div class="hero">
  <div class="container">
    <div class="row justify-content-between">
      <div class="col-lg-5">
        <div class="intro-excerpt">
          <h1>Danh mục sản phẩm</h1>
        </div>
      </div>
      <div class="col-lg-7"></div>
    </div>
  </div>
</div>
<!-- End Hero Section -->
<section class="category">
  <!-- Container Chính -->
  <div class="container">
    <!-- Thanh Điều Hướng (Breadcrumb) -->
    <div class="category-top roww">
      <p>Trang chủ</p>
      <span>&#10230;</span>
      <p>Danh mục sản phẩm</p>
    </div>

    <!-- Nội Dung Chính -->
    <div class="row">
      <!-- Phần Trái -->
      <div class="category-left">
        <ul>
          <% List<Category> categories = (List<Category>) request.getAttribute("categories");
            for (Category category : categories){%>
          <li id="item1" class="category-left-li" style="cursor: pointer">
            <a href="shop?categoryId=<%=category.getId()%>"><%=category.getName()%></a>
          </li>
          <% }%>
        </ul>
      </div>

      <!-- Phần Phải -->
      <div class="category-right roww">
        <!-- Các Mục Trên -->
        <div class="category-right-top-item">
          <p>Hiển thị sản phẩm</p>
        </div>
        <div class="category-right-top-item">
          <button>
            <span>Bộ lọc</span>
            <i class="fa-solid fa-caret-down"></i>
          </button>
        </div>

        <div class="category-right-top-item">
          <select name="" id="">
            <option value="">Sắp xếp</option>
            <option value="">Giá cao nhất</option>
            <option value="">Giá thấp nhất</option>
            <option value="">Trả góp 0đ</option>
          </select>
        </div>
        <div class="category-right-content roww">
          <div
                  class="untree_co-section product-section before-footer-section"
          >
            <div class="container">
              <div class="row">
                <%
                  List<Product> products = (List<Product>) request.getAttribute("products");
                  if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
                %>
                <div class="col-12 col-md-4 col-lg-3 mb-5">
                  <a class="product-item" href="detail?id=<%= product.getId() %>">
                    <img
                            src="<%= product.getImages() != null && !product.getImages().isEmpty()
                                ? product.getImages().get(0).getImageUrl()
                                : "default-image.jpg" %>"
                            class="img-fluid product-thumbnail"
                            style="width: 228px; height: 228px;"
                    />
                    <h3 class="product-title"><%= product.getName() %>
                    </h3>
                    <strong class="product-price">
                      <%
                        java.text.NumberFormat currencyFormatter =
                                java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                        String formattedPrice = currencyFormatter.format(product.getPrice());
                      %>
                      <%= formattedPrice.replace("₫", "VNĐ") %>
                    </strong>
                  </a>
                </div>
                <%
                  }
                } else {
                %>
                <p>Không có sản phẩm nào để hiển thị.</p>
                <%
                  }
                %>

              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Nội Dung Phần Phải -->
    </div>

    <!-- Thanh Dưới -->
    <div class="category-right-bottom roww">
      <div class="category-right-bottom-items">
        <p>Hiển thị 2 <span>|</span> 4 sản phẩm</p>
      </div>
      <div class="category-right-bottom-items">
        <p><span>&#171;</span>1 2 3 4 5<span>&#171;</span>Trang cuối</p>
      </div>
    </div>
  </div>
</section>

<!-- Start Footer Section -->
<footer class="footer-section"></footer>
<!-- End Footer Section -->

<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/tiny-slider.js"></script>
<script src="js/custom.js"></script>
<script src="js/header.jsp"></script>
<script src="js/footer.js"></script>
</body>
</html>
