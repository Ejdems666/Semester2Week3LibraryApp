<%@ page import="model.entity.Material" %><% Material material = ((Material) request.getAttribute("material")); %>
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