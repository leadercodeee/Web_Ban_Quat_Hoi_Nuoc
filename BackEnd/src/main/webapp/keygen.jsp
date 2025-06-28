<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.backend.models.User" %>

<%
    HttpSession sessionUser = request.getSession(false);
    if (sessionUser == null || sessionUser.getAttribute("user") == null) {
        response.sendRedirect("auth.jsp");
        return;
    }
    User user = (User) sessionUser.getAttribute("user");
    String privateKeyPEM = (String) request.getAttribute("privateKeyPEM");
    String publicKeyPEM  = (String) request.getAttribute("publicKeyPEM");
    // Danh sách lịch sử khoá (nếu có) – list<Map<String,Object>> với version, createdAt, status
    List<Map<String, Object>> keyHistory = (List<Map<String,Object>>) request.getAttribute("keyHistory");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý khoá số – <%= user.getFullName() %></title>

    <!-- ✅ Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet"/>

    <style>
        body {
            background: #f6f9fc;
            min-height: 100vh;
        }
        .key-block {
            background:#fdfdfd;
            border:1px solid #d8dee8;
            border-radius: .5rem;
            padding:1rem;
            max-height:260px;
            overflow:auto;
            font-size: .9rem;
            white-space: pre-wrap;
            word-break: break-all;
        }
        .copy-btn {
            position:absolute;
            top: .25rem;
            right:.5rem;
        }
    </style>
</head>
<body>

<div class="container-lg py-5">
    <!-- HEADER -->
    <div class="card shadow-sm mb-4">
        <div class="card-body d-flex flex-column flex-md-row justify-content-between align-items-md-center">
            <div>
                <h4 class="mb-1 fw-bold"><i class="fa-solid fa-lock"></i> Cặp khoá RSA của bạn</h4>
                <small class="text-muted">Xin chào, <strong><%= user.getFullName() %></strong> – <%= user.getEmail() %></small>
            </div>
            <a href="index.jsp" class="btn btn-outline-secondary mt-3 mt-md-0"><i class="fa-solid fa-house"></i> Trang chủ</a>
        </div>
    </div>

    <!-- MAIN PANEL -->
    <% if (privateKeyPEM != null && publicKeyPEM != null) { %>
    <!-- Khoá đang hoạt động -->
    <div class="row g-4">
        <div class="col-md-6">
            <div class="card shadow-sm h-100">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center position-relative">
                    <span><i class="fa-solid fa-key"></i> Private Key</span>
                    <button class="btn btn-sm btn-light copy-btn" data-target="privateKey"><i class="fa-regular fa-copy"></i></button>
                </div>
                <div class="card-body">
                    <div id="privateKey" class="key-block"><%= privateKeyPEM %></div>
                    <a class="btn btn-primary mt-3" href="downloadKey?type=private"><i class="fa-solid fa-download"></i> Tải Private Key</a>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow-sm h-100">
                <div class="card-header bg-success text-white d-flex justify-content-between align-items-center position-relative">
                    <span><i class="fa-solid fa-bullhorn"></i> Public Key</span>
                    <button class="btn btn-sm btn-light copy-btn" data-target="publicKey"><i class="fa-regular fa-copy"></i></button>
                </div>
                <div class="card-body">
                    <div id="publicKey" class="key-block"><%= publicKeyPEM %></div>
                    <a class="btn btn-success mt-3" href="downloadKey?type=public"><i class="fa-solid fa-download"></i> Tải Public Key</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Action buttons -->
    <div class="d-flex flex-wrap gap-3 my-4">
        <form action="reset-key" method="post">
            <button class="btn btn-warning"><i class="fa-solid fa-rotate"></i> Báo mất khoá – Tạo lại</button>
        </form>
        <a class="btn btn-outline-primary" href="sign.jsp"><i class="fa-solid fa-file-signature"></i> Xem & ký đơn hàng</a>
    </div>

    <!-- Lịch sử khoá -->
    <h5 class="mt-5 mb-3 fw-bold"><i class="fa-solid fa-clock-rotate-left"></i> Lịch sử phiên bản khoá</h5>
    <div class="table-responsive">
        <table class="table table-striped table-bordered align-middle">
            <thead class="table-light">
            <tr>
                <th>#</th>
                <th>Ngày tạo</th>
                <th>Trạng thái</th>
                <th>Ghi chú</th>
            </tr>
            </thead>
            <tbody>
            <% if (keyHistory != null && !keyHistory.isEmpty()) {
                for (Map<String,Object> row : keyHistory) { %>
            <tr>
                <td class="text-center"><%= row.get("version") %></td>
                <td><%= row.get("createdAt") %></td>
                <td>
                    <% String status = (String) row.get("status"); %>
                    <span class="badge bg-<%= "active".equals(status)?"success":"danger" %>">
                                     <%= "active".equals(status) ? "Hoạt động" : "Thu hồi" %>
                                </span>
                </td>
                <td><%= row.get("note") == null ? "" : row.get("note") %></td>
            </tr>
            <%     }
            } else { %>
            <tr><td colspan="4" class="text-center">Chưa có dữ liệu</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <% } else { %>
    <!-- Chưa có khoá -->
    <div class="alert alert-info text-center p-5">
        <p class="mb-4 fs-5"><i class="fa-solid fa-triangle-exclamation"></i> Bạn chưa có cặp khoá RSA.</p>
        <a class="btn btn-primary btn-lg" href="keygen"><i class="fa-solid fa-gears"></i> Tạo cặp khoá mới</a>
    </div>
    <% } %>
</div>

<!-- Bootstrap JS Bundle (Popper + JS) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Copy to clipboard helper
    document.querySelectorAll('.copy-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const targetId = this.getAttribute('data-target');
            const text = document.getElementById(targetId).innerText;
            navigator.clipboard.writeText(text).then(() => {
                this.innerHTML = '<i class="fa-solid fa-check"></i>';
                setTimeout(()=>{ this.innerHTML = '<i class="fa-regular fa-copy"></i>'; },1500);
            });
        });
    });
</script>
</body>
</html>
