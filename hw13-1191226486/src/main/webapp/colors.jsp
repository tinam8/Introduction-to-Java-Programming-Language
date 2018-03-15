<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
<title>Welcome</title>
</head>
<body bgcolor="${sessionScope.pickedBgColor}">
	<ul>
		<li><a href="/webapp2/setcolor?color=cyan">CYAN</a></li>
		<li><a href="/webapp2/setcolor?color=red">RED</a></li>
		<li><a href="/webapp2/setcolor?color=white">WHITE</a></li>
		<li><a href="/webapp2/setcolor?color=green">GREEN</a></li>
	</ul>
</body>
</html>
