<%@ page import="java.util.List" %>
<%@ page import="com.example.College" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Read Colleges</title>
</head>
<body>
    <!-- Form for adding a new college -->
    <jsp:include page="create.jsp" />

    <!-- Form for updating an existing college -->
    <jsp:include page="update.jsp" />

    <!-- Form for deleting an existing college -->
    <jsp:include page="delete.jsp" />

    <h1>Read Colleges</h1>
    <form action="ReadCollege" method="get">
        <label for="id">College ID (optional):</label>
        <input type="text" id="id" name="id"><br>
        <button type="submit">Get College(s)</button>
    </form>
    <hr>
    <h2>College Details:</h2>
    <%
        List<College> colleges = (List<College>) request.getAttribute("colleges");
        if (colleges != null && !colleges.isEmpty()) {
            for (College college : colleges) {
                out.println("ID: " + college.getId() + ", Name: " + college.getName() + ", Department: " + college.getDepartment() + "<br>");
            }
        } else {
            out.println("No colleges found.");
        }
    %>
</body>
</html>
