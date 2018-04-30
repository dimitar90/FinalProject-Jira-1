	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<jsp:include page="navigation-bar.jsp"></jsp:include>
	<c:set var="task" value="${ task }" />
<body>
	<h3>Task details</h3><br>
		Images:
		<c:if test="${task.imageUrls.size() == 0 }">
			No images. <br>
		</c:if>
		<c:if test="${task.imageUrls.size() > 0 }">
			<br>
			<c:forEach items= "${task.imageUrls }" var="url">
				<img src="data:image/jpeg;base64,${url}"/>		
			</c:forEach>
		</c:if>
		Project: <input type="text" value= "${task.project.name}" disabled="disabled"><br>
		Summary: <input type="text" value= "${task.summary}" disabled="disabled"><br>
		Description: <input type="text" value= "${task.description}" disabled="disabled"><br>
		Creator: <input type="text" value= "${task.creator.name}" disabled="disabled"><br>
		Assignee: <input type="text" value= "${task.assignee.name}" disabled="disabled"><br>
		Start date: <input type="text" value= "${task.startDate}" disabled="disabled"><br>
		Due date: <input type="text" value= "${task.dueDate}" disabled="disabled"><br>
		Priority: <input type="text" value= "${task.priority.type.value}" disabled="disabled"><br>
		Issue type: <input type="text" value= "${task.issue.type.value}" disabled="disabled"><br>
		State: <input type="text" value= "${task.state.type.value}" disabled="disabled"><br>
			<c:if test= '${not empty sessionScope.user}'>
				<a href="./createtaskcomment?taskId=${ task.id }">Add new comment</a>
			</c:if>
	<h3>Comments:</h3> <br>
	<c:if test="${ task.comments.size() == 0 }">
		<p>No comments!</p>
	</c:if>
	<c:if test="${ task.comments.size() > 0 }">
		<c:forEach items = "${ task.comments }" var="com">
			<div>
				<span>Author: "${com.user.name}" </span> 
				<span>Wrriten on: ${ com.dateTime }</span>
				<p>Content: "${com.description }"</p>
			</div>
		</c:forEach>
	</c:if>
</body>
</html>