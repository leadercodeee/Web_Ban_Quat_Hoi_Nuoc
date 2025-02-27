<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Việc Làm</title>
    <link rel="stylesheet" href="css/jobs.css">
</head>
<body>
<header>
    <h1>Cơ Hội Nghề Nghiệp tại Công Ty ABC</h1>
    <p>Chúng tôi tìm kiếm những tài năng muốn cùng chúng tôi phát triển.</p>
</header>

<main>
    <!-- Thanh tìm kiếm công việc -->
    <section class="job-search">
        <input type="text" placeholder="Tìm kiếm công việc...">
        <button>Tìm kiếm</button>
    </section>

    <!-- Danh sách vị trí công việc -->
    <section class="job-listings">
        <h2>Vị Trí Tuyển Dụng</h2>

        <!-- Mẫu vị trí công việc -->
        <article class="job-posting">
            <h3>Chuyên Viên Marketing</h3>
            <p><strong>Vị trí:</strong> Toàn thời gian</p>
            <p><strong>Địa điểm:</strong> TP Hồ Chí Minh</p>
            <p><strong>Mô tả công việc:</strong> Phát triển chiến lược tiếp thị, triển khai các chiến dịch quảng cáo và tiếp thị để nâng cao nhận diện thương hiệu.</p>
            <button>Xem chi tiết</button>
        </article>

        <article class="job-posting">
            <h3>Kỹ Sư Phần Mềm</h3>
            <p><strong>Vị trí:</strong> Toàn thời gian</p>
            <p><strong>Địa điểm:</strong> TP Hồ Chí Minh</p>
            <p><strong>Mô tả công việc:</strong> Thiết kế, phát triển và bảo trì các ứng dụng phần mềm đảm bảo hiệu quả và bảo mật.</p>
            <button>Xem chi tiết</button>
        </article>

        <article class="job-posting">
            <h3>Nhân Viên Kế Toán</h3>
            <p><strong>Vị trí:</strong> Bán thời gian</p>
            <p><strong>Địa điểm:</strong>TP Hồ Chí Minh</p>
            <p><strong>Mô tả công việc:</strong> Quản lý hồ sơ tài chính, theo dõi và báo cáo tài chính hàng tháng.</p>
            <button>Xem chi tiết</button>
        </article>
    </section>
</main>

<footer>
    <p>&copy; 2024 Công Ty ABC. Tất cả các quyền được bảo lưu.</p>
</footer>

<script src="js/jobs.js"></script>

</body>


</html>
