package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	String name;
//	
//	@Override
//	public void init() throws ServletException {
//		name="Servlet";
//		System.out.println("inside init");
//	}
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
        
        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertData(request, response);
                break;
            case "display":
                displayData(request, response);
                break;
            case "update":
                updateData(request, response);
                break;
            case "delete":
                deleteData(request, response);
                break;
            case "displayAll":
                displayAllData(request, response);
                break;
            default:
                response.getWriter().println("Invalid action");
        }
    }
        
	private void insertData(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // Retrieve user input from the request parameters
	    String name = request.getParameter("name");
	    String fatherName = request.getParameter("fatherName");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String phone = request.getParameter("phone");
	    String dob = request.getParameter("dob");
	    String gender = request.getParameter("gender");

	    // JDBC variables
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        // Load the JDBC driver
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // Establish the database connection
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/register", "root", "root1234");

	        // SQL query to insert data into the database
	        String sql = "INSERT INTO register(name, father_name, email, password, phone, dob, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, name);
	        ps.setString(2, fatherName);
	        ps.setString(3, email);
	        ps.setString(4, password);
	        ps.setString(5, phone);
	        ps.setString(6, dob);
	        ps.setString(7, gender);

	        // Execute the query
	        ps.executeUpdate();

	        // Send a response back to the client
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html><body>");
	        out.println("<h2>User Details</h2>");
	        out.println("<p>Name: " + name + "</p>");
	        out.println("<p>Father's Name: " + fatherName + "</p>");
	        out.println("<p>Email: " + email + "</p>");
	        out.println("<p>Phone Number: " + phone + "</p>");
	        out.println("<p>Date of Birth: " + dob + "</p>");
	        out.println("<p>Gender: " + gender + "</p>");
	        out.println("</body></html>");
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources in the finally block
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// Import statements and other existing code...

	private void displayData(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String name = request.getParameter("name");

	    // JDBC variables
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        // Load the JDBC driver
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // Establish the database connection
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/register", "root", "root1234");

	        // SQL query to retrieve user data from the database
	        String sql = "SELECT * FROM register WHERE name = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, name);

	        // Execute the query
	        rs = ps.executeQuery();

	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html><body>");
	        out.println("<h2>User Information</h2>");

	        if (rs.next()) {
	            out.println("<p>Name: " + rs.getString("name") + "</p>");
	            out.println("<p>Father's Name: " + rs.getString("father_name") + "</p>");
	            out.println("<p>Email: " + rs.getString("email") + "</p>");
	            out.println("<p>Phone Number: " + rs.getString("phone") + "</p>");
	            out.println("<p>Date of Birth: " + rs.getString("dob") + "</p>");
	            out.println("<p>Gender: " + rs.getString("gender") + "</p>");
	        } else {
	            out.println("<p>User not found</p>");
	        }

	        out.println("</body></html>");
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources in the finally block
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (ps != null) {
	                ps.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
        
        private void updateData(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String name = request.getParameter("name");
            String newEmail = request.getParameter("newEmail");
            String newPassword = request.getParameter("newPassword");
            String newPhone = request.getParameter("newPhone");

            // JDBC variables
            Connection conn = null;
            PreparedStatement ps = null;

            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/register", "root", "root1234");

                // SQL query to update data in the database
                String sql = "UPDATE register SET email = ?, password = ?, phone = ? WHERE name = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, newEmail);
                ps.setString(2, newPassword);
                ps.setString(3, newPhone);
                ps.setString(4, name);

                // Execute the query
                int rowsUpdated = ps.executeUpdate();

                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h2>Update Result</h2>");

                if (rowsUpdated > 0) {
                    out.println("<p>Update successful for user: " + name + "</p>");
                } else {
                    out.println("<p>User not found or no changes made</p>");
                }

                out.println("</body></html>");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                // Close resources in the finally block
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void deleteData(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String name = request.getParameter("name");

            // JDBC variables
            Connection conn = null;
            PreparedStatement ps = null;

            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/register", "root", "root1234");

                // SQL query to delete data from the database
                String sql = "DELETE FROM register WHERE name = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name);

                // Execute the query
                int rowsDeleted = ps.executeUpdate();

                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h2>Delete Result</h2>");

                if (rowsDeleted > 0) {
                    out.println("<p>Delete successful for user: " + name + "</p>");
                } else {
                    out.println("<p>User not found or no changes made</p>");
                }

                out.println("</body></html>");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                // Close resources in the finally block
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } 

        private void displayAllData(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // JDBC variables
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/register", "root", "root1234");

                // SQL query to retrieve all user data from the database
                String sql = "SELECT * FROM register";
                ps = conn.prepareStatement(sql);

                // Execute the query
                rs = ps.executeQuery();

                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<style>table {margin: 0 auto;}</style>");
                out.println("<h2 style='text-align: center;'>All User Information</h2>");

                out.println("<table border='1'>");
                out.println("<tr><th>Name</th><th>Father's Name</th><th>Email</th><th>Phone Number</th><th>Date of Birth</th><th>Gender</th></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("father_name") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("<td>" + rs.getString("phone") + "</td>");
                    out.println("<td>" + rs.getString("dob") + "</td>");
                    out.println("<td>" + rs.getString("gender") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");

                out.println("<hr>");
                out.println("</body></html>");
                
                
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                // Close resources in the finally block
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
}
	
	
	
//	@Override
//	public void destroy() {
//		System.out.println("Destructed after service");
//	}

	


