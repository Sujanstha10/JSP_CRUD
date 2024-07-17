<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.example.DatabaseUtil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CRUD Operations</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
    <h1>CRUD Operations</h1>

    <!-- Form for adding a new user -->
    <h2>Add User</h2>
    <form action="crud" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <button type="submit">Add</button>
    </form>

    <!-- Form for updating an existing user -->
    <h2>Update User</h2>
    <form action="crud" method="post">
        <input type="hidden" name="_method" value="PUT">
        <label for="update_id">ID:</label>
        <input type="text" id="update_id" name="id" required>
        <label for="update_name">Name:</label>
        <input type="text" id="update_name" name="name" required>
        <label for="update_email">Email:</label>
        <input type="email" id="update_email" name="email" required>
        <button type="submit">Update</button>
    </form>

    <!-- Form for deleting an existing user -->
    <h2>Delete User</h2>
    <form action="crud" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <label for="delete_id">ID:</label>
        <input type="text" id="delete_id" name="id" required>
        <button type="submit">Delete</button>
    </form>

    <!-- Display users -->
    <h2>Users List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <%
                try (Connection conn = DatabaseUtil.getConnection()) {
                    String sql = "SELECT * FROM users";
                    try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            out.println("<tr>");
                            out.println("<td>" + rs.getInt("id") + "</td>");
                            out.println("<td>" + rs.getString("name") + "</td>");
                            out.println("<td>" + rs.getString("email") + "</td>");
                            out.println("</tr>");
                        }
                    }
                } catch (SQLException e) {
                    out.println("<tr><td colspan='3'>Error retrieving data: " + e.getMessage() + "</td></tr>");
                }
            %>
        </tbody>
    </table>
</body>
</html>
