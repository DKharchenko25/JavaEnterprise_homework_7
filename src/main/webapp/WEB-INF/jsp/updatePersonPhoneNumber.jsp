<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>User Registration Form</title>
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
  <h2>Update Person</h2>
  <form:form action="update_phone_number" method="put" modelAttribute="person">
    <form:label path="phoneNumber">Phone Number:</form:label>
    <form:input required="required" path="phoneNumber"/><br/>
    <form:button>Update</form:button>
  </form:form>
</div>
</body>
</html>
