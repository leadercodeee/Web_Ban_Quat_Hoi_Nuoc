
<%@ page import="com.example.backend.models.CartItem" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.backend.models.Product" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.backend.models.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="author" content="Untree.co"/>
    <link rel="shortcut icon" href="favicon.png"/>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/style.css" rel="stylesheet"/>
    <title>Order Confirmation</title>
</head>

<body>
<div class="container">
    <h2 class="mt-5">Order Confirmation</h2>
    <div class="order-details">
        <p><strong>Địa chỉ giao hàng:</strong> ${order.shippingAddress}</p>
        <p><strong>Phương thức thanh toán:</strong> ${order.paymentMethod}</p>
        <%
            Order order = (Order) request.getAttribute("order");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedOrderDate = dateFormat.format(order.getOrderDate());
            String formattedDeliveryDate = dateFormat.format(order.getDeliveryDate());
            java.text.NumberFormat currencyFormatter =
                    java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
        %>
        <p><strong>Tổng tiền:</strong> <%=currencyFormatter.format(order.getTotalAmount())%></p>

        <p><strong>Ngày đặt hàng:</strong> <%=formattedOrderDate%>
        </p>
        <p><strong>Ngày giao hàng:</strong> <%=formattedDeliveryDate%>
        </p>
    </div>

    <h4>Sản phẩm trong đơn hàng của bạn:</h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Tên sản phẩm</th>
            <th>Số lượng</th>
            <th>Giá</th>
            <th>Thành tiền</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Hiển thị các sản phẩm trong giỏ hàng
            Map<Integer, CartItem> cartItems = (Map<Integer, CartItem>) request.getAttribute("cartItems");

            for (CartItem cartItem : cartItems.values()) {
                Product product = cartItem.getProduct();
                double totalPrice = cartItem.getQuantity() * product.getPrice();
        %>
        <tr>
            <td><%= product.getName() %>
            </td>
            <td><%= cartItem.getQuantity() %>
            </td>
            <%
                String formattedPrice = currencyFormatter.format(product.getPrice());
            %>
            <td><%= formattedPrice %> </td>
            <td><%= currencyFormatter.format(totalPrice) %> </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="mt-5">
        <a href="/home" class="btn btn-primary">Continue Shopping</a>
    </div>
</div>
</body>
</html>
