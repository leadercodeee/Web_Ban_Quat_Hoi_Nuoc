<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.sql.*, com.example.backend.models.Order, com.example.backend.models.CartItem, com.example.backend.DAO.OrderDAO" %>
<%@ page import="java.sql.Date" %>

<%
    // Lấy userId từ session
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Lấy dữ liệu từ form
    String country = request.getParameter("c_country");
    String fname = request.getParameter("c_fname");
    String lname = request.getParameter("c_lname");
    String companyName = request.getParameter("c_companyname");
    String address = request.getParameter("c_address");
    String stateCountry = request.getParameter("c_state_country");
    String postalZip = request.getParameter("c_postal_zip");
    String email = request.getParameter("c_email_address");
    String phone = request.getParameter("c_phone");
    String createAccount = request.getParameter("create_account");
    String accountPassword = request.getParameter("c_account_password");
    String paymentMethod = request.getParameter("payment_method"); // Từ form

    // Kiểm tra các trường bắt buộc
    List<String> errors = new ArrayList<>();

    if (country == null || country.trim().isEmpty()) errors.add("Country is required.");
    if (fname == null || fname.trim().isEmpty()) errors.add("First name is required.");
    if (lname == null || lname.trim().isEmpty()) errors.add("Last name is required.");
    if (address == null || address.trim().isEmpty()) errors.add("Address is required.");
    if (stateCountry == null || stateCountry.trim().isEmpty()) errors.add("State / Country is required.");
    if (postalZip == null || postalZip.trim().isEmpty()) errors.add("Postal / Zip is required.");
    if (email == null || email.trim().isEmpty()) errors.add("Email address is required.");
    if (phone == null || phone.trim().isEmpty()) errors.add("Phone number is required.");
    if ("on".equals(createAccount) && (accountPassword == null || accountPassword.trim().isEmpty())) {
        errors.add("Password is required to create an account.");
    }

    // Lấy giỏ hàng từ session và tính tổng tiền
    double totalAmount = 0;
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
        errors.add("Your cart is empty.");
    } else {
        for (CartItem item : cart) {
            totalAmount += item.getQuantity() * item.getProduct().getPrice();
        }
    }

    // Chuẩn bị ngày giao hàng dự kiến
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, 5); // giao hàng sau 5 ngày
    Date deliveryDate = new Date(calendar.getTimeInMillis());

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Checkout Result</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<div class="container my-5">
    <h1>Order Processing Result</h1>
    <hr/>

    <%
        if (!errors.isEmpty()) {
    %>
    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">Please fix the following errors:</h4>
        <ul>
            <% for (String err : errors) { %>
            <li><%= err %></li>
            <% } %>
        </ul>
        <a href="javascript:history.back()" class="btn btn-secondary mt-3">Go Back</a>
    </div>
    <%
    } else {
        // Tạo đối tượng Order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(address + ", " + stateCountry + ", " + postalZip);
        order.setPaymentMethod(paymentMethod != null ? paymentMethod : "COD");
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setDeliveryDate(deliveryDate);
        order.setStatus("Processing");
        order.setSignature(null);

        // Lưu order
        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.saveOrder(order);

        // Xóa giỏ hàng sau khi đặt hàng
        session.removeAttribute("cart");
    %>

    <div class="alert alert-success" role="alert">
        <h4 class="alert-heading">Thank you for your order, <%= fname %>!</h4>
        <p>Your order has been received and is being processed. Your Order ID is <strong>#<%= orderId %></strong>.</p>
        <hr />
        <h5>Order Summary:</h5>
        <ul>
            <li><strong>Name:</strong> <%= fname %> <%= lname %></li>
            <li><strong>Address:</strong> <%= order.getShippingAddress() %></li>
            <li><strong>Email:</strong> <%= email %></li>
            <li><strong>Phone:</strong> <%= phone %></li>
            <li><strong>Total:</strong> $<%= String.format("%.2f", totalAmount) %></li>
            <li><strong>Payment Method:</strong> <%= order.getPaymentMethod() %></li>
        </ul>
        <a href="index.jsp" class="btn btn-primary mt-3">Return to Home</a>
    </div>
    <%
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
