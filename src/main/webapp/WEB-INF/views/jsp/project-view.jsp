<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>

    <title>IT Talents</title>
	 <link rel="stylesheet" href="<c:url value="/css/style.css" />">
    <link rel="stylesheet" href="<c:url value="/bootstrap/dist/css/bootstrap.min.css" />">
       <link rel="stylesheet" href="<c:url value="/css/animate.css" />">
       <link rel="stylesheet" href="<c:url value="css/default.css" />"> 
 
</head>

<body>
<c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
	</c:if>
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

<jsp:include page="table-for-tasks-withou-pagination.jsp"></jsp:include>
</body>
</html>