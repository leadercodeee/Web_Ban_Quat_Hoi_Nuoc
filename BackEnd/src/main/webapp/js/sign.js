function handlePrivateKeyUpload(event) {
    const fileInput = event.target;
    const file = fileInput.files[0];
    const filePathInput = document.getElementById("privateKeyPath");

    if (!file) {
        filePathInput.value = "Chưa chọn file";
        return;
    }

    // Hiển thị tên file
    filePathInput.value = file.name;

    // Gửi file đến servlet
    const formData = new FormData();
    formData.append("privateKey", file);

    fetch("uploadPrivateKey", {
        method: "POST",
        body: formData
    })
        .then(response => response.text())
        .then(result => {
            alert("✅ " + result);
        })
        .catch(error => {
            console.error("❌ Upload lỗi:", error);
            alert("❌ Upload thất bại. Xem console để biết chi tiết.");
        });
}
