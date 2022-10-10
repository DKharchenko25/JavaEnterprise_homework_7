<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
    <style type="text/css">
        body{
            background: #fff;
            font-family: 'PT Sans', sans-serif;
        }
    </style>
</head>

<body>
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/main"); %>
</sec:authorize>
<div>
    <form method="POST" action="/login">
        <h2>Log in</h2>
        <div>
            <input name="username" type="text" placeholder="Username" autofocus="true"/><br>
            <input name="password" type="password" placeholder="Password"/><br>
            <button type="submit">Log In</button>
            <h4><a href="/registration">Registration</a></h4>
        </div>
    </form>
</div>

</body>
</html>