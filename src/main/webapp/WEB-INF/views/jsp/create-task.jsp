<head>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 	<meta charset="UTF-8">
    <title>Create task</title>
	<link rel="stylesheet" href="<c:url value="/css/style.css" />">
	<link rel="stylesheet" href="<c:url value="/css/create-task-form.css" />">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<c:set var="maxImageCount" value="2"/>
</head>
</head>

 <jsp:include page="navigation-bar.jsp"></jsp:include>
 
<body>
<s:form method="POST" action="create" enctype="multipart/form-data">
    <h3>Create task</h3>
    <label>
        <span>
           	Project:
        </span>
         <select name="projectId"> 
					<c:forEach items= "${ projects }" var = "p">
						<option value = "${ p.id }">${ p.name }</option>
					</c:forEach>
		</select>
    </label>
    <label>
        <span>
            Summary:
        </span>
        <input id="name" type="text" name="summary" placeholder="Add a summary" required/>
    </label>

    <label>
        <span>Description :</span>
        <textarea id="message" name="description" placeholder="Add a descripion" required></textarea>
    </label>
   
    <label>
        <span>
           	Priority:
        </span>
         <select name="priorityId"> 
					<c:forEach items = "${ priorities }" var = "p">
						<option value = "${ p.id }">${ p.type }</option>
					</c:forEach>
		</select>
    </label>
     <label>
        <span>
           	Issue Type:
        </span>
         <select name="issueTypeId"> 
					<c:forEach items= "${ issueTypes }" var = "it">
						<option value = "${ it.id }">${ it.type.getValue() }</option>
					</c:forEach>
		</select>
    </label>
     <label>
        <span>
           	Assignee:
        </span>
         <select name="assigneeId"> 
					<c:forEach items= "${ assignees }" var = "a">
						<option value = "${ a.id }">${ a.name }</option>
					</c:forEach>
		</select>
    </label>
    <label>
        <span>
           	Due Date:
        </span>
          <input id="#dueDateId" type="date" name="dueDate" placeholder="Add a summary" required/>
    </label>
    <br>
     <label id="imageLabel">
        <span>
           	Select images:
        </span>
        	<c:forEach begin="0" end="${ maxImageCount }">
        		<input type="hidden" name="MAX_FILE_SIZE" value="5242880" /> 
        	    <input class="selected-images" type="file" name="files" accept="image/jpeg, image/png, image/jpg, image/bmp" />
        	</c:forEach>
    </label>
    <input id="createButton" type="submit" class="button" value="Create" />
</s:form>
</body>
</html>