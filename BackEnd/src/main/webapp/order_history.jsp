<%@ page import="com.example.backend.models.Order, java.util.List" %>
<%
    List<Order> orderList = (List<Order>) request.getAttribute("orderList");
%>
<h2>Lịch sử mua hàng</h2>
<table border="1">
    <tr>
        <th>ID</th><th>Ngày</th><th>Hóa đơn</th><th>Ký</th><th>Hóa đơn đã ký</th>
    </tr>
    <% for(Order o : orderList) { %>
    <tr>
        <td><%= o.getId() %></td>
        <td><%= o.getOrderDate() %></td>
        <td>
            <a href="GenerateInvoiceServlet?orderId=<%= o.getId() %>">Tải hóa đơn</a>
        </td>
        <td>
            <form action="SignInvoiceServlet" method="post">
                <input type="hidden" name="orderId" value="<%= o.getId() %>"/>
                <input type="submit" value="Ký"/>
            </form>
        </td>
        <td>
            <% if(o.getSignature() != null) { %>
            <a href="DownloadInvoiceServlet?orderId=<%= o.getId() %>">Tải hóa đơn đã ký</a>
            <% } else { %>
            Chưa ký
            <% } %>
        </td>
    </tr>
    <% } %>
</table>
