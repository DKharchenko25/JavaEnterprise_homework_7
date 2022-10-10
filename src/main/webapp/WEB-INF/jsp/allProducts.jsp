<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Products List</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/main">&#8592 Main Page</a>
<h1 align="center">Products List</h1>

<div align="center">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Shop ID</th>
        </tr>
        <c:forEach  items="${all}" var ="product">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.shopId}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>

</html>