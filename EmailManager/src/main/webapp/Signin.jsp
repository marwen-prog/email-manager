<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Athantification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        form {
            width: 50%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin: 10px 0;
            font-weight: bold;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: none;
            border-radius: 3px;
            background-color: #337ab7;
            color: #fff;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #135682;
        }
    </style>
</head>
<body>
    <h1>Authentification</h1>
    <form action="signin" method="post">
        <label for="username">username :</label>
        <input type="text" id="username" name="username">

        <label for="password">password :</label>
        <input type="password" id="password" name="password">

        <input type="submit" value="Connexion">
        <a href="signup.jsp">create user </a>
    </form>
  
</body>
</html>