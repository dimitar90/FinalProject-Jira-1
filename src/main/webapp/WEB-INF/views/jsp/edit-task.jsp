<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>

<head>
<link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<link rel="stylesheet"
	href="<c:url value="/css/create-task-form.css" />">
</head>

<jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
	<s:form action="${editTaskDto.id}" method="POST">
		<h3>Edit task</h3>
		<label> <span> Project: </span> <input type="text"
			value="${editTaskDto.project.name}" disabled="disabled">
		</label>
		<label> <span> Summary: </span> <input type="text"
			value="${editTaskDto.summary}" disabled="disabled">
		</label>

		<label> <span>Description :</span> <textarea
				name="description" disabled="disabled">${editTaskDto.description}</textarea>
		</label>
		<label> <span>Start date:</span> <input type="text"
			value="${editTaskDto.startDate}" disabled="disabled">
		</label>
		<label> <span>Due date:</span> <input type="text"
			value="${editTaskDto.dueDate}" disabled="disabled">
		</label>
		<label> <span>Creator:</span> <input type="text"
			value="${editTaskDto.creator.name}" disabled="disabled">
		</label>
		<label> <span>Assignee:</span> <input type="text"
			value="${editTaskDto.assignee.name}" disabled="disabled">
		</label>

		<label> <span>Priority:</span> <input type="text"
			value="${editTaskDto.priority.type.value}" disabled="disabled">
		</label>

		<label> <span>Issue type:</span> <input type="text"
			value="${editTaskDto.issue.type.value}" disabled="disabled">
		</label>
		<label> <span>Issue type:</span> <input type="text"
			value="${editTaskDto.issue.type.value}" disabled="disabled">
		</label>
		<label> <span> State: </span> <select name="newStateId">
				<c:forEach items="${ states }" var="s">
					<option value="${ s.id }">${ s.type.getValue() }</option>
				</c:forEach>
		</select>
		</label>
		<input type="hidden" name="editTaskId" value="${ editTaskDto.id}">

		<input id="createButton" type="submit" class="button" value="Edit" />
	</s:form>
</body>
</html>