
<%@ page import="java.util.List" %>
<%@ page import="com.example.backend.models.Product" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta
          name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no"
  />
  <meta name="author" content="Untree.co" />
  <link rel="shortcut icon" href="favicon.png" />

  <meta name="description" content="" />
  <meta name="keywords" content="bootstrap, bootstrap4" />

  <!-- Bootstrap CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet" />
  <link
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
          rel="stylesheet"
  />
  <link href="css/tiny-slider.css" rel="stylesheet" />
  <link href="css/style.css" rel="stylesheet" />
  <title>
    Furni Free Bootstrap 5 Template for Furniture and Interior Design Websites
    by Untree.co
  </title>
</head>

<body>
<!-- Start Header/Navigation -->
<!-- Start Header/Navigation -->
<div class="header"></div>
<!-- End Header/Navigation -->

<!-- End Header/Navigation -->

<!------------Banner--------------------------->

<!------------ End Banner--------------------------->

<!-- Start Hero Section -->
<div class="hero">
  <div class="container">
    <div class="row justify-content-between">
      <div class="col-lg-5">
        <div class="intro-excerpt">
          <h1>Về chúng tôi</h1>
          <p class="mb-4">
            Furni chuyên cung cấp các sản phẩm quạt hơi nước chất lượng,
            giúp không gian của bạn luôn mát mẻ và dễ chịu. Chúng tôi cam
            kết mang đến giải pháp làm mát hiệu quả và dịch vụ khách hàng
            tận tâm.
          </p>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- End Hero Section -->

<!-- Start Product Section -->
<div class="product-section">
  <div class="container">
    <div class="row">
      <!-- Start Column 1 -->

      <div class="col-md-12 col-lg-3 mb-5 mb-lg-0">
        <h2 class="mb-4 section-title">
          Được chế tạo bằng vật liệu tuyệt vời.
        </h2>
        <p class="mb-4">
          Quạt điều hoà là một thiết bị làm mát hiệu quả được nhiều gia đình
          lựa chọn trong mùa nắng nóng. Vậy quạt điều hòa có công dụng, ưu
          điểm như thế nào? Kinh nghiệm chọn mua ra sao? Hãy cùng tìm hiểu
          thông qua đoạn thông tin bên dưới nhé!
        </p>
        <p><a href="shop" class="btn">Mua ngay</a></p>
      </div>
      <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        for (Product product: products){
      %>
      <!-- Start Column 3 -->
      <div class="col-12 col-md-4 col-lg-3 mb-5 mb-md-0">
        <a class="product-item" href="detail?id=<%= product.getId() %>">
          <img
                  src="<%= product.getImages().get(0).getImageUrl()%>"
                  class="img-fluid product-thumbnail"
                  style="width: 350px; height: 250px"
          />
          <h3 class="product-title"><%= product.getName()%></h3>
          <%
            java.text.NumberFormat currencyFormatter =
                    java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
            String formattedPrice = currencyFormatter.format(product.getPrice());
          %>
          <strong class="product-price"> <%= formattedPrice.replace("₫", "VNĐ") %></strong>

          <span class="icon-cross">
                <img src="images/cross.svg" class="img-fluid" />
              </span>
        </a>
      </div>
      <% } %>
    </div>
  </div>
</div>
<!-- End Product Section -->

<!-- Start Why Choose Us Section -->
<div class="why-choose-section">
  <div class="container">
    <div class="row justify-content-between align-items-center">
      <div class="col-lg-6">
        <h2 class="section-title">Tại sao chọn chúng tôi?</h2>
        <p>
          Chúng tôi cung cấp quạt hơi nước chất lượng cao, tiết kiệm điện
          năng và bền bỉ, giúp bạn tận hưởng không gian mát mẻ, dễ chịu suốt
          mùa hè
        </p>

        <div class="row my-5">
          <div class="col-6 col-md-6">
            <div class="feature">
              <div class="icon">
                <img src="images/truck.svg" alt="Image" class="imf-fluid" />
              </div>
              <h3>Miễn phí vận chuyển</h3>
              <p>
                Chúng tôi cam kết mang đến dịch vụ giao hàng nhanh chóng và
                miễn phí trên toàn quốc, giúp bạn nhận được sản phẩm quạt
                hơi nước ngay trong thời gian ngắn nhất mà không phải lo
                lắng về chi phí vận chuyển.
              </p>
            </div>
          </div>

          <div class="col-6 col-md-6">
            <div class="feature">
              <div class="icon">
                <img src="images/bag.svg" alt="Image" class="imf-fluid" />
              </div>
              <h3>Dễ dàng mua sắm</h3>
              <p>
                Mua sắm tại Furni thật đơn giản và tiện lợi! Với giao diện
                thân thiện, bạn chỉ cần vài bước là có thể chọn lựa và đặt
                mua quạt hơi nước yêu thích.
              </p>
            </div>
          </div>

          <div class="col-6 col-md-6">
            <div class="feature">
              <div class="icon">
                <img
                        src="images/support.svg"
                        alt="Image"
                        class="imf-fluid"
                />
              </div>
              <h3>Hỗ trợ 24/7</h3>
              <p>
                Chúng tôi cung cấp dịch vụ hỗ trợ khách hàng 24/7, luôn sẵn
                sàng giải đáp mọi thắc mắc và giúp bạn giải quyết bất kỳ vấn
                đề nào liên quan đến sản phẩm. Dù là trước, trong hoặc sau
                khi mua hàng, đội ngũ chăm sóc khách hàng của chúng tôi luôn
                ở đây để mang đến sự hỗ trợ tận tình và nhanh chóng nhất.
              </p>
            </div>
          </div>

          <div class="col-6 col-md-6">
            <div class="feature">
              <div class="icon">
                <img
                        src="images/return.svg"
                        alt="Image"
                        class="imf-fluid"
                />
              </div>
              <h3>Đổi trả dễ dàng, không phiền hà</h3>
              <p>
                Chúng tôi cam kết mang đến chính sách đổi trả dễ dàng và
                không phức tạp, giúp bạn yên tâm hơn khi mua sắm. Nếu sản
                phẩm không phù hợp hoặc gặp vấn đề, bạn có thể yêu cầu đổi
                trả trong thời gian quy định mà không gặp bất kỳ khó khăn
                nào, đảm bảo sự hài lòng tuyệt đối cho khách hàng.
              </p>
            </div>
          </div>
        </div>
      </div>

      <div class="col-lg-5">
        <div class="img-wrap">
          <img
                  src="images/why-choose-us-img.jpg"
                  alt="Image"
                  class="img-fluid"
          />
        </div>
      </div>
    </div>
  </div>
</div>
<!-- End Why Choose Us Section -->

<!-- Start We Help Section -->
<div class="we-help-section">
  <div class="container">
    <div class="row justify-content-between">
      <div class="col-lg-7 mb-5 mb-lg-0">
        <div class="imgs-grid">
          <div class="grid grid-1">
            <img src="images/img-grid-1.jpg" alt="Untree.co" />
          </div>
          <div class="grid grid-2">
            <img src="images/img-grid-2.jpg" alt="Untree.co" />
          </div>
          <div class="grid grid-3">
            <img src="images/img-grid-3.jpg" alt="Untree.co" />
          </div>
        </div>
      </div>
      <div class="col-lg-5 ps-lg-5">
        <h2 class="section-title mb-4">
          Vì sao nên mua quạt điều hòa tại Furni?
        </h2>
        <p>
          Furni tự hào là một trong những siêu thị điện máy lớn chuyên cung
          cấp các sản phẩm quạt điều hòa chính hãng đến từ các hãng uy tín
          trên thị trường với một mức giá hợp lý, phải chăng kèm theo nhiều
          khuyến mãi hấp dẫn (tuỳ thời điểm).
        </p>

        <ul class="list-unstyled custom-list my-4">
          <li>Dễ dàng di chuyển</li>
          <li>Giữ ẩm không khí tốt</li>
          <li>Cải thiện chất lượng không khí</li>
          <li>Tiết kiệm năng lượng</li>
        </ul>
        <p><a href="shop.jsp" class="btn">Mua ngay</a></p>
      </div>
    </div>
  </div>
</div>
<!-- End We Help Section -->

<!-- Start Popular Product -->
<div class="popular-product">
  <div class="container">
    <div class="row">
      <div class="col-12 col-md-6 col-lg-4 mb-4 mb-lg-0">
        <div class="product-item-sm d-flex">
          <div class="thumbnail">
            <img
                    src="https://rapido.vn/wp-content/uploads/2023/01/quat-6000D-800x600-1.png"
                    alt="Image"
                    class="img-fluid"
                    style="
                    width: 98px;
                    height: 98px;
                    object-fit: cover;
                    border-radius: 20px;
                    margin-top: 45px;
                    margin-left: 10px;
                  "
            />
          </div>
          <div class="pt-3">
            <h3>Quạt điều hòa Everest 6000D</h3>
            <p>
              Nhập mã VNPAYTGDD5 giảm từ 50,000đ đến 200,000đ (áp dụng tùy
              giá trị đơn hàng) khi thanh toán qua VNPAY-QR.
            </p>
            <p>
              Cơ hội nhận ngay Phiếu mua hàng trị giá 1,000,000đ khi tham
              gia Trả góp Duyệt qua điện thoại, giao hàng tận nhà.
            </p>
          </div>
        </div>
      </div>

      <div class="col-12 col-md-6 col-lg-4 mb-4 mb-lg-0">
        <div class="product-item-sm d-flex">
          <div class="thumbnail">
            <img
                    src="https://product.hstatic.net/200000227819/product/dka-06000a-min-389_87d1b9846bc547e79fbe3aeaed78a854.png"
                    alt="Image"
                    class="img-fluid"
                    style="
                    width: 98px;
                    height: 98px;
                    object-fit: cover;
                    border-radius: 20px;
                    margin-top: 45px;
                    margin-left: 10px;
                  "
            />
          </div>
          <div class="pt-3">
            <h3>QUẠT HƠI NƯỚC DAIKIOSAN DKA-06000A</h3>
            <p>
              Nhập mã VNPAYTGDD5 giảm từ 50,000đ đến 200,000đ (áp dụng tùy
              giá trị đơn hàng) khi thanh toán qua VNPAY-QR.
            </p>
            <p>
              Cơ hội nhận ngay Phiếu mua hàng trị giá 1,000,000đ khi tham
              gia Trả góp Duyệt qua điện thoại, giao hàng tận nhà.
            </p>
          </div>
        </div>
      </div>

      <div class="col-12 col-md-6 col-lg-4 mb-4 mb-lg-0">
        <div class="product-item-sm d-flex">
          <div class="thumbnail">
            <img
                    src="https://product.hstatic.net/200000041518/product/5440_d1138937de4944daa283871aa1e912f3_grande.png"
                    alt="Image"
                    class="img-fluid"
                    style="
                    width: 98px;
                    height: 98px;
                    object-fit: cover;
                    border-radius: 20px;
                    margin-top: 40px;
                    margin-left: 10px;
                  "
            />
          </div>
          <div class="pt-3">
            <h3>Quạt Hơi Nước Daikiosan DM107</h3>
            <p>
              Nhập mã VNPAYTGDD5 giảm từ 50,000đ đến 200,000đ (áp dụng tùy
              giá trị đơn hàng) khi thanh toán qua VNPAY-QR.
            </p>
            <p>
              Cơ hội nhận ngay Phiếu mua hàng trị giá 1,000,000đ khi tham
              gia Trả góp Duyệt qua điện thoại, giao hàng tận nhà.
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- End Popular Product -->

<!-- Start Footer Section -->
<footer class="footer-section">
  <div class="container relative">
    <div class="row">
      <div class="col-lg-8">
        <div class="subscription-form">
          <h3 class="d-flex align-items-center">
                <span class="me-1"
                ><img
                        src="images/envelope-outline.svg"
                        alt="Image"
                        class="img-fluid" /></span
                ><span>Subscribe to Newsletter</span>
          </h3>

          <form action="#" class="row g-3">
            <div class="col-auto">
              <input
                      type="text"
                      class="form-control"
                      placeholder="Enter your name"
              />
            </div>
            <div class="col-auto">
              <input
                      type="email"
                      class="form-control"
                      placeholder="Enter your email"
              />
            </div>
            <div class="col-auto">
              <button class="btn btn-primary">
                <span class="fa fa-paper-plane"></span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <div class="row g-5 mb-5">
      <div class="col-lg-4">
        <div class="mb-4 footer-logo-wrap">
          <a href="#" class="footer-logo">Furni<span>.</span></a>
        </div>
        <p class="mb-4">
          Donec facilisis quam ut purus rutrum lobortis. Donec vitae odio
          quis nisl dapibus malesuada. Nullam ac aliquet velit. Aliquam
          vulputate velit imperdiet dolor tempor tristique. Pellentesque
          habitant
        </p>

        <ul class="list-unstyled custom-social">
          <li>
            <a href="#"><span class="fa fa-brands fa-facebook-f"></span></a>
          </li>
          <li>
            <a href="#"><span class="fa fa-brands fa-twitter"></span></a>
          </li>
          <li>
            <a href="#"><span class="fa fa-brands fa-instagram"></span></a>
          </li>
          <li>
            <a href="#"><span class="fa fa-brands fa-linkedin"></span></a>
          </li>
        </ul>
      </div>

      <div class="col-lg-8">
        <div class="row links-wrap">
          <div class="col-6 col-sm-6 col-md-3">
            <ul class="list-unstyled">
              <li><a href="#">About us</a></li>
              <li><a href="#">Services</a></li>
              <li><a href="#">Blog</a></li>
              <li><a href="#">Contact us</a></li>
            </ul>
          </div>

          <div class="col-6 col-sm-6 col-md-3">
            <ul class="list-unstyled">
              <li><a href="#">Support</a></li>
              <li><a href="#">Knowledge base</a></li>
              <li><a href="#">Live chat</a></li>
            </ul>
          </div>

          <div class="col-6 col-sm-6 col-md-3">
            <ul class="list-unstyled">
              <li><a href="#">Jobs</a></li>
              <li><a href="#">Our team</a></li>
              <li><a href="#">Leadership</a></li>
              <li><a href="#">Privacy Policy</a></li>
            </ul>
          </div>

          <div class="col-6 col-sm-6 col-md-3">
            <ul class="list-unstyled">
              <li><a href="#">Nordic Chair</a></li>
              <li><a href="#">Kruzo Aero</a></li>
              <li><a href="#">Ergonomic Chair</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <div class="border-top copyright">
      <div class="row pt-4">
        <div class="col-lg-6">
          <p class="mb-2 text-center text-lg-start">
            Copyright &copy;
            <script>
              document.write(new Date().getFullYear())
            </script>
            . All Rights Reserved. &mdash; Designed with love by
            <a href="https://untree.co">Untree.co</a> Distributed By
            <a hreff="https://themewagon.com">ThemeWagon</a>
            <!-- License information: https://untree.co/license/ -->
          </p>
        </div>

        <div class="col-lg-6 text-center text-lg-end">
          <ul class="list-unstyled d-inline-flex ms-auto">
            <li class="me-4"><a href="#">Terms &amp; Conditions</a></li>
            <li><a href="#">Privacy Policy</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</footer>
<!-- End Footer Section -->

<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/tiny-slider.js"></script>
<script src="js/custom.js"></script>
</body>
<script src="js/header.jsp"></script>
<script src="js/footer.js"></script>
</html>
