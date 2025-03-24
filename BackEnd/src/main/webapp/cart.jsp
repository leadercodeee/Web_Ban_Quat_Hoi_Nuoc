
<%@ page import="com.example.backend.models.Cart" %>
<%@ page import="com.example.backend.models.CartItem" %>
<%@ page import="java.util.List" %>
<!-- /*
* Bootstrap 5
* Template Name: Furni
* Template Author: Untree.co
* Template URI: https://untree.co/
* License: https://creativecommons.org/licenses/by/3.0/
*/ -->
<%@ page contentType="text/html; charset=UTF-8" %>
<!--cmt....-->
<!DOCTYPE html>
<html lang="en">
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
  <link rel="stylesheet" href="css/cart.css"/>
  <link href="css/bootstrap.min.css" rel="stylesheet"/>
  <link
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
          rel="stylesheet"
  />
  <link href="css/tiny-slider.css" rel="stylesheet"/>
  <link href="css/style.css" rel="stylesheet"/>
  <title>Trang giỏ hàng</title>
</head>

<body>
<!-- Start Header/Navigation -->
<div class="header"></div>
<!-- End Header/Navigation -->
<!--cmttttttttt-->
<!-- Start Hero Section -->
<div class="hero">
  <div class="container">
    <div class="row justify-content-between">
      <div class="col-lg-5">
        <div class="intro-excerpt">
          <h1>Cart</h1>
        </div>
      </div>
      <div class="col-lg-7"></div>
    </div>
  </div>
</div>
<!-- End Hero Section -->
<!-----------------cart---------------------->

<section class="cart">
  <div class="container">
    <div class="cart-top-wrap">
      <div class="cart-top">
        <div class="cart-top-cart cart-top-item">
          <i class="fa-solid fa-cart-shopping"></i>
        </div>

        <div class="cart-top-address cart-top-item">
          <i class="fa-solid fa-location-dot"></i>
        </div>

        <div class="cart-top-payment cart-top-item">
          <i class="fa-solid fa-money-check"></i>
        </div>
      </div>
    </div>
  </div>

  <div class="container">
    <div class="cart-content" style="display: flex; gap: 20px">
      <!-- Cột trái -->
      <div class="cart-content-left" style="flex: 1; max-width: 70%">
        <%
          // Lấy giỏ hàng từ session
          Cart cart = (Cart) session.getAttribute("cart");

          if (cart != null && !cart.getItems().isEmpty()) {
        %>
        <form action="update-cart" method="post">

          <table>
            <tr>
              <th class="product-name">Ảnh</th>
              <th class="product-name">Tên Sản Phẩm</th>
              <th class="product-price">Giá</th>
              <th class="product-quantity">Số Lượng</th>
              <th class="product-total">Xóa</th>
            </tr>
            <%
              for (CartItem item : cart.getItems().values()) {
            %>
            <input type="hidden" name="productId" value="<%=item.getProduct().getId()%>">
            <tr>
              <td>
                <img src="<%=item.getProduct().getImages().get(0).getImageUrl()%>"
                     alt="product_img"
                />
              </td>
              <td>
                <p><%=item.getProduct().getName()%></p>
              </td>
              <%
                java.text.NumberFormat currencyFormatter =
                        java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                String formattedPrice = currencyFormatter.format(item.getProduct().getPrice());

              %>
              <td><p><%=formattedPrice%>
              </p></td>


              <td>
                <input type="number" name="quantity_<%= item.getProduct().getId() %>" value="<%= item.getQuantity() %>" />
              </td>
              <td><a href="/remove-from-cart?productId=<%=item.getProduct().getId()%>">x</a></td>
            </tr>
            <%
              }
            %>
          </table>
          <button type="submit">Cập nhật giỏ hàng</button>
        </form>
        <% }%>
      </div>

      <!-- Cột phải -->
      <div class="cart-content-right" style="flex: 1; max-width: 30%">
        <%
          if (cart != null && !cart.getItems().isEmpty()) {
        %>
        <table>
          <tr>
            <th colspan="2">TỔNG TIỀN GIỎ HÀNG</th>
          </tr>
          <tr>
            <td>TỔNG SẢN PHẨM</td>
            <td><%=cart.getItems().size()%></td>
          </tr>
          <tr>
            <td>TỔNG TIỀN HÀNG</td>
            <%java.text.NumberFormat currencyFormatter =
                    java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
              String formattedTotalPrice = currencyFormatter.format(cart.getTotalPrice());%>
            <td>
              <p><%=formattedTotalPrice%><sup>VNĐ</sup></p>
            </td>
          </tr>
          <tr>
            <td>TẠM TÍNH</td>
            <td>
              <p style="color: black; font-weight: bold">
                <%=formattedTotalPrice%><sup>VNĐ</sup>
              </p>
            </td>
          </tr>
        </table>
        <%}%>


        <div class="cart-content-right-button">
          <button><a href="shop?categoryId=3">TIẾP TỤC MUA HÀNG</a></button>
          <button>
            <a
                    style="color: white; display: block; width: 100%"
                    href="delivery.jsp"
            >THANH TOÁN</a
            >
          </button>
        </div>
      </div>
    </div>
  </div>
</section>

<!-----------------End Cart---------------------->

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
