<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

	<style>
		.error {
			color: red;
		}
		
	</style>

  <body>

	<a href="main">Pocetna</a> <br>
	<h2>Stvori post</h2>
	<form action="/webapp-bazaorm/servleti/newEntry" method="POST">
		<label>Naslov</label><br>
		<input type="text" name="title" value="${blogEntryForm.title}">  
		<span class="error"><c:out value="${errors['Naslov']}"></c:out></span>  <br>
		<label>Tekst</label><br>
		<textarea rows="10" cols=50" name="text">${blogEntryForm.text}</textarea>
		<span class="error"><c:out value="${errors['Tekst']}"></c:out></span> <br>
		<input type="hidden" name="blogUserID" value="${blogEntryForm.blogUserID}">
		<button type="submit">Spremi</button>
	</form>

  </body>
</html>