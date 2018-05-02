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
				<form name="commentForm">
					<input type="hidden" value="${ task.id }" name="taskId">
					Add comment: <input type="text" name ="description">
					<input type="button" value="Add" onclick="addComment()">
				</form>
			</c:if>
			
	<h3>Comments:</h3> <br>
	<c:if test="${ task.comments.size() == 0 }">
		<p>No comments!</p>
	</c:if>
	
	<div id="addedComment"></div>
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
	
	<script>
	function addComment() {
		var description = document.commentForm.description.value;
		var taskId = document.commentForm.taskId.value;
		var request = new XMLHttpRequest();
		
		request.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				
				var json = request.responseText;
				var newComment = JSON.parse(json);

				var html = "<table><tr>" +
					"<td>" + newComment.userId + "</td>" +
					"<td>" + newComment.dateTime.date.year + "-" + newComment.dateTime.date.month + "-" + newComment.dateTime.date.day + "T" +
							+ newComment.dateTime.time.hour + "-" + newComment.dateTime.time.minute + "-" + newComment.dateTime.time.second +"</td>" +
					"<td>" + newComment["description"] + "</td>" +
							"</tr></table><br>";
				document.getElementById("addedComment").innerHTML = html;
			}
		}
		
		request.open("GET", "http://localhost:8080/Jira/comments/add?" + "taskId=" + taskId + "&description=" + description, true);
		request.send();
	};
	</script>
	<span id="mylocation"></span>  
</body>
</html>