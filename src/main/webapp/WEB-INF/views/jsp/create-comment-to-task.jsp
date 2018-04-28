<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
 <jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
	<h3>Add comment</h3><br>
	<form action="./createtaskcomment" method="post">
		Comment: <input type="text" name="description">
		<input type="hidden" name="taskId" value="${taskId}">
		<button type="submit">Create</button>
	</form>
</body>
</html>