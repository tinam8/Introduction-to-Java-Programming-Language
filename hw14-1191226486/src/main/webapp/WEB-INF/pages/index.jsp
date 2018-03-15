<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>

	<%
		List<Poll> polls = (List<Poll>) request.getAttribute("polls");
		if (polls.isEmpty()) {
	%>
		<b>There is no polls</b>
	<%
		} else {
	%>
	<b>Active Polls:</b>
	<br>

	<c:forEach var="poll" items="${polls}">
		<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
	</c:forEach>
	<%
		}
	%>


</body>
</html>

