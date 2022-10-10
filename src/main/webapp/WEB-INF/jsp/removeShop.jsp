<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Shop deleting form</title>
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
    <h2>Remove Shop</h2>
    <form:form action="remove_shop" method="delete" modelAttribute="shop">
        <form:label path="id">ID:</form:label>
        <form:input required="required" path="id"/><br/>
        <form:button>Delete</form:button>
    </form:form>
</div>
</body>
</html>
