<%@ page import="model.entity.MaterialType" %>
<%@ page import="java.util.Collection" %>
<h1>New material</h1>
<form method="post">
    <div class="row">
        <div class="col-sm-12">
            <label for="title">Title:</label>
            <input id="title" class="form-control" type="text" name="title" required>
        </div>
        <div class="col-sm-12">
            <label for="language">Language:</label>
            <input id="language" class="form-control" type="text" name="language" required>
        </div>
        <div class="col-sm-12">
            <label for="publisher">Publisher:</label>
            <input id="publisher" class="form-control" type="text" name="publisher" required>
        </div>
        <div class="col-sm-12">
            <label for="type">Type:</label>
            <select id="type" class="form-control" name="material_type_id" required>
                <% for(MaterialType type : (Collection<MaterialType>) request.getAttribute("types")) { %>
                <option value="<%=type.getId()%>"><%=type.getType()%></option>
                <% } %>
            </select>
        </div>
    </div>
    <button>Create</button>
</form>
