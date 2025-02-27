<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>

    <!===========================FONT AWESOME=========================================>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <!===========================CSS=========================================>
    <link rel ="stylesheet" href="css/login.css">

</head>
<body>
<!--=============== LOGIN IMAGE ===============-->
<svg class="login__blob" viewBox="0 0 500 500" xmlns="http://www.w3.org/2000/svg">
    <mask id="mask0" mask-type="alpha">
        <path d="M342.407 73.6315C388.53 56.4007 394.378 17.3643 391.538
            0H566V840H0C14.5385 834.991 100.266 804.436 77.2046 707.263C49.6393
            591.11 115.306 518.927 176.468 488.873C363.385 397.026 156.98 302.824
            167.945 179.32C173.46 117.209 284.755 95.1699 342.407 73.6315Z"/>
    </mask>

    <g mask="url(#mask0)">
        <path d="M342.407 73.6315C388.53 56.4007 394.378 17.3643 391.538
            0H566V840H0C14.5385 834.991 100.266 804.436 77.2046 707.263C49.6393
            591.11 115.306 518.927 176.468 488.873C363.385 397.026 156.98 302.824
            167.945 179.32C173.46 117.209 284.755 95.1699 342.407 73.6315Z"/>

        <!-- Insert your image (recommended size: 1000 x 1200) -->
        <image class="login__img" href="images/bg-img.jpg"/>
    </g>
</svg>
<!--=============== LOGIN ===============-->
<div class="login container grid" id="loginAccessRegister">
    <!--===== LOGIN ACCESS =====-->
    <div class="login__access">

        <h1 class="login__title">Đăng nhập</h1>
        <% String message = (String) request.getAttribute("message"); %>
        <% if (message != null) { %>
        <div style="color: red; margin-bottom: 20px " class="alert alert-success"><%= message %>
        </div>
        <% } %>
        <div class="login__area">
            <form action="login" method="post" class="login__form">
                <div class="login__content grid">
                    <div class="login__box">
                        <input type="email" id="email" name="email" required placeholder=" " class="login__input">
                        <label for="email" class="login__label">Email</label>

                        <i class="ri-mail-fill login__icon"></i>
                    </div>

                    <div class="login__box">
                        <input type="password" id="password" name="password" required placeholder=" " class="login__input">
                        <label for="password" class="login__label">Mật khẩu</label>

                        <i class="ri-eye-off-fill login__icon login__password" id="loginPassword"></i>
                    </div>
                </div>

                <a href="/forgot-password" class="login__forgot">Quên mật khẩu?</a>

                <button type="submit" class="login__button">Đăng nhập</button>
            </form>

            <div class="login__social">
                <p class="login__social-title">Đăng nhập với?</p>

                <div class="login__social-links">
                    <a href="#" class="login__social-link">
                        <img src="images/icon-google.svg" alt="image" class="login__social-img">
                    </a>

                    <a href="#" class="login__social-link">
                        <img src="images/icon-facebook.svg" alt="image" class="login__social-img">
                    </a>

                    <a href="#" class="login__social-link">
                        <img src="images/icon-apple.svg" alt="image" class="login__social-img">
                    </a>
                </div>
            </div>

            <p class="login__switch">
                Bạn chưa có tài khoản?
                <button id="loginButtonRegister">Tạo tài khoản</button>
            </p>
        </div>
    </div>

    <!--===== LOGIN REGISTER =====-->
    <div class="login__register">
        <h1 class="login__title">Tạo tài khoản mới.</h1>

        <div class="login__area">
            <form action="register" method="post" class="login__form">
                <div class="login__content grid">
                    <div class="login__box">
                        <input type="text" id="names" name="fullName" required placeholder=" " class="login__input">
                        <label for="names" class="login__label">Họ và tên</label>

                        <i class="ri-id-card-fill login__icon"></i>
                    </div>
                    <div class="login__box">
                        <input type="text" name="username" required placeholder=" " class="login__input">
                        <label for="emailCreate" class="login__label">Username</label>

                        <i class="ri-mail-fill login__icon"></i>
                    </div>
                    <div class="login__box">
                        <input type="email" id="emailCreate" name="email" required placeholder=" " class="login__input">
                        <label for="emailCreate" class="login__label">Email</label>

                        <i class="ri-mail-fill login__icon"></i>
                    </div>
                    <div class="login__box">
                        <input type="text" id="dob" name="dob" required placeholder=" " class="login__input">
                        <label  class="login__label">Ngày tháng năm sinh</label>
                        <i class="ri-mail-fill login__icon"></i>
                    </div>
                    <div class="login__box">
                        <input type="text" id="emailCreate" name="phone" required placeholder=" " class="login__input">
                        <label for="emailCreate" class="login__label">Số điện thoại</label>

                        <i class="ri-mail-fill login__icon"></i>
                    </div>
                    <div class="login__box">
                        <input type="text" id="emailCreate" name="address"  required placeholder=" " class="login__input">
                        <label for="emailCreate" class="login__label">Địa chỉ</label>

                        <i class="ri-mail-fill login__icon"></i>
                    </div>


                    <div class="login__box">
                        <input type="password" id="passwordCreate" name="password" required placeholder=" " class="login__input">
                        <label for="passwordCreate" class="login__label">Mật khẩu</label>

                        <i class="ri-eye-off-fill login__icon login__password" id="loginPasswordCreate"></i>
                    </div>
                </div>

                <button type="submit" class="login__button">Tạo tài khoản</button>
            </form>

            <p class="login__switch">
                Đã có tài khoản?
                <button id="loginButtonAccess">Đăng nhập</button>
            </p>
        </div>
    </div>
</div>

<!--=============== MAIN JS ===============-->
<script src="js/login.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script>
    $(document).ready(function() {
        $("#dob").datepicker({
            dateFormat: "dd/mm/yy",  // Định dạng ngày hiển thị
            changeMonth: true,
            changeYear: true
        });

    });
</script>
</body>
</html>