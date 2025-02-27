// DOM Elements
const form = document.querySelector("form");
const input = document.getElementById("email");
const errorMessage = document.createElement("p");

// Thêm thẻ thông báo lỗi sau ô nhập mã
errorMessage.id = "errorMessage";
form.insertBefore(errorMessage, form.querySelector("button"));

// Xử lý sự kiện khi nhấn nút "Tiếp tục"
form.addEventListener("submit", function (event) {
    event.preventDefault(); // Ngăn form gửi dữ liệu tự động

    // Lấy giá trị mã xác minh
    const code = input.value.trim();

    // Kiểm tra mã xác minh (6 chữ số)
    const codePattern = /^[0-9]{6}$/;

    if (codePattern.test(code)) {
        // Nếu mã hợp lệ, điều hướng đến trang đặt lại mật khẩu
        errorMessage.style.display = "none"; // Ẩn thông báo lỗi
        window.location.href = "repassword.jsp";
    } else {
        // Nếu mã không hợp lệ, hiển thị thông báo lỗi
        errorMessage.textContent = "Mã xác minh không hợp lệ. Vui lòng nhập 6 chữ số.";
        errorMessage.style.display = "block";
    }
});
/**
 * Hàm hiện/ẩn mật khẩu
 * @param {string} fieldId - ID của trường mật khẩu cần thay đổi
 * @param {HTMLElement} toggleIcon - Biểu tượng con mắt được nhấn
 */
function togglePasswordVisibility(fieldId, toggleIcon) {
    const passwordField = document.getElementById(fieldId);

    if (passwordField.type === "password") {
        passwordField.type = "text"; // Chuyển thành kiểu text (hiện mật khẩu)
        toggleIcon.textContent = "🙈"; // Thay đổi biểu tượng
    } else {
        passwordField.type = "password"; // Chuyển về kiểu password (ẩn mật khẩu)
        toggleIcon.textContent = "👁️"; // Thay đổi biểu tượng
    }
}
