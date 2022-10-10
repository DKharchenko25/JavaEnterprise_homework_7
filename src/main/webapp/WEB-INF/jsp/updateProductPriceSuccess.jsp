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
    <h2>Product Price is updated!</h2>
    <span>ID:</span><span>${product.id}</span><br/>
    <span>Price:</span><span>${product.price}</span><br/>
</div>
<br>
<a href="${pageContext.request.contextPath}/products/all_products">Back to all products</a>
</body>
</html>
