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
	<c:set var="dtoProject" value="${ dtoProject }" />
	<c:if test="${!empty dtoProject }">
	<table>
		<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th >Project category</th>
			<th >Project lead</th>
				
		</tr>
		<tr>	
			<td class="even">${dtoProject.name}</td>
			<td>${dtoProject.projectType}</td>
			<td class="project">${dtoProject.projectCategory}</td>
			<td class="project">${dtoProject.projectLead}</td>
			<th>
					<a href="http://localhost:8080/Jira/projects/delete/${ dtoProject.id }">Delete</a>
				</th>
		</tr>
	</table>
	</c:if>
	<c:if test="${!empty tasksDto }">
	
	<h3>Tasks of the current project</h3>
	<br>
	<table>
		<tr>
			<th>Task Summary</th>
			<th>Due date</th>
			<th>Project name</th>
			<th >Priority</th>
			<th >State</th>
			<th >Creator</th>
			<th >Assigne</th>
		</tr>
		<c:forEach items="${ tasksDto }" var="t">
		<tr>	
			<td >${t.summary}</td>
			<td>${t.dueDate}</td>
			<td >${t.project.name}</td>
			<td >${t.priority.type.value}</td>
			<td>${t.state.type.value}</td>
			<td>${t.creator.name}</td>
			<td>${t.assignee.name}</td>
		</tr>
		
		</c:forEach>
	</table>
	</c:if>
		<c:if test="${empty tasksDto }">
	<h3>No tasks of this project</h3>
	</c:if>
</body>
</html>