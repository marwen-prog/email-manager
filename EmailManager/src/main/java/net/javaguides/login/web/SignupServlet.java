package net.javaguides.login.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.javaguides.login.bean.LoginBean;
import net.javaguides.login.database.LoginDao;

/**
 * @email Ramesh Fadatare
 */

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    List<String> emails = new ArrayList<>();
    private final LoginDao loginDao = new LoginDao();
private Connection con;
    public void init() {
    	try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    String jdbcUrl = "jdbc:mysql://localhost:3306/users?useSSL=false";
    	    con = DriverManager.getConnection(jdbcUrl, "root", "PHW#84#jeor");
    	} catch (ClassNotFoundException | SQLException e) {
    	    e.printStackTrace();
    	    // Handle errors
    	}
    }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {

    	    String username = request.getParameter("username");
    	    String password = request.getParameter("password");
    	    LoginBean loginBean = new LoginBean();
    	    loginBean.setUsername(username);
    	    loginBean.setPassword(password);

    	    try {
    	        if (loginDao.validate(loginBean)) {
    	            // User already exists
    	            request.setAttribute("message", "User already exists!");
    	            request.getRequestDispatcher("error.jsp").forward(request, response);
    	        } else {
    	            // User does not exist, proceed with insertion
    	            String insertUserSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
    	            String insertLoginSQL = "INSERT INTO login (username, password) VALUES (?, ?)";

    	            try (PreparedStatement insertUserStatement = con.prepareStatement(insertUserSQL);
    	                 PreparedStatement insertLoginStatement = con.prepareStatement(insertLoginSQL)) {

    	                // Insert into 'users' table
    	                insertUserStatement.setString(1, loginBean.getUsername());
    	                insertUserStatement.setString(2, loginBean.getPassword());
    	                int rowsInsertedUser = insertUserStatement.executeUpdate();

    	                // Insert into 'login' table
    	                insertLoginStatement.setString(1, loginBean.getUsername());
    	                insertLoginStatement.setString(2, loginBean.getPassword());
    	                int rowsInsertedLogin = insertLoginStatement.executeUpdate();

    	                if (rowsInsertedUser > 0 && rowsInsertedLogin > 0) {
    	                    // Both insertions successful
    	                    response.sendRedirect("Signin.jsp");
    	                } else {
    	                    // Insertion failed for one or both tables
    	                    request.setAttribute("message", "Failed to insert user into the database.");
    	                    request.getRequestDispatcher("error.jsp").forward(request, response);
    	                }
    	            }
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        // Handle SQL exceptions
    	        request.setAttribute("message", "An error occurred while processing the request.");
    	        request.getRequestDispatcher("error.jsp").forward(request, response);
    	    } catch (ClassNotFoundException e) {
    	        e.printStackTrace();
    	        // Handle class not found exception
    	        request.setAttribute("message", "Database driver not found.");
    	        request.getRequestDispatcher("error.jsp").forward(request, response);
    	    } finally {
    	        // Close the connection in a finally block
    	        try {
    	            if (con != null) {
    	                con.close();
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	}}