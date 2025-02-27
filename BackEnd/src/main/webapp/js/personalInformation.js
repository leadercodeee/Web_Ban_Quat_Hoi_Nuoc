// DOM Elements
const addressInput = document.getElementById("Address");
const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const acceptTermsCheckbox = document.getElementById("acceptTerms");
const submitButton = document.getElementById("submitButton");

// Hàm kiểm tra định dạng email
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Hàm kiểm tra tất cả điều kiện
function checkFormValidity() {
    const isAddressValid = addressInput.value.trim() !== "";
    const isNameValid = nameInput.value.trim().length >= 3;
    const isEmailValid = isValidEmail(emailInput.value.trim());
    const isTermsAccepted = acceptTermsCheckbox.checked;

    // Nếu tất cả điều kiện đúng, kích hoạt nút submit
    submitButton.disabled = !(isAddressValid && isNameValid && isEmailValid && isTermsAccepted);
}

// Sự kiện khi nhấn nút submit
submitButton.addEventListener("click", function (event) {
    event.preventDefault(); // Ngăn hành động gửi form mặc định
    const isAddressValid = addressInput.value.trim() !== "";
    const isNameValid = nameInput.value.trim().length >= 3;
    const isEmailValid = isValidEmail(emailInput.value.trim());
    const isTermsAccepted = acceptTermsCheckbox.checked;

    // Nếu tất cả điều kiện đúng, chuyển hướng sang index.jsp
    if (isAddressValid && isNameValid && isEmailValid && isTermsAccepted) {
        window.location.href = "index.jsp";
    } else {
        alert("Vui lòng nhập đúng và đủ thông tin trước khi lưu!");
    }
});

// Lắng nghe sự kiện "input" trên các trường dữ liệu
addressInput.addEventListener("input", checkFormValidity);
nameInput.addEventListener("input", checkFormValidity);
emailInput.addEventListener("input", checkFormValidity);
acceptTermsCheckbox.addEventListener("change", checkFormValidity);

// Gọi kiểm tra ban đầu
checkFormValidity();
