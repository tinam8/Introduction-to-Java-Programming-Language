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

	<c:choose>
		<c:when test="${sessionScope.get('current.user.id') == null}">
			<span class="error"><c:out value="${error}"></c:out></span>
			<form action="main" method="POST">
				<label>Nadimak</label> <br> <input type="text" name="nick"
					value="${loginForm.nick}"> <br> <label>Sifra</label> <br>
				<input type="password" name="password"> <br>
				<button type="submit">Ulogiraj se</button>
			</form>

			<a href="register">Registriraj se</a>
			<br>
			<br>
		</c:when>
	</c:choose>

	<c:choose>
		<c:when test="${authors == null}">
			<h4>Nema registriranih autora</h4>
		</c:when>
		<c:otherwise>
			<h4>Popis autora</h4>
			<c:forEach var="blogUser" items="${authors}">
				<li><a href="author/${blogUser.nick}">${blogUser.nick}<a></li>
			</c:forEach>
		</c:otherwise>
	</c:choose>

</body>
</html>