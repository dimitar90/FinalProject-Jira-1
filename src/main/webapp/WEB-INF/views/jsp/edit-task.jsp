<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<head>
   <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>

<jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
	<h3>Edit task</h3><br>
	<form action="${editTaskDto.id}" method="post">
		<table>
    	 <tr>
            <td>Project</td>
            <td>
            	<input type="text" value= "${editTaskDto.project.name}" disabled="disabled">
			 </td>
        </tr>
        <tr>
            <td>Summary</td>
            <td><input type="text" value= "${editTaskDto.summary}" disabled="disabled"></td>
        </tr>
        
         <tr>
            <td>Description</td>
            <td><input type="text" value= "${editTaskDto.description}" disabled="disabled"></td>
        </tr>
        
         <tr>
            <td>Start Date</td>
            <td><input type="text" value= "${editTaskDto.startDate}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>Due Date</td>
            <td><input type="text" value= "${editTaskDto.dueDate}" disabled="disabled"></td>
        </tr>
        
         <tr>
            <td>Creator</td>
            <td><input type="text" value= "${editTaskDto.creator.name}" disabled="disabled"></td>
        </tr>
        
          <tr>
            <td>Assignee</td>
            <td><input type="text" value= "${editTaskDto.assignee.name}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>Priority</td>
            <td><input type="text" value= "${editTaskDto.priority.type.value}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>Issue Type</td>
            <td><input type="text" value= "${editTaskDto.issue.type.value}" disabled="disabled"></td>
        </tr>
        
        <tr>
            <td>State</td>
            <td><select name="newStateId"> 
					<c:forEach items = "${ states }" var = "s">
						<option value = "${ s.id }">${ s.type.getValue() }</option>
					</c:forEach>
				 </select>
			</td>
        </tr>
    </table>
		  <input type="hidden"name="editTaskId" value= "${ editTaskDto.id}">
          <button type="submit">Edit</button>
	</form>
</body>
</html>