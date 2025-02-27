// Hàm hiển thị/ẩn mật khẩu
function togglePasswordVisibility(fieldId, toggleIcon) {
    const passwordField = document.getElementById(fieldId);
    if (passwordField.type === "password") {
        passwordField.type = "text";
        toggleIcon.textContent = "🙈"; // Biểu tượng ẩn mật khẩu
    } else {
        passwordField.type = "password";
        toggleIcon.textContent = "👁️"; // Biểu tượng hiện mật khẩu
    }
}

// Xử lý sự kiện submit form
document.getElementById("changePasswordForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Ngăn form gửi dữ liệu tự động

    const currentPassword = document.getElementById("currentPassword").value.trim();
    const newPassword = document.getElementById("newPassword").value.trim();
    const confirmPassword = document.getElementById("confirmPassword").value.trim();

    // Kiểm tra các điều kiện
    if (newPassword.length < 8) {
        alert("Mật khẩu mới phải có ít nhất 8 ký tự.");
        return;
    }

    if (newPassword !== confirmPassword) {
        alert("Xác nhận mật khẩu không khớp.");
        return;
    }

    alert("Mật khẩu đã được đổi thành công!");
    window.location.href = "personalInformation.jsp"; // Chuyển về trang thông tin cá nhân
});
