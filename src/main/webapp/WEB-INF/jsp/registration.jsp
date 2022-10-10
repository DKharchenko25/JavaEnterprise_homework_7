<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Registration</title>
    <style type="text/css">
        @import url('https://fonts.googleapis.com/css?family=PT+Sans');
        body{
            background: #fff;
            font-family: 'PT Sans', sans-serif;
        }
    </style>
</head>
<body>
<div>
    <form:form method="POST" modelAttribute="personForm">
    <h2>Registration</h2>
    <div>
        <form:input type="text" path="username" placeholder="Username" autofocus="true"></form:input>
        <form:errors path="username"></form:errors>${usernameError}
    </div>
    <div>
        <form:input type="password" path="password" placeholder="Password"></form:input>
    </div>
    <div>
        <form:input type="password" path="passwordConfirm" placeholder="Confirm your password"></form:input>
        <form:errors path="password"></form:errors>${passwordError}
    </div>
    <div>
        <form:input type="text" path="firstName" placeholder="First Name" autofocus="true"></form:input>
    </div>
    <div>
        <form:input type="text" path="lastName" placeholder="Last Name" autofocus="true"></form:input>
    </div>
    <div>
        <form:input type="text" path="phoneNumber" placeholder="Phone Number" autofocus="true"></form:input>
    </div>
</div>
<button type="submit">Register</button>
</form:form>
<a href="/main">Main Page</a>
</div>
</body>
</html>