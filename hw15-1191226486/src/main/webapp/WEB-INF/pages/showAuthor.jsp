<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<style>
.error {
	color: red;
}
</style>



<body>

	<header>
		<jsp:include page="/WEB-INF/pages/header.jsp" />
	</header>

	<br>
	<h2>Autor: ${blogUser.nick}</h2>

	<c:choose>
		<c:when test="${blogEntries == null || empty blogEntries}">
			<h4>Nema postova</h4>
		</c:when>
		<c:otherwise>
			<h4>Popis postova</h4>
			<c:forEach var="blogEntry" items="${blogEntries}">
				<li><a href="${blogUser.nick}/${blogEntry.id}">${blogEntry.title}<a></li>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<c:if
		test="${sessionScope.get('current.user.id') != null && sessionScope.get('current.user.id')== blogUser.id}">
		<c:if test=""></c:if>
		<a href="${blogUser.nick}/new">Dodaj novi post</a>
	</c:if>

</body>
</html>