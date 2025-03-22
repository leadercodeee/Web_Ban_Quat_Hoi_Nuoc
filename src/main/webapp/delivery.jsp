
<%@ page import="com.example.backend.models.Cart" %>
<%@ page import="com.example.backend.models.CartItem" %>
<%@ page import="com.example.backend.models.Product" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
  <meta name="author" content="Untree.co"/>
  <link rel="shortcut icon" href="favicon.png"/>
  <link href="css/bootstrap.min.css" rel="stylesheet"/>
  <link rel="stylesheet" href="css/delivery.css"/>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet"/>
  <link href="css/tiny-slider.css" rel="stylesheet"/>
  <link href="css/style.css" rel="stylesheet"/>
  <title>Trang giao hàng</title>
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
          <h1>Delivery</h1>
        </div>
      </div>
      <div class="col-lg-7"></div>
    </div>
  </div>
</div>

<!-- Start Delivery Section -->
<section class="delivery">
  <div class="delivery-content">
    <div class="delivery-top-wrap">
      <div class="delivery-top">
        <div class="delivery-top-delivery delivery-top-item">
          <i class="fa-solid fa-cart-shopping"></i>
        </div>

        <div class="delivery-top-address delivery-top-item">
          <i class="fa-solid fa-location-dot"></i>
        </div>

        <div class="delivery-top-payment delivery-top-item">
          <i class="fa-solid fa-money-check"></i>
        </div>
      </div>
    </div>
  </div>

  <div class="container">
    <div class="delivery" style="display: flex; gap: 20px">
      <!-- Cột phải: Hiển thị sản phẩm trong giỏ hàng -->
      <div class="delivery-content-right" style="flex: 1; max-width: 100%">
        <form action="placeOrder" method="POST">
          <table>
            <tr>
              <th>Mã sản phẩm</th>
              <th>Tên sản phẩm</th>
              <th>Số lượng</th>
              <th>Thành tiền</th>
            </tr>
            <%
              Cart cart = (Cart) session.getAttribute("cart");
              double totalAmount = 0;

              if (cart != null && !cart.getItems().isEmpty()) {
                for (CartItem item : cart.getItems().values()) {
                  double itemTotal = item.getProduct().getPrice() * item.getQuantity();
                  totalAmount += itemTotal;
            %>
            <tr>
              <td><%= item.getProduct().getId()%>
              </td>
              <td><%= item.getProduct().getName() %>
              </td>
              <td><%= item.getQuantity() %>
              </td>
              <%
                java.text.NumberFormat currencyFormatter =
                        java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                String formattedTotalAmount = currencyFormatter.format(totalAmount);

              %>
              <td><%= formattedTotalAmount %> VNĐ</td>
            </tr>
            <%
              }
            } else {
            %>
            <tr>
              <td colspan="4">Giỏ hàng trống</td>
            </tr>
            <%
              }
            %>
            <%
              java.text.NumberFormat currencyFormatter =
                      java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
              String formattedTotalAmount = currencyFormatter.format(totalAmount);

            %>
            <tr>
              <td style="font-weight: bold" colspan="3">Tổng</td>
              <td style="font-weight: bold">
                <p><%= formattedTotalAmount %> VNĐ</p>
              </td>
            </tr>

            <tr>
              <td style="font-weight: bold" colspan="3">Thuế VAT</td>
              <%String formattedVAT = currencyFormatter.format(totalAmount * 0.001);%>
              <td style="font-weight: bold">
                <p><%= formattedVAT %>
                </p>
              </td>
            </tr>

            <tr>
              <td style="font-weight: bold" colspan="3">Tổng tiền hàng</td>
              <td style="font-weight: bold">
                <p><%=currencyFormatter.format(totalAmount - (totalAmount * 0.001)) %> VNĐ</p>
              </td>
            </tr>
          </table>

          <!-- Shipping Address -->
          <label for="shippingAddress">Địa chỉ giao hàng:</label>
          <input type="text" id="shippingAddress" name="shippingAddress" required/>

          <!-- Payment Method -->
          <label for="paymentMethod">Phương thức thanh toán:</label>
          <select id="paymentMethod" name="paymentMethod" required>
            <option value="Credit Card">Credit Card</option>
            <option value="Cash on Delivery">Cash on Delivery</option>
          </select>

          <!-- Hidden Total Amount Field -->
          <input type="hidden" name="totalAmount" value="<%= totalAmount * 1.1 %>"/>

          <!-- Buttons -->
          <div class="delivery-content-left-button now">
            <a href="cart.jsp"><span>&#171;</span> Quay lại giỏ hàng</a>
            <button type="submit" style="font-weight: bold;">
              THANH TOÁN VÀ GIAO HÀNG
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</section>

<!-- End Delivery Section -->

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
