<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="nav-bar-main.jsp"></jsp:include>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

 <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
<title>Software projects</title>
</head>
<body>
	<h1>All software projects</h1>
	<table>
		<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th class="project">Project category</th>
			<th class="project">Project lead</th>
		</tr>
		<c:forEach items="${ softwareProjects }" var="softProject">
		<tr>	
			<td class="even">${softProject.name}</td>
			<td>${softProject.projectType}</td>
			<td class="project">${softProject.projectCategory}</td>
			<td class="project">${softProject.projectLead}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>