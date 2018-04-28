<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
 
 <jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
	<h3>Create task</h3><br>
	<form action="./tasks/create" method="post" enctype="multipart/form-data">
		Project: <select name="project"> 
		<c:forEach items= "${ projects }" var = "p">
			<option value = "${ p.id }">${ p.name }</option>
		</c:forEach>
			     </select><br>
		Summary: <input type="text" name="summary"><br>
		Description: <input type="text" name="description"><br>
		Due date: <input type="date" name="dueDate"><br>
		Priority:  <select name="priority"> 
		<c:forEach items = "${ priorities }" var = "p">
			<option value = "${ p.id }">${ p.type }</option>
		</c:forEach>
			     </select><br>
		Issue Type:  <select name="issueType"> 
		<c:forEach items= "${ issueTypes }" var = "it">
			<option value = "${ it.id }">${ it.type.getValue() }</option>
		</c:forEach>
			     </select><br>
		Assignee:  <select name="assignee"> 
		<c:forEach items= "${ assignees }" var = "a">
			<option value = "${ a.id }">${ a.name }</option>
		</c:forEach>
			     </select><br>
		Images: <input type="file" name="file[]" multiple="multiple"/><br>
		<button type="submit">Create</button> 
	</form>
</body>
</html>