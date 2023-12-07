package com.fst.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.file.Matcher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
/**
 * Servlet implementation class ListEmailServlet
 */
@WebServlet("/index")
public class ListEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    List<String> emails = new ArrayList<>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    private Connection conn;

    public void init() {
    	try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    String jdbcUrl = "jdbc:mysql://localhost:3306/users?useSSL=false";
    	    conn = DriverManager.getConnection(jdbcUrl, "root", "PHW#84#jeor");
    	} catch (ClassNotFoundException | SQLException e) {
    	    e.printStackTrace();
    	    // Handle errors
    	}
        
        String fetchSQL = "SELECT * FROM emails";
        PreparedStatement fetchStatement = null;
		try {
			fetchStatement = conn.prepareStatement(fetchSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ResultSet resultSet = null;
		try {
			resultSet = fetchStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
			while (resultSet.next()) {
			    emails.add(resultSet.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        
    }
    public static boolean isValidEmail(String email) {
        // Regular expression for a simple email validation
        String emailRegex = "([A-Z]|[a-z]|[0-9])*@(yahoo|gmail|hotmail).(com|tn|fr)";
        Pattern pattern = Pattern.compile(emailRegex);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String val = request.getParameter("action");
        // Utilisation de la connexion existante pour insérer l'e-mail dans la base de données
        // ...
        if (isValidEmail(email)) {    
            // Check if the email exists in the database
            try {
                if (conn != null) {
                    if ("add".equals(val)) {
                        PreparedStatement checkStatement = conn.prepareStatement("SELECT * FROM emails WHERE email = ?");
                        checkStatement.setString(1, email);

                        ResultSet resultSet = checkStatement.executeQuery();

                        if (resultSet.next()) {
                            // Email exists in the table
                        	request.setAttribute("message", " email existe deja !!!");

                            // Forward the request to the JSP page
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                            dispatcher.forward(request, response);
                            // You can perform actions here for an existing email
                        } else {
                            // Email does not exist in the table
                            String insertSQL = "INSERT INTO emails (email) VALUES (?)";
                            PreparedStatement insertStatement = conn.prepareStatement(insertSQL);
                            insertStatement.setString(1, email); // Set the email parameter in the query

                            int rowsInserted = insertStatement.executeUpdate(); // Execute the query for insertion

                            if (rowsInserted > 0) {
                            	emails.add(email);
                                request.setAttribute("emails", emails);
                                request.setAttribute("message", " email added successful !!!");

                                // Forward the request to the JSP page
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/successful.jsp");
                                dispatcher.forward(request, response);
                                System.out.println("Email added successfully");
                                // You can perform actions here after successful addition
                            } else {
                                System.out.println("Failed to add email");
                                // You can handle the failure here
                            }
                            
                        }
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer les erreurs
            }

            try {
                if (emails.contains(email)) {
                    if (conn != null) {

                        if ("del".equals(val)) {
                            // Check if the email exists in the database
                            PreparedStatement checkStatement = conn.prepareStatement("SELECT * FROM emails WHERE email = ?");
                            checkStatement.setString(1, email);
                            ResultSet resultSet = checkStatement.executeQuery();

                            if (resultSet.next()) {
                                // Email exists in the table, proceed with deletion
                                String deleteSQL = "DELETE FROM emails WHERE email = ?";
                                PreparedStatement deleteStatement = conn.prepareStatement(deleteSQL);
                                deleteStatement.setString(1, email);

                                int rowsDeleted = deleteStatement.executeUpdate();

                                if (rowsDeleted > 0) {
                                    System.out.println("Email unsubscribed successfully");
                                    // You can perform actions here after successful unsubscription
                                    emails.remove(email); // remove the email from the list
                                } else {
                                    System.out.println("Failed to unsubscribe email");
                                    // You can handle the failure here
                                }
                            } else {
                                // Email does not exist in the table, handle accordingly
                                System.out.println("Email not found in the database");
                                // You can perform actions here for a non-existing email
                            }
                        }
                    }

                    // Move the forwarding outside of the if (conn != null) block
                    request.setAttribute("emails", emails);
                    request.getRequestDispatcher("index.jsp").forward(request, response); // Redirect after operation
                }
                else {
                	request.setAttribute("message", " email not found !!!");

                    // Forward the request to the JSP page
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                    dispatcher.forward(request, response);
                    // You can take additional actions for an invalid email format
                }
            } catch (SQLException e) {
                e.printStackTrace();

                // Handle errors
            }
        } else { 
            request.setAttribute("message", "Invalid email format. Please enter a valid email address!");

            // Forward the request to the JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            // You can take additional actions for an invalid email format
        }
        
    }
    
    
           

  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("emails", emails);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
