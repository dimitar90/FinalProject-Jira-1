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
	
		<table>
    	 <tr>
            <td>Project</td>
            <td>
            	<input type="text" value= "${task.project.name}" disabled="disabled">
			 </td>
        </tr>
        <tr>
            <td>Summary</td>
            <td><input type="text" value= "${task.summary}" disabled="disabled"></td>
        </tr>
        
         <tr>
            <td>Description</td>
            <td><input type="text" value= "${task.description}" disabled="disabled"></td>
        </tr>
        
         <tr>
            <td>Start Date</td>
            <td><input type="text" value= "${task.startDate}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>Due Date</td>
            <td><input type="text" value= "${task.dueDate}" disabled="disabled"></td>
        </tr>
        
         <tr>
            <td>Creator</td>
            <td><input type="text" value= "${task.creator.name}" disabled="disabled"></td>
        </tr>
        
          <tr>
            <td>Assignee</td>
            <td><input type="text" value= "${task.assignee.name}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>Priority</td>
            <td><input type="text" value= "${task.priority.type.value}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>Issue Type</td>
            <td><input type="text" value= "${task.issue.type.value}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>State</td>
            <td><input type="text" value= "${task.state.type.value}" disabled="disabled"></td>
        </tr>
    </table>
		
			<c:if test= '${not empty sessionScope.user}'>
				<form action="../../comments/add/${ task.id }" method="post">
					Add comment: <input type="text" name ="description">
					<button type="submit">Add</button>
				</form>
			</c:if>
			
	<h3>Comments:</h3> <br>
	<c:if test="${ task.comments.size() == 0 }">
		<p>No comments!</p>
	</c:if>
	
	<c:if test="${ task.comments.size() > 0 }">
	<table>
			<tr>
				<th>Author</th>
				<th>Wrriten on</th>
				<th>Content</th>
			</tr>
		
			<c:forEach items = "${ task.comments }" var="c">
			<tr>
				<td>${c.user.name}</td>
				<td>${ c.dateTime }</td>
				<td>${c.description }</td>
			</tr> <br>
		</c:forEach>
	</table>
	</c:if>
</body>
</html>