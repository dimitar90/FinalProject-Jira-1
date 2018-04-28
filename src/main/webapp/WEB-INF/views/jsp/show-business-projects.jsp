<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="navigation-bar.jsp"></jsp:include>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <link rel="stylesheet" type="text/css" href="table.css">
<title>Business projects</title>
</head>
<body>
	<h1>All business projects</h1>
	<table>
		<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th class="project">Project category</th>
			<th class="project">Project lead</th>
		</tr>
		<c:forEach items="${ businessProjects }" var="busProject">
		<tr>	
			<td class="even">${busProject.name}</td>
			<td>${busProject.projectType}</td>
			<td class="project">${busProject.projectCategory}</td>
			<td class="project">${busProject.projectLead}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>