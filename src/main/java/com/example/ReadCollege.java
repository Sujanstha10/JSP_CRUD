package com.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadCollege extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String id = request.getParameter("id");
            String sql = "SELECT * FROM colleges";
            if (id != null && !id.isEmpty()) {
                sql += " WHERE id = ?";
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (id != null && !id.isEmpty()) {
                    stmt.setInt(1, Integer.parseInt(id));
                }
                try (ResultSet rs = stmt.executeQuery()) {
                    List<College> colleges = new ArrayList<>();
                    while (rs.next()) {
                        College college = new College();
                        college.setId(rs.getInt("id"));
                        college.setName(rs.getString("name"));
                        college.setDepartment(rs.getString("department"));
                        colleges.add(college);
                    }
                    request.setAttribute("colleges", colleges);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("read.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
