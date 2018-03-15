<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%!/**
				Method that calculates how long applications is running and returns string of duration.
				@return string that holds information about running duration
			
			*/
	public String getTimeDiff() {
		long appStarted = System.currentTimeMillis();
		long diff = appStarted - (long) getServletContext().getAttribute("appStarted");
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		StringBuilder sb = new StringBuilder();
		sb.append(diffDays + " days, ");
		sb.append(diffHours + " hours, ");
		sb.append(diffMinutes + " minutes, ");
		sb.append(diffSeconds + " seconds.");
		return sb.toString();
	}%>


<!DOCTYPE html>
<html>
<head>
<title>Welcome</title>
</head>
<body bgcolor="${sessionScope.pickedBgColor}">
	<p>
		Application is running for
		<%=getTimeDiff()%>
	</p>
</body>
</html>