<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!-- /*
* Bootstrap 5
* Template Name: Furni
* Template Author: Untree.co
* Template URI: https://untree.co/
* License: https://creativecommons.org/licenses/by/3.0/
*/ -->
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
    <link rel="stylesheet" href="css/payment.css" />
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
      rel="stylesheet"
    />
    <link href="css/tiny-slider.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet" />
    <title>Trang thanh toán</title>
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
              <h1>Payment</h1>
            </div>
          </div>
          <div class="col-lg-7"></div>
        </div>
      </div>
    </div>
    <!-- End Hero Section -->
    <!-----------------Payment---------------------->
    <section class="payment">
      <div class="container">
        <div class="payment-top-wrap">
          <div class="payment-top">
            <div class="payment-top-delivery payment-top-item">
              <i class="fa-solid fa-cart-shopping"></i>
            </div>

            <div class="payment-top-address payment-top-item">
              <i class="fa-solid fa-location-dot"></i>
            </div>

            <div class="payment-top-payment payment-top-item">
              <i class="fa-solid fa-money-check"></i>
            </div>
          </div>
        </div>
      </div>

      <div class="container">
        <div class="payment-content row">
          <!-- Left section -->
          <div class="payment-content-left col-md-8">
            <div class="payment-content-left-method-delivery">
              <p style="font-weight: bold">Phương thức giao hàng</p>
              <div class="payment-content-left-method-delivery-item">
                <input checked type="radio" />
                <label style="font-size: 18px" for=""
                  >Giao hàng chuyển phát nhanh</label
                >
              </div>
            </div>
            <div class="payment-content-left-method-payment">
              <p style="font-weight: bold">Phương thức thanh toán</p>
              <p>
                Mọi giao dịch đều được bảo mật và mã hóa. Thông tin thẻ tín dụng
                sẽ không bao giờ được lưu lại.
              </p>

              <div class="payment-content-left-method-payment-item">
                <input name="method-payment" type="radio" />
                <label style="font-size: 18px" for=""
                  >Thanh toán bằng thẻ tín dụng (OnePay)</label
                >
              </div>
              <div class="payment-content-left-method-payment-item-img">
                <img src="images/payment/visa.jpg" alt="" />
              </div>

              <div class="payment-content-left-method-payment-item">
                <input checked name="method-payment" type="radio" />
                <label for="" style="font-size: 18px"
                  >Thanh toán bằng thẻ ATM (OnePay)</label
                >
              </div>
              <div class="payment-content-left-method-payment-item-img">
                <img src="images/payment/atm.jpg" alt="" />
              </div>

              <div class="payment-content-left-method-payment-item">
                <input name="method-payment" type="radio" />
                <label for="" style="font-size: 18px">Thanh toán Momo</label>
              </div>
              <div class="payment-content-left-method-payment-item-img">
                <img src="images/payment/momo.jpg" alt="" />
              </div>

              <div class="payment-content-left-method-payment-item">
                <input name="method-payment" type="radio" />
                <label style="font-size: 18px" for="">Thu tiền tận nơi</label>
              </div>
            </div>
          </div>

          <!-- Right section -->
          <div class="payment-content-right col-md-4">
            <!-- <div class="payment-content-right-button">
              <input type="text" placeholder="Mã giảm giá/Quà tặng" />
              <button><i class="fa-solid fa-check"></i></button>
            </div> -->

            <!-- <div class="payment-content-right-button">
              <input type="text" placeholder="Mã cộng tác viên" />
              <button><i class="fa-solid fa-check"></i></button>
            </div> -->

            <!-- <div class="payment-content-right-mnv">
              <select name="" id="">
                <option value="">Chọn mã nhân viên thân thiết</option>
                <option>D123</option>
                <option>D111</option>
                <option>D222</option>
                <option>D333</option>
                <option>D444</option>
              </select>
            </div> -->

            <div class="payment-content-right-bottom">
              <table>
                <tr>
                  <th>Tên sản phẩm</th>
                  <th>Giảm giá</th>
                  <th>Số lượng</th>
                  <th>Thành tiền</th>
                </tr>

                <tr>
                  <td>Quạt hơi nước giá rẻ</td>
                  <td>-20%</td>
                  <td>1</td>
                  <td>500.000<sup>VNĐ</sup></td>
                </tr>

                <tr>
                  <td>Quạt hơi nước giá đắt</td>
                  <td>-20%</td>
                  <td>1</td>
                  <td>1.000.000<sup>VNĐ</sup></td>
                </tr>

                <tr>
                  <td style="font-weight: bold; font-size: 18px" colspan="3">
                    Tổng
                  </td>
                  <td style="font-weight: bold; font-size: 18px">
                    <p>1.000.000<sup>VNĐ</sup></p>
                  </td>
                </tr>

                <tr>
                  <td style="font-weight: bold; font-size: 18px" colspan="3">
                    Giao hàng chuyển phát nhanh
                  </td>
                  <td style="font-weight: bold; font-size: 18px">
                    <p>38.000<sup>VNĐ</sup></p>
                  </td>
                </tr>

                <tr>
                  <td style="font-weight: bold; font-size: 18px" colspan="3">
                    Tổng tiền hàng
                  </td>
                  <td style="font-weight: bold; font-size: 18px">
                    <p>1.038.000<sup>VNĐ</sup></p>
                  </td>
                </tr>
              </table>
            </div>
            <div></div>
          </div>
        </div>

        <div class="payment-content-right-payment">
          <button
            style="color: white; background-color: black; border-color: black"
          >
            Thanh toán
          </button>
        </div>
      </div>
    </section>

    <!-----------------End Payment---------------------->

    <!-- Start Footer Section -->
    <footer class="footer-section"></footer>
    <!-- End Footer Section -->

    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/tiny-slider.js"></script>
    <script src="js/custom.js"></script>
    <script src="js/header.js"></script>
    <script src="js/footer.js"></script>
  </body>
</html>
