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
    <h2>Shop Name is updated!</h2>
    <span>ID:</span><span>${shop.id}</span><br/>
    <span>Name:</span><span>${shop.name}</span><br/>
</div>
<br>
<a href="${pageContext.request.contextPath}/all_shops">Back to all shops</a>
</body>
</html>
