<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="navigation-bar.jsp"></jsp:include>
<c:set var="user" value="${ user }" />
 <link rel="stylesheet" type="text/css" href="table.css">
<body>
	<h3>All tasks:</h3>
	<br>
	<form style="display: inline-block"  action="./filtertasks" method="post">
	 <p>Filter by issue type</p>
	 	<c:forEach items="${ issueTypes }" var="it" >
	 	 <input type="checkbox" name="selected" value= "${it.type.value}"> ${it.type.value} <br>
	 	</c:forEach>
	  	 <input type="submit" value="Filter">
	 </form> 
	 <form style="display: inline-block"  action="./gettasksbetweentwodates" method="post">
	  <p>Show tasks between</p>
	 	 <input type="date" name="firstDate"> 
	 	 <br> and <br> 
	 	 <input type="date" name="secondDate"><br>
	 	 due date
	  	 <input type="submit" value="Show">
	 </form> 
	 
	<table>
		<tr>
			<th>Project</th>
			<th>Summary</th>
			<th>Description</th>
			<th>Creator</th>
			<th>Assignee</th>
			<th>Due date</th>
			<th>Priority</th>
			<th>Issue type</th>
			<th>State</th>
			<th>Details</th>
			<th>Add comment</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		
		<c:forEach items="${ tasks }" var="t">
			<tr>
				<td>${t.project.name}</td>
				<td>${t.summary}</td>
				<td>${t.description}</td>
				<td>${t.creator.name}</td>
				<td>${t.assignee.name}</td>
				<td>${t.dueDate}</td>
				<td>${t.priority.type.value}</td>
				<td>${t.issue.type.value}</td>
				<td>${t.state.type.value}</td>
				<td>
					<a href="./viewtaskdetails?taskId=${ t.id }">Show details</a>
				</td>
				<c:if test="${ user != null }">
					<td>
						<a href="./createtaskcomment?taskId=${ t.id }">Add comment</a>
					</td>
					<c:if test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
						<td>
							<a href="./edittask?taskId=${ t.id }">Edit</a>
						</td>
						<td>
							<a href="./deletetask?taskId=${ t.id }">Delete</a>
						</td>
					</c:if>
				</c:if>
			</tr> <br>
		</c:forEach>
	</table>
</body>
</html>