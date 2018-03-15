<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>

<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
		<c:forEach var="pollOption" items="${pollOptions}">
			<li><a href="glasanje-glasaj?id=${pollOption.id}&pollID=${pollOption.pollID}">${pollOption.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>