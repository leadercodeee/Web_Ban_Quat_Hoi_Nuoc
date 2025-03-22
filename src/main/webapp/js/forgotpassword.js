// DOM Elements
const form = document.querySelector("form");
const emailInput = document.getElementById("email");

// Thêm sự kiện submit cho form
form.addEventListener("submit", function (event) {
    event.preventDefault(); // Ngăn form gửi dữ liệu tự động

    const emailValue = emailInput.value.trim();

    // Kiểm tra định dạng email hoặc số điện thoại
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Định dạng email
    const phonePattern = /^[0-9]{10,15}$/; // Định dạng số điện thoại (10-15 chữ số)

    if (emailPattern.test(emailValue) || phonePattern.test(emailValue)) {
        // Nếu dữ liệu hợp lệ, chuyển hướng đến trang recode.jsp
        alert("Tìm kiếm thành công! Đang chuyển đến trang đặt lại mật khẩu...");
        window.location.href = "recode.jsp";
    } else {
        // Nếu dữ liệu không hợp lệ, hiển thị thông báo lỗi
        alert("Vui lòng nhập email hoặc số điện thoại hợp lệ!");
    }
});
