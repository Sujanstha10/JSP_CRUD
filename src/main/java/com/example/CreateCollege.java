package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateCollege extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        try (Connection conn = DatabaseUtil.getConnection()) {
            String name = request.getParameter("name");
            String department = request.getParameter("department");

            // Ensure required parameters are provided
            if (name == null || department == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Name and department are required.");
                return;
            }

            String sql = "INSERT INTO colleges (name, department) VALUES (?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, department);
                int rowsAffected = stmt.executeUpdate();

                // Check if the insertion was successful
                if (rowsAffected > 0) {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().println("College added successfully.");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().println("Failed to add college.");
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database error: " + e.getMessage());
            throw new ServletException(e);
        }
    }
}
