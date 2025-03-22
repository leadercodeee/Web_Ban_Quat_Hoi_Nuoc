
package com.example.backend.controller;

import com.example.backend.models.Discount;
import com.example.backend.models.User;
import com.example.backend.services.DiscountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/admin/editDiscount")
public class EditDiscountController extends HttpServlet {
    DiscountService discountService = new DiscountService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String discountIdParam = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null ){
            response.sendRedirect("/home");
            return;
        }
        if (!"admin".equals(user.getRole())) {
            response.sendRedirect("/home");
            return;
        }
        if (discountIdParam != null) {
            try {
                int discountId = Integer.parseInt(discountIdParam);
                Discount discount = discountService.getDiscountById(discountId);
                if (discount != null) {
                    request.setAttribute("discount", discount);
                    request.getRequestDispatcher("/admin/editDiscount.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/admin/discounts");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("/admin/discounts");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        BigDecimal discountValue = new BigDecimal(request.getParameter("discount_value"));
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        int discountId = Integer.parseInt(request.getParameter("id"));

        // Kiểm tra và xử lý điều kiện
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date checkStartDate = sdf.parse(startDate);
            Date checkEndDate = sdf.parse(endDate);
            if (checkEndDate.before(checkStartDate)) {
                Discount discount = discountService.getDiscountById(discountId);
                request.setAttribute("discount", discount);
                request.setAttribute("error", "Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
                request.getRequestDispatcher("/admin/editDiscount.jsp").forward(request, response);
            } else {
                String formattedStartDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" + startDate.split("/")[0];
                String formattedEndDate = endDate.split("/")[2] + "-" + endDate.split("/")[1] + "-" + endDate.split("/")[0];

                Discount discount = new Discount(discountId, name, discountValue, formattedStartDate, formattedEndDate);
                discountService.update(discount);

                response.sendRedirect("/admin/discounts");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
