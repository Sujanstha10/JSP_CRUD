package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCollege extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = DatabaseUtil.getConnection()) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String department = request.getParameter("department");

            // Ensure required parameters are provided
            if (id == null || name == null || department == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{ \"message\": \"ID, name, and department are required.\" }");
                return;
            }

            String sql = "UPDATE colleges SET name = ?, department = ? WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, department);
                stmt.setInt(3, Integer.parseInt(id));
                int rowsAffected = stmt.executeUpdate();

                // Check if the update was successful
                if (rowsAffected > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println("{ \"message\": \"College updated successfully.\" }");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{ \"message\": \"College with ID " + id + " not found.\" }");
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{ \"message\": \"Database error: " + e.getMessage() + "\" }");
            throw new ServletException(e);
        }
    }
}
