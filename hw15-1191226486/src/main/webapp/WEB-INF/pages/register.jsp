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
	<h2>Registriraj se se</h2>
	<form action="register" method="POST">
		<label>Ime</label><br>
		<input type="text" name="firstName" value="${blogUserForm.firstName}">  
		<span class="error"><c:out value="${errors['firstName']}"></c:out></span>  <br>
		<label>Prezime</label><br>
		<input type="text" name="lastName" value="${blogUserForm.lastName}">
		<span class="error"><c:out value=" ${errors['lastName']}"></c:out></span> <br>
		<label>Nadimak</label><br>
		<input type="text" name="nick" value="${blogUserForm.nick}"> 
		<span class="error"><c:out value="${errors['nick']}"></c:out></span><br>
		<label>Email</label><br>
		<input type="email" name="email" value="${blogUserForm.email}">
		<span class="error"><c:out value="${errors['email']}"></c:out></span> <br>
		<label>Sifra</label><br>
		<input type="password" name="password">
		<span class="error"><c:out value="${errors['password']}"></c:out></span> <br>
		<button type="submit">Registriraj se</button>
	</form>

  </body>
</html>