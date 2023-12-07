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

@WebServlet("/signin")
public class LoginServlet extends HttpServlet {
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

        String fetchSQL = "SELECT * FROM emails";
        PreparedStatement fetchStatement = null;
		try {
			fetchStatement = con.prepareStatement(fetchSQL);
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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	String username = request.getParameter("username");
        String password = request.getParameter("password");
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword(password);
        HttpSession session = request.getSession();
        // Retrieve the last login attempt time from the session
        Long lastLoginTime = (Long) session.getAttribute("lastLoginTime");
        if (lastLoginTime == null) {
            lastLoginTime = System.currentTimeMillis();
            session.setAttribute("lastLoginTime", lastLoginTime);
        }
        Integer connexion = (Integer) session.getAttribute("connexion");
        System.out.println(connexion);
        if (connexion == null) {
            connexion = 0;
        }

        
        try {
        	if (connexion<5) {
        		if (loginDao.validate(loginBean)) {
                //HttpSession session = request.getSession();
                // session.setAttribute("username",username);
            	request.setAttribute("emails", emails);
        		request.getRequestDispatcher("index.jsp").forward(request, response);
            } 	else {
            	connexion++;
            	session.setAttribute("connexion", connexion);

                request.setAttribute("message", " user not found !!!");
                request.setAttribute("connexion",connexion);
                // Forward the request to the JSP page
                RequestDispatcher dispatcher = request.getRequestDispatcher("/connectionfailed.jsp");
                dispatcher.forward(request, response);
                            }
        	}else {
        		 // Too many login attempts
                Long currentTime = System.currentTimeMillis();
                // Set a time duration for resetting the counter (e.g., 5 minutes)
                long resetDuration = 5000; // 20 second in milliseconds
                
                if (currentTime - lastLoginTime > resetDuration) {
                    // Reset the counter
                    connexion = 1;
                    session.setAttribute("connexion", connexion);
                    session.setAttribute("lastLoginTime", currentTime);
                } else {
                    request.setAttribute("message", "Connection failed !!!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/connectionfailed.jsp");
                    dispatcher.forward(request, response);
                    return; // Skip further processing if the counter is not reset
        	}
        	}
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}