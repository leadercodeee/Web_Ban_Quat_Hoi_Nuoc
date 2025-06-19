<% String message = (String) request.getAttribute("message"); %>
<% if (message != null) { %>
<h2 style="color: green; text-align: center;"><%= message %></h2>
<% } %>
