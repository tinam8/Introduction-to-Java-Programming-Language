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
	<c:if
		test="${sessionScope.get('current.user.id') != null && sessionScope.get('current.user.id')== blogUser.id}">
		<span><a href="edit/${blogEntry.id}">Uredi</a></span>
	</c:if>
	<h3>Naslov: ${blogEntry.title}</h3>
	<p>${blogEntry.text}</p>

	<c:choose>
		<c:when test="${blogComments == null || empty blogComments}">
			<h4>Nema komentara</h4>
		</c:when>
		<c:otherwise>
			<h4>Komentari</h4>
			<c:forEach var="blogComment" items="${blogComments}">
				<div>
					<span>${blogComment.usersEMail}</span>
					<span>${blogComment.message}</span>
					<span>${blogComment.postedOn}</span>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<form action="/webapp-bazaorm/servleti/newComment" method="POST">
		<label>Email</label> <br>
		<input type="email" name="email" size="50" value="${blogCommentForm.usersEMail}">
		<span class="error"><c:out value="${errors['email']}"></c:out></span><br>
		<label>Komentar</label> <br>
		<textarea name="message" rows="5" cols="50">${blogCommentForm.message}</textarea>
		<span class="error"><c:out value="${errors['message']}"></c:out></span><br>
		<input type="hidden" name="blogEntryID" value="${blogEntry.id}">
		<input type="hidden" name="nick" value="${blogUser.nick }">
		<input type="submit" name="metoda" value="Komentiraj"> 
	</form>

</body>
</html>