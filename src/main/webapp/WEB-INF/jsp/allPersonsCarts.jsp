<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Person carts</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/main">&#8592 Main Page</a>
<h1 align="center">Carts List</h1>
<div align="center">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Amount of products</th>
            <th>Sum</th>
            <th>Owner</th>
            <th>Details</th>
            <th>Remove all products</th>
            <th>Remove cart</th>
        </tr>
        <c:forEach  items="${allPersonsCarts}" var ="cart">
            <tr>
                <td>${cart.id}</td>
                <td>${cart.amountOfProducts}</td>
                <td>${cart.sum}</td>
                <td>${pageContext.request.userPrincipal.name}</td>
                <td><button><a href="${pageContext.request.contextPath}/get_cart?id=${cart.id}">Details </a></button></td>
                <td><button><a href="${pageContext.request.contextPath}/remove_all?id=${cart.id}">Remove all products </a></button></td>
                <td><button><a href="${pageContext.request.contextPath}/remove_cart?id=${cart.id}">&#10060;</a></button></td>
            </tr>
        </c:forEach>
    </table>
</div>
<h2>Options:</h2>
<a href="${pageContext.request.contextPath}/add_cart">Add new cart</a><br>
</body>
</html>