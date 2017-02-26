<%@ page import="model.entity.Material" %>
<%@ page import="model.entity.Reservation" %>
<%@ page import="model.entity.User" %>
<% Material material = ((Material) request.getAttribute("material")); %>
<% Reservation reservation = ((Reservation) session.getAttribute("reservation")); %>
<% User user = ((User) session.getAttribute("user")); %>
<h1>Detail of <%= material.getTitle() %></h1>
<table class="table">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Language</th>
        <th>Published Date</th>
        <th>Publisher</th>
        <th>Type</th>
    </tr>
    <tr>
        <td><%= material.getId() %></td>
        <td><%= material.getTitle() %></td>
        <td><%= material.getLanguage() %></td>
        <td><%= material.getPublishedAt() %></td>
        <td><%= material.getPublisher() %></td>
        <td><%= material.getMaterialType().getType() %></td>
    </tr>
</table>
<% if (user != null){ %>
    <% if (reservation == null){ %>
        <form action="${root}material/reserve?id=<%= material.getId() %>">
            <button>reserve</button>
        </form>
    <% } else {  %>
        <strong>Reserved until <%=reservation.getReservedUntil()%></strong>
    <% }  %>
<% }  %>