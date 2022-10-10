<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Product to cart Form</title>
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
<h1>My carts</h1>
<div>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Amount of products</th>
            <th>Sum</th>
            <th>Owner</th>
        </tr>
        <c:forEach  items="${allCarts}" var ="cart">
            <tr>
                <td>${cart.id}</td>
                <td>${cart.amountOfProducts}</td>
                <td>${cart.sum}</td>
                <td>${pageContext.request.userPrincipal.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<h1>Products List</h1>
<div>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Shop ID</th>
        </tr>
        <c:forEach  items="${allProducts}" var ="product">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.shopId}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    <h2>Add product by id:</h2>
    <form:form action="add_to_cart" method="put" modelAttribute="product">
        <form:label path="cartId">Cart ID:</form:label>
        <form:input required="required" path="cartId"/><br/>
        <form:label path="id">Product ID:</form:label>
        <form:input required="required" path="id"/><br/>
        <form:button>Add</form:button>
    </form:form>
</div>
</body>
</html>