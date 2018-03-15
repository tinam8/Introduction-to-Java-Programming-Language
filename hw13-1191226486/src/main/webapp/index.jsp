<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Welcome</title>
</head>
<body bgcolor="${sessionScope.pickedBgColor}">
	<a href="colors.jsp">Background color chooser</a>
	<br>
	<a href="trigonometric?a=0&b=90">Calculate trigonometric
		functions</a>
	<br>
	<form action="trigonometric" method="GET">
		Početni kut:<br>
		<input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br>
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form><br>
	<a href="stories/funny.jsp">Story</a> <br>
	<a href="reports.jsp">Report image</a><br>
	<a href="powers?a=1&b=100&n=3">Powers</a><br>
	<a href="appinfo.jsp">App Info</a> <br>
	<a href="glasanje">Voting</a>
</body>
</html>