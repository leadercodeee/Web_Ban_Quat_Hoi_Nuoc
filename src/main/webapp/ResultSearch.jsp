
<%@ page import="com.example.backend.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
            rel="stylesheet"
    />
    <link href="css/tiny-slider.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet" />
    <title>
        Furni
    </title>
</head>

<body>
<div class="header"></div>
<div class="category-right-content roww">
    <div
            style="width: 100%;display: flex; justify-content: center"
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
<footer class="footer-section"></footer>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/tiny-slider.js"></script>
<script src="js/custom.js"></script>
<script src="js/header.jsp"></script>
<script src="js/footer.js"></script>
</body>
</html>
