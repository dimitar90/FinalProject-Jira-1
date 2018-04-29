<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
 
 <jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
	<h3>Create task</h3><br>
	<s:form method="POST" action="create" enctype="multipart/form-data">
    <table>
    	 <tr>
            <td>Project</td>
            <td>
            <select name="projectId"> 
					<c:forEach items= "${ projects }" var = "p">
						<option value = "${ p.id }">${ p.name }</option>
					</c:forEach>
			 </select>
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
	</s:form>
</body>
</html>