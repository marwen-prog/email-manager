<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ListeEmailManager</title>
    <style>
        /* Adding CSS styles for a basic layout */
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            width: 50%;
            margin: 0 auto;
        }
        input[type="email"],
        input[type="submit"] {
            margin: 10px 0;
            padding: 10px;
            width: 100%;
            box-sizing: border-box;
        }
        .btn-container {
            display: flex;
            justify-content: space-between;
        }
        .btn-container button {
            width: 48%;
            padding: 10px;
            box-sizing: border-box;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin: 5px 0;
            padding: 10px;
            background-color: #f4f4f4;
            border-radius: 5px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<%List<String> emails = new ArrayList<String>(); %>
<% emails = (ArrayList<String>) request.getAttribute("emails"); %>
<div class="container">
    <h1>ListeEmailManager</h1>
    <ul>
        <% if(emails != null) {
            for(int i=0; i < emails.size(); i++) {
                out.println("<li>" + emails.get(i) + "</li>");
            }
        } %>
    </ul>
    <form action="ListEmailServlet" method="post">
        <label for="mail">Enter your email address:</label>
        <input type="email" id="mail" name="email" required>

        <div class="btn-container">
            <button name="action" value="add" type="submit">Subscribe</button>
            <button name="action" value="del" type="submit">Unsubscribe</button>
        </div>
    </form>
</div>
</body>
</html>