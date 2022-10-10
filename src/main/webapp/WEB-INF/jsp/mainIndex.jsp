<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Online Shop</title>
    <style>
        body {
            background: #fff;
            font-family: 'PT Sans', sans-serif;
        }

        * {
            margin: 0px;
            padding: 0px;
            width: 100%;
            overflow: hidden;

        }

        .container {
            overflow: hidden;
            width: 800%;
            white-space: nowrap;
        }

        .container > div {
            width: 330px;
            height: 200px;
            float: left;
            margin: 0 4px 0 0;
            background-color: #E7D1CD;
        }
    </style>
</head>
<body>
<div>
    <div>
        <h1>Online Shop</h1>
        <h3><c:choose>
            <c:when test="${pageContext.request.userPrincipal.name == null}">Welcome! Log in or register, please:</c:when>
            <c:otherwise>Welcome, ${pageContext.request.userPrincipal.name}!</c:otherwise>
        </c:choose></h3>
        <sec:authorize access="!isAuthenticated()">
            <a href="${pageContext.request.contextPath}/login">Log in</a>
            <a href="${pageContext.request.contextPath}/registration">Registration</a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <h4><a href="/logout">Log out</a></h4>
        </sec:authorize>
    </div>
    <div class="container">
        <div>
            <h2>Persons:</h2><br>
            <a href="${pageContext.request.contextPath}/all_persons">All persons(for admin only)</a><br>
            <a href="${pageContext.request.contextPath}/update_first_name">Update person first name(for
                registered
                users)</a><br>
            <a href="${pageContext.request.contextPath}/update_last_name">Update person last name(for registered
                users)</a><br>
            <a href="${pageContext.request.contextPath}/update_phone_number">Update person phone number(for
                registered users)</a><br>
        </div>
        <div>
            <h2>Products:</h2><br>
            <a href="${pageContext.request.contextPath}/all_products">All products(for all users)</a><br>
            <a href="${pageContext.request.contextPath}/add_product">Add product(for admin only)</a><br>
            <a href="${pageContext.request.contextPath}/remove_product">Remove product(for admin only)</a><br>
            <a href="${pageContext.request.contextPath}/update_product_name">Update product name(for admin
                only)</a><br>
            <a href="${pageContext.request.contextPath}/update_price">Update product price(for admin
                only)</a><br>
        </div>
        <div>
            <h2>Shops:</h2><br>
            <a href="${pageContext.request.contextPath}/all_shops">All shops(for all users)</a><br>
            <a href="${pageContext.request.contextPath}/add_shop">Add shop(for admin only)</a><br>
            <a href="${pageContext.request.contextPath}/remove_shop">Remove shop(for admin only)</a><br>
            <a href="${pageContext.request.contextPath}/update_name">Update shop name(for admin only)</a><br>
        </div>
        <div>
            <h2>Carts:</h2><br>
            <a href="${pageContext.request.contextPath}/all_carts">All carts(for admin only)</a><br>
            <a href="${pageContext.request.contextPath}/person_carts">All person carts(for registered users)</a><br>
        </div>
    </div>
</div>
</body>
</html>