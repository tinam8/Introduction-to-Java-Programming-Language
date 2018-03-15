<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>OS usage</title>
</head>
<body bgcolor="${sessionScope.pickedBgColor}">
	<p>Here are the results of OS usage in survey that we completed.”</p>
	<c:url value="/reportImage" var="imgUrl"/>
   	<img alt="OS usage" src="${imgUrl}">
</body>
</html>