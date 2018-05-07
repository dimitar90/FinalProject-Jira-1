<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="nav-bar-main.jsp"></jsp:include>
 <link rel="shortcut icon" href="http://designshack.net/favicon.ico">
  <link rel="icon" href="http://designshack.net/favicon.ico">
  <link rel="stylesheet" type="text/css" media="all" href="css/test1.css">
  <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script> 

<body>

  <title>IT Talents</title>

</head>

<body>
      
      <h1>Welcome, ${user.name}</h1>
	<br>
	<br>
	 <!--  <div class="col-sm-2"><a href="/users" class="pull-right"><img title="profile image" class="img-circle img-responsive" src="http://www.rlsandbox.com/img/profile.jpg"></a></div> -->
	<c:if test="${!empty sessionScope.myProjects }">
	<h2>Your own projects</h2>
	<table>
		<tr>
			<th> name of project </th>
			<th> type of project </th>
			<th> category of project </th>
		</tr>
		  
   		 <c:forEach items="${ myProjects }" var="p">
			<tr>	
				<td class="even">
					<a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a> 
				 </td>
				<td>${p.projectType}</td>
			<td> ${p.projectCategory}</td>
		</tr>
		</c:forEach>
	</table>
	</c:if>
	 <c:if test="${empty sessionScope.myProjects }">
	 <h3>No projects yet</h3>
	  </c:if>
	  
	  <div class="container">
    <h1>My profile</h1>
  	<hr>
	<div class="row">
      <div class="col-md-3">
        <div class="text-center">
			<img src="getPicSession?user=${user.email} " height="100" width="100" class="avatar img-circle" alt="avatar">
        </div>
      </div>
      <div class="col-md-9 personal-info">
        <div class="alert alert-info alert-dismissable"> 
          <i class="fa fa-coffee"></i>
        </div>
        <h3>Personal info</h3>
        
        <table>
			<tr>
				<th>User name </th>
				<th> User email </th>
	       </tr>
	       <tr>	
				<td class="even">${user.name}</td>
				<td>${user.email}</td>
		  </tr>
		</table>
      </div>
  </div>
</div>
	 </body>
	



 

</html>
 