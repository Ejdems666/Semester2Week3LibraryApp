<%@ page import="java.util.Collection" %>
<%@ page import="model.entity.Material" %>
<h1>List of all</h1>
<table class="table">
    <tr>
        <th>ID (link to detail)</th>
        <th>Title</th>
        <th>Language</th>
        <th>Published Date</th>
        <th>Publisher</th>
        <th>Type</th>
    </tr>
    <% for(Material material : ((Collection< Material>) request.getAttribute("materials"))) { %>
        <tr>
            <td><a href="?id=<%= material.getId() %>" title="link ot detail"><%= material.getId() %></a></td>
            <td><%= material.getTitle() %></td>
            <td><%= material.getLanguage() %></td>
            <td><%= material.getPublishedAt() %></td>
            <td><%= material.getPublisher() %></td>
            <td><%= material.getMaterialType().getType() %></td>
        </tr>
    <% } %>
</table>