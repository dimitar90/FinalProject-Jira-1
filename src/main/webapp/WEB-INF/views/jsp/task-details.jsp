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
		
		Project: <br>
		Summary: <input type="text" value= "${task.summary}" disabled="disabled"><br>
		Description: <input type="text" value= "${task.description}" disabled="disabled"><br>
		Creator: <input type="text" value= "${task.creator.name}" disabled="disabled"><br>
		Assignee: <input type="text" value= "${task.assignee.name}" disabled="disabled"><br>
		Start date: <input type="text" value= "${task.startDate}" disabled="disabled"><br>
		Due date: <input type="text" value= "${task.dueDate}" disabled="disabled"><br>
		Priority: <input type="text" value= "${task.priority.type.value}" disabled="disabled"><br>
		Issue type: <input type="text" value= "${task.issue.type.value}" disabled="disabled"><br>
		State: <input type="text" value= "${task.state.type.value}" disabled="disabled"><br>
		
		<table>
    	 <tr>
            <td>Project</td>
            <td>
            	<input type="text" value= "${task.project.name}" disabled="disabled">
			 </td>
        </tr>
        <tr>
            <td>Summary</td>
            <td><input type="text" name="summary" /></td>
        </tr>
        
         <tr>
            <td>Description</td>
            <td><input type="text" name="description" /></td>
        </tr>
        
         <tr>
            <td>Due Date</td>
            <td><input type="date" name="dueDate" /></td>
        </tr>
        
         <tr>
            <td>Priority</td>
            <td><select name="priorityId"> 
					<c:forEach items = "${ priorities }" var = "p">
						<option value = "${ p.id }">${ p.type }</option>
					</c:forEach>
			     </select>
			</td>
        </tr>
        
         <tr>
            <td>Issue Type</td>
            <td>
	            <select name="issueTypeId"> 
					<c:forEach items= "${ issueTypes }" var = "it">
						<option value = "${ it.id }">${ it.type.getValue() }</option>
					</c:forEach>
				 </select>
			 </td>
         </tr>
         
          <tr>
            <td>Assignee</td>
            <td>
	            <select name="assigneeId"> 
					<c:forEach items= "${ assignees }" var = "a">
						<option value = "${ a.id }">${ a.name }</option>
					</c:forEach>
			     </select>
			 </td>
        		</tr>
				       <tr>
				           <td>Select a file to upload</td>
				           <td><input type="file" name="files" /></td>
				       </tr>
				        <tr>
				           <td>Select a file to upload</td>
				           <td><input type="file" name="files" /></td>
				       </tr>
				        <tr>
				           <td>Select a file to upload</td>
				           <td><input type="file" name="files" /></td>
				       </tr>
                 <tr>
            <td><input type="submit" value="Create" /></td>
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
		<c:forEach items = "${ task.comments }" var="c">
			<div>
				<span>Author: "${c.user.name}" </span> 
				<span>Wrriten on: ${ c.dateTime }</span>
				<p>Content: "${c.description }"</p>
			</div>
		</c:forEach>
	</c:if>
</body>
</html>