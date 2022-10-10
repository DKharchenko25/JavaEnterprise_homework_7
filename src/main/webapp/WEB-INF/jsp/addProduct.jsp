<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Product Addition Form</title>
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
    <h2>Product addition</h2>
    <form:form action="add_product" method="post" modelAttribute="product">
        <form:label path="name">Name:</form:label>
        <form:input required="required" path="name"/><br/>

        <form:label path="price">Price:</form:label>
        <form:input required="required" path="price"/><br/>

        <form:label path="shopId">Shop ID:</form:label>
        <form:input required="required" path="shopId"/><br/>

        <form:button>Add</form:button>
    </form:form>
</div>


<div align="center">
    <h2>Available shops:</h2>
    <a href="${pageContext.request.contextPath}/all_shops">All shops</a><br>

</div>
</body>
</html>