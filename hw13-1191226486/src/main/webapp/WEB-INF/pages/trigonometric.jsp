<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Welcome</title>
</head>
<body bgcolor="${sessionScope.pickedBgColor}">
	<table>
		<tr>
		<th>Number</th>
			<th>Sin</th>
			<th>Cos</th>
		</tr>
		<c:forEach var="value" items="${trigonometricValues}">
			<tr>
				<td>${value.number}</td>
				<td>${value.sine}</td>
				<td>${value.cosine}</td>
			</tr>
		</c:forEach>  
	</table>
</body>
</html>