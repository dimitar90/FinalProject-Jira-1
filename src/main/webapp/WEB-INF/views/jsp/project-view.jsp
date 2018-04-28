<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <jsp:include page="navigation-bar.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 <link rel="stylesheet" type="text/css" href="table.css">
<title>Project info</title>
</head>
<body>
	<h1>Project view</h1>
	<table>
		<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th class="project">Project category</th>
			<th class="project">Project lead</th>
		</tr>
		<tr>	
			<td class="even">${dto.name}</td>
			<td>${dto.projectType}</td>
			<td class="project">${dto.projectCategory}</td>
			<td class="project">${dto.projectLead}</td>
		</tr>
	</table>
	
</body>
</html>