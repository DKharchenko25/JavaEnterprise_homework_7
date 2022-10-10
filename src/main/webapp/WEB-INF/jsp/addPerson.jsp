<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>User Registration Form</title>
    <style type="text/css">
        label {
            display: inline-block;
            width: 200px;
            margin: 5px;
            text-align: left;
        }
        input[type=text], input[type=password], select {
            width: 200px;
        }
        button {
            padding: 10px;
            margin: 10px;
        }
    </style>
</head>
<body>
<div align="center">
    <h2>User Registration</h2>
    <form:form action="add_person" method="post" modelAttribute="person">
        <form:label path="firstName">First Name:</form:label>
        <form:input required="required" path="firstName"/><br/>
        <form:label path="lastName">Last Name:</form:label>
        <form:input required="required" path="lastName"/><br/>
        <form:label path="phoneNumber">Phone Number:</form:label>
        <form:input required="required" path="phoneNumber"/><br/>
        <form:label path="username">Username:</form:label>
        <form:input required="required" path="username"/><br/>
        <form:label path="password">Password:</form:label>
        <form:input required="required" path="password"/><br/>
        <form:button>Register</form:button>
    </form:form>
</div>
</body>
</html>