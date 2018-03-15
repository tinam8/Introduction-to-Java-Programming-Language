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

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="pollOption" items="${pollOptions}">
				<tr>
					<td>${pollOption.optionTitle}</td>
					<td>${pollOption.votes}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="/voting-app/servleti/glasanje-grafika?pollID=${pollID}" width="400"
		height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="/voting-app/servleti/glasanje-xls?pollID=${pollID}">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="winner" items="${winners}">
			<li><a href="${winner.optionLink}" target="_blank">${winner.optionTitle}</a></li>
		</c:forEach>

	</ul>
</body>
</html>