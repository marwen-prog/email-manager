<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .error-container {
            background-color: #fff;
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h1 {
            color: #ff6347; /* Tomato color */
        }

        p {
            color: #333;
        }
              body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        a {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            color: #ffffff;
            background-color: #007bff;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Error athentification</h1>
        <p>${message}</p>
        <p>${connexion} time</p>
        <!-- Access the attribute set in the servlet -->
            <script>
        // Assuming 'connexion' is the variable holding the number of attempts
        var connexion = <%= request.getAttribute("connexion") %>;

        // Check if the number of attempts is 5
        if (connexion === 5) {
            // Display an alert
            alert("Too many unsuccessful login attempts. Please try again after 5 second.");
         // Function to simulate sleep/delay
            function sleep(ms) {
                return new Promise(resolve => setTimeout(resolve, ms));
            }

         // Redirect after a delay of 3000 milliseconds (3 seconds)
            setTimeout(function() {
                window.location.href = "Signin.jsp";
            }, 3000);
            

        }
    </script>
    <a href="Signin.jsp">try again</a>
    </div>
</body>
</html>