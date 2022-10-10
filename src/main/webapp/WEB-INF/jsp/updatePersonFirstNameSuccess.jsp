<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Updating Success</title>
    <style type="text/css">
        span {
            display: inline-block;
            width: 200px;
            text-align: left;
        }
    </style>
</head>
<body>
<div align="center">
    <h2>Person First Name is updated!</h2>
    <span>Username:</span><span>${pageContext.request.userPrincipal.name}</span><br/>
    <span>First Name:</span><span>${person.firstName}</span><br/>
</div>
<br>
<a href="${pageContext.request.contextPath}/main">Back to main page</a>
</body>
</html>
