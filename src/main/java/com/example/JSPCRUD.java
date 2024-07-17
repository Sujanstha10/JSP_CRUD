package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JSPCRUD extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection conn = DatabaseUtil.getConnection()) {
			String id = request.getParameter("id");
			String sql = "SELECT * FROM users";
			if (id != null) {
				sql += " WHERE id = ?";
			}

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				if (id != null) {
					stmt.setInt(1, Integer.parseInt(id));
				}
				try (ResultSet rs = stmt.executeQuery()) {
					PrintWriter out = response.getWriter();
					while (rs.next()) {
						out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name")+ ", Email: " + rs.getString("email"));
					}
				}
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String method = request.getParameter("_method");
    if (method == null) {
        method = "post";
    }

    switch (method.toLowerCase()) {
        case "put":
            doPut(request, response);
            break;
        case "delete":
            doDelete(request, response);
            break;
        default:
            // Original POST handling code
            response.setContentType("text/html");

            try (Connection conn = DatabaseUtil.getConnection()) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");

                // Ensure required parameters are provided
                if (name == null || email == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().println("Name and email are required.");
                    return;
                }

                String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    int rowsAffected = stmt.executeUpdate();

                    // Check if the insertion was successful
                    if (rowsAffected > 0) {
                        response.setStatus(HttpServletResponse.SC_CREATED);
                        response.getWriter().println("User added successfully.");
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().println("Failed to add user.");
                    }
                }
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Database error: " + e.getMessage());
                throw new ServletException(e);
            }
            break;
    }
}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // Set response content type
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();

	    try (Connection conn = DatabaseUtil.getConnection()) {
	        String id = request.getParameter("id");
	        String name = request.getParameter("name");
	        String email = request.getParameter("email");
	        out.println("email"+email);

	        // Ensure required parameters are provided
	        if (id == null || name == null || email == null) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            out.println("{ \"message\": \"ID, name, and email are required.\" }");
	            return;
	        }

	        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, name);
	            stmt.setString(2, email);
	            stmt.setInt(3, Integer.parseInt(id));
	            int rowsAffected = stmt.executeUpdate();

	            // Check if the update was successful
	            if (rowsAffected > 0) {
	                response.setStatus(HttpServletResponse.SC_OK);
	                out.println("{ \"message\": \"User updated successfully.\" }");
	            } else {
	                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                out.println("{ \"message\": \"User with ID " + id + " not found.\" }");
	            }
	        }
	    } catch (SQLException e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        out.println("{ \"message\": \"Database error: " + e.getMessage() + "\" }");
	        throw new ServletException(e);
	    }
	}


	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection conn = DatabaseUtil.getConnection()) {
			String id = request.getParameter("id");
			String sql = "DELETE FROM users WHERE id = ?";

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, Integer.parseInt(id));
				stmt.executeUpdate();
	            response.getWriter().println("User deleted successfull");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
