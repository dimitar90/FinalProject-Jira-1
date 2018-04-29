<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="table.css">
<jsp:include page="navigation-bar.jsp"></jsp:include>
<c:set var="user" value="${ user }" />

<body>
	<h3>All tasks:</h3>
	<br>
	<form style="display: inline-block"  action="./filtertasks" method="post">
	 <p>Filter by issue type</p>
	 	<c:forEach items="${ issueTypes }" var="it" >
	 	 <input type="checkbox" name="selected" value= "${it.type.value}"> ${it.type.value} <br>
	 	</c:forEach>
	  	 <input type="submit" value="Filter">
	 </form> 
	 <form style="display: inline-block"  action="./gettasksbetweentwodates" method="post">
	  <p>Show tasks between</p>
	 	 <input type="date" name="firstDate"> 
	 	 <br> and <br> 
	 	 <input type="date" name="secondDate"><br>
	 	 due date
	  	 <input type="submit" value="Show">
	 </form> 
	 
	<table>
		<tr>
			<th>Project</th>
			<th>Summary</th>
			<th>Assignee</th>
			<th>Due date</th>
			<th>Priority</th>
			<th>State</th>
			<th>Details</th>
			<th>Add comment</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		
		<c:forEach items="${ tasks }" var="t">
			<tr>
				<%-- <td>${t.project.name}</td> --%>
				<td>${t.summary}</td>
				<td>${t.assignee.name}</td>
				<td>${t.dueDate}</td>
				<td>${t.priority.type.value}</td>
				<td>${t.state.type.value}</td>
				<td>
					<a href="./viewtaskdetails?taskId=${ t.id }">Show details</a>
				</td>
				<c:if test="${not empty user}">
					<td>
						<a href="./createtaskcomment?taskId=${ t.id }">Add comment</a>
					</td>
					<c:if test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
						<td>
							<a href="./edittask?taskId=${ t.id }">Edit</a>
						</td>
						<td>
							<a href="./deletetask?taskId=${ t.id }">Delete</a>
						</td>
					</c:if>
				</c:if>
			</tr> <br>
		</c:forEach>
	</table>
	
	
	 <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${currentPage != 1}">
    	<td><a href="all/${currentPage - 1}">Previous</a></td>
       <%--  <td><a href="all?page=${currentPage - 1}">Previous</a></td> --%>
    </c:if>
 
    <%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
    <table border="1">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="all/${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
     
    <%--For displaying Next link --%>
    <c:if test="${currentPage lt noOfPages}">
        <td><a href="all/${currentPage + 1}">Next</a></td>
    </c:if>
</body>
</html>