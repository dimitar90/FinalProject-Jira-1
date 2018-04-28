<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
 <jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
	<h3>Edit task</h3><br>
	<form action="./edittask" method="post">
		<c:set var="editTaskDto" value="${ editTaskDto }" />
		Project: <input type="text" value= "${task.project.name}" disabled="disabled"><br>
		Summary: <input type="text" value= "${editTaskDto.summary}" disabled="disabled"><br>
		Description: <input type="text" value= "${editTaskDto.description}" disabled="disabled"><br>
		Creator: <input type="text" value= "${editTaskDto.creator.name}" disabled="disabled"><br>
		Assignee: <input type="text" value= "${editTaskDto.assignee.name}" disabled="disabled"><br>
		Start date: <input type="text" value= "${editTaskDto.startDate}" disabled="disabled"><br>
		Due date: <input type="text" value= "${editTaskDto.dueDate}" disabled="disabled"><br>
		Priority: <input type="text" value= "${editTaskDto.priority.type.value}" disabled="disabled"><br>
		Issue type: <input type="text" value= "${editTaskDto.issue.type.value}" disabled="disabled"><br>
		<input type="hidden"name="editTaskId" value= "${ editTaskDto.id}">
		State:  <select name="state"> 
				<c:forEach items = "${ states }" var = "s">
					<option value = "${ s.id }">${ s.type.getValue() }</option>
				</c:forEach>
				  </select><br>
		<button type="submit">Edit</button> 
	</form>
</body>
</html>