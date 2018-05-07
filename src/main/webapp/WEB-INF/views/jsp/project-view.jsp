<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
	<jsp:include page="nav-bar-main.jsp"></jsp:include>

    <title>IT Talents</title>
	 <link rel="stylesheet" href="<c:url value="/css/style.css" />">
    <link rel="stylesheet" href="<c:url value="/bootstrap/dist/css/bootstrap.min.css" />">
       <link rel="stylesheet" href="<c:url value="/css/animate.css" />">
       <link rel="stylesheet" href="<c:url value="css/default.css" />"> 
 
</head>

<body>
<%-- <c:set value="${sessionScope.user} var="user"/> --%>
	
<c:if test="${!empty dtoProject }">
<div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">${dtoProject.projectLead} </h3>
                            <p class="text-muted">Project view<code>.Jira</code></p>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                           <th>Project name</th>
											<th>Project type</th>
											<th>Project category</th>
											<th>Project lead</th>
											<th>Remove project</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                         <c:set var="dtoProject" value="${ dtoProject }" />
											 <tr>	
												<td>${dtoProject.name}</td>
											 	<td>${dtoProject.projectType}</td>
			   								 	<td>${dtoProject.projectCategory}</td>
											 	<td>
											 		<a href="http://localhost:8080/Jira/userId/${dtoProject.id}">${dtoProject.projectLead}</a>
											 	</td>
											 	<td>
											 		<c:if test="${user.id == dtoProject.projectLeadId}">
													<a href="http://localhost:8080/Jira/projects/delete/${ dtoProject.id }">Delete</a>			
													</c:if>
												</td>
										</tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
</c:if>
                     
                    <c:if test="${empty dtoProject }">
                      <div>
                       <span>
                       <h1>No project to view</h1>
                       </span>
                      </div>
                    </c:if>
                    <div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title"> </h3>
                            <p class="text-muted">Tasks of the project</p>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
											<th>Task Summary</th>
											<th>Due date</th>
											<th>Project name</th>
											<th >Priority</th>
											<th >State</th>
											<th >Creator</th>
											<th >Assigne</th>
											<th>Details</th>
											<th>Action</th>
										</tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${ tasksDto }" var="t">
										  <tr>	
											<td >${t.summary}</td>
											<td>${t.dueDate}</td>
											<td>${t.project.name}</td>
											<td>${t.priority.type.value}</td>
											<td>${t.state.type.value}</td>
											<td>${t.creator.name}</td>
											<td>${t.assignee.name}</td>
											<td><a href="../detail/${ t.id }">Show details</a></td>
											<td>
												<c:if test="${not empty user}">
													<c:if
														test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
															<a href="../edit/${ t.id }">[Edit]</a>
																<a href="../delete/${ t.id }">[Delete]</a>
																</c:if>
															</c:if>
														</td>
										 </tr>
									   </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <c:if test="${empty tasksDto && !empty dtoProject}">
                      <div>
                       <span>
                       <h1>No task on the projects</h1>
                       </span>
                      </div>
                    </c:if>
</body>
</html>

<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="nav-bar-main.jsp"></jsp:include>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project info</title>
</head>
<body>
	<h1>Project view</h1>
	<c:set var="dtoProject" value="${ dtoProject }" />
	<c:if test="${!empty dtoProject }">
	<table>
		<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th >Project category</th>
			<th >Project lead</th>
				
		</tr>
		<tr>	
			<td class="even">${dtoProject.name}</td>
			<td>${dtoProject.projectType}</td>
			<td class="project">${dtoProject.projectCategory}</td>
			<td class="project">${dtoProject.projectLead}</td>
			<th>
					<a href="http://localhost:8080/Jira/projects/delete/${ dtoProject.id }">Delete</a>
				</th>
		</tr>
	</table>
	</c:if>
	<c:if test="${!empty tasksDto }">
	
	<h3>Tasks of the current project</h3>
	<br>
	<table>
		<tr>
			<th>Task Summary</th>
			<th>Due date</th>
			<th>Project name</th>
			<th >Priority</th>
			<th >State</th>
			<th >Creator</th>
			<th >Assigne</th>
		</tr>
		<c:forEach items="${ tasksDto }" var="t">
		<tr>	
			<td >${t.summary}</td>
			<td>${t.dueDate}</td>
			<td >${t.project.name}</td>
			<td >${t.priority.type.value}</td>
			<td>${t.state.type.value}</td>
			<td>${t.creator.name}</td>
			<td>${t.assignee.name}</td>
		</tr>
		
		</c:forEach>
	</table>
	</c:if>
		<c:if test="${empty tasksDto }">
	<h3>No tasks of this project</h3>
	</c:if>
</body>
</html> --%>