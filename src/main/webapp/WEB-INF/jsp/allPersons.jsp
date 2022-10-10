<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Person List</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/main">&#8592 Main Page</a>
<h1 align="center">Persons List</h1>

<div align="center">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Phone Number</th>
            <th>Username</th>
            <th>Password</th>
            <th>Remove</th>
        </tr>
        <c:forEach  items="${all}" var ="person">
            <tr>
                <td>${person.id}</td>
                <td>${person.firstName}</td>
                <td>${person.lastName}</td>
                <td>${person.phoneNumber}</td>
                <td>${person.username}</td>
                <td>${person.password}</td>
                <td><button><a href="${pageContext.request.contextPath}/remove_person?id=${person.id}">&#10060;</a></button></td>
            </tr>
        </c:forEach>
    </table>
</div>
<h2>Options:</h2>
<a href="${pageContext.request.contextPath}/add_person">Add new person</a><br>
</body>

</html>