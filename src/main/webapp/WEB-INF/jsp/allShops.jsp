<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Shops List</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/main">&#8592 Main Page</a>
<h1 align="center">Shops List</h1>
<div align="center">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
        </tr>
        <c:forEach  items="${all}" var ="shop">
            <tr>
                <td>${shop.id}</td>
                <td>${shop.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>

</html>