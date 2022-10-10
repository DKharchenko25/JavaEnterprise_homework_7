<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Cart</title>
    <style type="text/css">
        span {
            display: inline-block;
            width: 200px;
            text-align: left;
        }
    </style>
</head>
<body>
<div align="justify">
    <h2>Cart</h2>
    <span>ID:</span><span>${cart.id}</span><br/>
    <span>Amount of products:</span><span>${cart.amountOfProducts}</span><br/>
    <span>Sum:</span><span>${cart.sum}</span><br/>
    <span>Owner:</span>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Phone Number</th>
        </tr>
        <tr>
            <th>${cart.person.id}</th>
            <th>${cart.person.firstName}</th>
            <th>${cart.person.lastName}</th>
            <th>${cart.person.phoneNumber}</th>
        </tr>
    </table>
    <span>Products:</span>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Shop ID</th>
            <th>Remove</th>
        </tr>
        <c:forEach items="${cart.products}" var="item">
            <tr>
                <td><c:out value="${item.id}"/></td>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.price}"/></td>
                <td><c:out value="${item.shop.getId()}"/></td>
                <td><button><a href="${pageContext.request.contextPath}/remove_from_cart?cartId=${cart.id}&productId=${item.id}">&#10060;</a></button></td>
            </tr>
        </c:forEach>
    </table>
</div>
<br>
<a href="${pageContext.request.contextPath}/add_to_cart">Add product</a><br>
<a href="${pageContext.request.contextPath}/person_carts">My carts</a>
</body>
</html>
