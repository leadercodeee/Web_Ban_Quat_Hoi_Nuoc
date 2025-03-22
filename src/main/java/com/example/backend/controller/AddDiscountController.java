
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet("/admin/addDiscount")
public class AddDiscountController extends HttpServlet {
    DiscountService discountService = new DiscountService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        if(userSession == null ){
            response.sendRedirect("/home");
            return;
        }
        if (!"admin".equals(userSession.getRole())) {
            response.sendRedirect("/home");
            return;
        }
        request.getRequestDispatcher("/admin/addDiscount.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        BigDecimal discountValue = new BigDecimal(request.getParameter("discount_value"));
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
//        check dieu kien
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date checkStartDate = sdf.parse(startDate);
            Date checkEndDate = sdf.parse(endDate);
            if(checkEndDate.before(checkStartDate)){
                request.setAttribute("errorMessage", "Ngày kết thúc phải lớn hơn ngày bắt đầu!");
                request.getRequestDispatcher("/admin/addDiscount.jsp").forward(request,response);
            } else{
                String[] startDateParts = startDate.split("/");
                String formattedStartDate = startDateParts[2] + "-" + startDateParts[1] + "-" + startDateParts[0];

                String[] endDateParts = endDate.split("/");
                String formattedEndDate = endDateParts[2] + "-" + endDateParts[1] + "-" + endDateParts[0];
                Discount discount = new Discount(name, discountValue, formattedStartDate, formattedEndDate);
                discountService.addDiscount(discount);

                response.sendRedirect("/admin/discounts");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
