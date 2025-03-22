<%@ page import="com.example.backend.models.User" %>
<%@ page contentType="text/html; charset=UTF-8" %>

const header = document.querySelector(".header")
<%
  User user = (User) session.getAttribute("user");
%>
header.innerHTML = `<nav
      class="custom-navbar navbar navbar-expand-md navbar-dark bg-dark"
      arial-label="Furni navigation bar"
    >
      <div class="container">
        <a class="navbar-brand" href="home">Furni<span>.</span></a>

        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarsFurni"
          aria-controls="navbarsFurni"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsFurni">
          <ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
            <li>
              <a class="nav-link" href="home">Trang chủ</a>
            </li>
            <li><a class="nav-link" href="shop?categoryId=3">Sản phẩm</a></li>
            <li><a class="nav-link" href="about.jsp">Về chúng tôi</a></li>
            <li><a class="nav-link" href="services.jsp">Dịch vụ</a></li>
            <li><a class="nav-link" href="contact.jsp">Liên hệ</a></li>
          </ul>

          <ul
            class="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5 align-items-center"
          >
            <li>
              <form class="d-flex" role="search" method="get" action="search">
                <input
                  class="form-control me-2"
                  type="search"
                  placeholder="Tìm kiếm"
                  aria-label="Search"
                  name="keyword"
                />
                <bu class="btn btn-outline-light" type="submit">
               <i class="fa-solid fa-magnifying-glass"></i>
                </bu
                tton>
              </form>
            </li>
            <% if(user == null){%>
            <li>
              <a class="nav-link" href="login"
                ><img src="images/user.svg" alt="User"
              /></a>
            </li>
            <% } else {%>
            <li>
              <p style="margin-bottom: 0; color: white">Xin chào <%= user.getFullName()%></p>
              <a href="change-password" style="color: white">Đổi mật khẩu</a>

              <a href="logout" style="color: white">Đăng xuất</a>
            </li>
            <%}%>
            <li>
              <a class="nav-link" href="view-cart"
                ><img src="images/cart.svg" alt="Cart"
              /></a>
            </li>
          </ul>
        </div>
      </div>
    </nav>`
const listLi = document.querySelectorAll(".navbar-nav li")
const arrLi = Array.from(listLi)
const navLinkList = document.querySelectorAll(".nav-link")
const navLinkArr = Array.from(navLinkList)

// navLinkArr.map((item, index) => {
//   item.onclick = (e) => {
//     localStorage.setItem("activePage", item.href)
//     arrLi.map((item) => item.classList.remove("active"))
//     arrLi[index].classList.add("nav-item")
//     arrLi[index].classList.add("active")
//   }
// })
// window.addEventListener("load", function () {
//   const activePage = localStorage.getItem("activePage")
//   if (activePage) {
//     const activeLink = document.querySelector(`a[href="${activePage}"]`)
//     console.log(activeLink)
//     if (activeLink) {
//       activeLink.parentElement.classList.add("active")
//     }
//   }
// })
document.addEventListener("DOMContentLoaded", function () {
  const currentPage = window.location.pathname
  console.log(currentPage)
  const links = document.querySelectorAll(".nav-link")
  links.forEach((link) => {
    if (currentPage.includes(link.getAttribute("href"))) {
      // Thêm lớp 'active' vào <li> chứa thẻ <a>
      link.parentElement.classList.add("active")
    }
  })
})
