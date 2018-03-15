<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<a href="/webapp-bazaorm/servleti/main">Pocetna</a>
<c:choose>
	<c:when test="${sessionScope.get('current.user.id') != null}">
		<h4>
			Pozdrav:
			<c:out value="${sessionScope.get('current.user.first')}" />
			<c:out value="${sessionScope.get('current.user.last')}" />
			<a href="/webapp-bazaorm/servleti/logout">Log Out</a>
		</h4>
	</c:when>
	<c:otherwise>
		<h4>Nitko nije ulogiran</h4>
	</c:otherwise>

</c:choose>
