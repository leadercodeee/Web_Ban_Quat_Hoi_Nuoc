const sidebar = document.querySelector(".left")
sidebar.innerHTML = `
        <!-- Sidebar -->
        <!-- logo -->
        <div class="logo">
          <img src="../images/logo.png" alt="logo" />
          <span>Furni</span>
        </div>
        <!-- Divider -->
        <hr class="solid" />
        <!-- list item -->
        <div class="list-navbar">
          <ul class="list-item">
            <li class="item" id= "item-product">
              <i class="fa-solid fa-fan"></i>
              <a href ="products">Quản lý sản phẩm</a>
            </li>
            <li class="item" id ="item-category">
              <i class="fa-solid fa-layer-group"></i>
              <a href = "categories">Quản lý danh mục</a>
            </li>
            <li class="item"p id = "item-user">
              <i class="fa-solid fa-table-list"></i>
                <a href ="users">Quản lý người dùng</a>
            </li>
            <li class="item" id ="item-order">
              <i class="fa-solid fa-gift"></i>
              <a href = "orders">Quản lý đơn hàng</a>
            </li>
            <li class="item" id ="item-order">
              <i class="fa-solid fa-gift"></i>
              <a href = "discounts">Quản lý giảm giá</a>
            </li>
          </ul>
        </div>
        <!-- profile -->
        <div class="profile">
          <div class="avatar">
            <img
              src="https://png.pngtree.com/png-vector/20240724/ourlarge/pngtree-administrator-admin-avatar-png-image_12853673.png"
              alt="avatar"
            />
          </div>
          <p class="name">Emily Jonson</p>
          <span class="email">emilyjonson@gmail.com</span>
        </div>
        <!-- logout -->
        <ul class="list-item">
          <li class="item">
            <i class="fa-solid fa-right-from-bracket"></i>
            Đăng xuất
          </li>
        </ul>
     `
