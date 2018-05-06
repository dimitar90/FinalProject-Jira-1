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

<div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">Business</h3>
                            <p class="text-muted">Projects <code>.Jira</code></p>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                           <th>Project name</th>
											<th>Project type</th>
											<th class="project">Project category</th>
											<th class="project">Project lead</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                     <c:forEach items="${ softwareProjects }" var="softProject">
									   <tr>	
										 <td>
										 <a href="http://localhost:8080/Jira/projects/projectId/${softProject.id}">${softProject.name}</a>
										 </td>
					   		      		 <td>${softProject.projectType}</td>
										 <td>${softProject.projectCategory}</td>
										 <td>
										 	<a href="http://localhost:8080/Jira/userId/${softProject.id}">${softProject.projectLead}</a>
										 </td>
									  </tr>
									</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
</body>
</html>