<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="navigation-bar.jsp"></jsp:include>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <link rel="stylesheet" type="text/css" href="table.css">
<title>View all projects</title>
</head>
<body>
	<h1>All projects</h1>
	<table>
		<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th class="project">Project category</th>
			<th class="project">Project lead</th>
		</tr>
		<c:forEach items="${ allProjects }" var="p">
		<tr>	
			<td>
			<a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a>
			</td> 
			<td>${p.projectType}</td>
			<td class="project">${p.projectCategory}</td>
			<td class="project">${p.projectLead}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>