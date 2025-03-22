const footer = document.querySelector(".footer-section")
footer.innerHTML = `   <div class="container relative">
        <div class="row">
          <div class="col-lg-8">
            <div class="subscription-form">
              <h3 class="d-flex align-items-center">
                <span class="me-1"
                  ><img
                    src="images/envelope-outline.svg"
                    alt="Image"
                    class="img-fluid" /></span
                ><span>Đăng ký nhận thư</span>
              </h3>

              <form action="#" class="row g-3">
                <div class="col-auto">
                  <input
                    type="text"
                    class="form-control"
                    placeholder="Họ và tên"
                  />
                </div>
                <div class="col-auto">
                  <input
                    type="email"
                    class="form-control"
                    placeholder="Địa chỉ email"
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
            Chúng tôi cung cấp những chiếc quạt hơi nước tiết kiệm năng lượng và hiệu quả, giúp bạn tận hưởng không khí mát mẻ và trong lành trong những ngày hè oi ả. Với thiết kế hiện đại, đa dạng mẫu mã và giá cả hợp lý, Funi cam kết mang đến cho bạn những sản phẩm tốt nhất, phục vụ nhu cầu làm mát mọi gia đình và văn phòng.
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
                  <li><a href="about.jsp">Về chúng tôi</a></li>
                  <li><a href="services.jsp">Dịch vụ</a></li>
                  <li><a href="contact.jsp">Liên hệ</a></li>
                </ul>
              </div>

              <div class="col-6 col-sm-6 col-md-3">
                <ul class="list-unstyled">
                  <li><a href="support.jsp">Hỗ trợ</a></li>
                  <li><a href="knowledgebase.jsp">Kiến thức cơ bản</a></li>
                </ul>
              </div>

              <div class="col-6 col-sm-6 col-md-3">
                <ul class="list-unstyled">
                  <li><a href="privacy_policy.jsp">Chính sách bảo mật</a></li>
                     <li><a href="terms.jsp">Điều khoản sử dụng</a></li>
                </ul>
              </div>

        
            </div>
          </div>
        </div>

        <div class="border-top copyright">
          <div class="row pt-4">
            <div class="col-lg-6">
              <p class="mb-2 text-center text-lg-start">
              © 2024 Furni. Bảo lưu tất cả quyền sở hữu. Được thiết kế và phát triển bởi Furni.
              </p>
            </div>

            <div class="col-lg-6 text-center text-lg-end">
              <ul class="list-unstyled d-inline-flex ms-auto">
                <li class="me-4"><a href="privacy_policy.jsp">Điều khoản sử dụng</a></li>
                <li><a href="terms.jsp">Chính sách bảo mật</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>`
