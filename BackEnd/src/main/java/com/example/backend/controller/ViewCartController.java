
package com.example.backend.controller;

import com.example.backend.models.Cart;
import com.example.backend.models.CartItem;
import com.example.backend.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet({"/view-cart", "/remove-from-cart", "/update-cart"})
public class ViewCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            response.sendRedirect("/login");
            return;
        }
        Cart cart = (Cart) session.getAttribute("cart");

        // Kiểm tra hành động từ URL
        String action = request.getServletPath();

        if ("/remove-from-cart".equals(action)) {
            // Lấy productId từ tham số URL
            String productId = request.getParameter("productId");
            if (cart != null && productId != null) {
                cart.removeItem(Integer.parseInt(productId)); // Giả sử bạn có phương thức removeItem trong Cart
            }
        } else if ("/update-cart".equals(action)) {
            // Cập nhật số lượng sản phẩm
            doPost(request,response);
        }
        request.getRequestDispatcher("cart.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            response.sendRedirect("/login");
            return;
        }
        Cart cart = (Cart) session.getAttribute("cart");
        String action = request.getServletPath();
        System.out.println(action);
        if ("/update-cart".equals(action)) {
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity_" + productId);
            System.out.println(productId);
            System.out.println(quantity);
            if (cart != null && productId != null && quantity != null) {
                int qty = Integer.parseInt(quantity);
                cart.updateItemQuantity(productId, qty);
            }
        }
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request,response);
    }
}
