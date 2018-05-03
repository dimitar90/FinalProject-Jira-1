<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en" >

<head>

 <title>IT Talents</title>
  
  
  
<link rel="stylesheet" href="css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
  
</head>

<body>

  
<div id="main">
  <div class="container">
    <nav>
      <div class="nav-fostrap">
        <ul>
          <li><a href="">Jira</a></li>
          <li><a href="javascript:void(0)">Issues<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="http://localhost:8080/Jira/tasks/create">Create issue</a></li>
              <li><a href="http://localhost:8080/Jira/tasks/all/0">Show all issues</a></li>
              <li><a href="http://localhost:8080/Jira/tasks/usertasks">My open issues</a> </li>
            </ul>
          </li>
          <li><a href="javascript:void(0)" >Projects<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="">Widget</a></li>
              <li><a href="">Tips</a></li>
            </ul>
          </li>
          <li><a href="http://localhost:8080/Jira/export/pdf">PDF export</a></li>
       <!--    <li><a href="">Advertising</a></li>
          <li><a href="">Business</a></li> -->
           <li><a href="javascript:void(0)" >Menu<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="">Login</a></li>
              <!-- <li><a href="">Tips</a></li> -->
            </ul>
          </li>
		</div>
        </ul>
       
      </div>
      <div class="nav-bg-fostrap">
        <div class="navbar-fostrap"> <span></span> <span></span> <span></span> </div>
        <a href="" class="title-mobile">Fostrap</a>
      </div>
    </nav>
    <div class='content'>
    </div>
</div>
</div>

  
</head>

<%-- <div class="navbar">
	<a href="./login">Jira</a>
	<div class="dropdown">
		<button class="dropbtn">
			Issues <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
			
			 
			 
		</div>
	</div>
	<div class="dropdown">
		<button class="dropbtn">
			Projects <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
			 <a
				href="http://localhost:8080/Jira/projects/showAllSoftware">Software</a> 
				<a href="http://localhost:8080/Jira/projects/showAllBusiness">Business</a>
			<a href="http://localhost:8080/Jira/projects/all/0">View all projects</a>
		</div>
	</div>

	<div class="dropdown">
		<button class="dropbtn">
			Export <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
		</div>
	</div>

	<c:if test="${not empty sessionScope.user}">
		<div class="dropdown">
			<button class="dropbtn">
				Your profile <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="http://localhost:8080/Jira/editProfile"> Edit profile</a>
			</div>
		</div>
	</c:if>

	<c:if test="${empty sessionScope.user}">
		<div class="dropdown">
			<button class="dropbtn">
				Log in <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="./"> Login</a>
			</div>
		</div>
	</c:if>
 --%>
	<div class="search-container">

		<form action="searchBtn" method="post">
			<input id="projectInput" type="text" list="projects" name="project"
				placeholder="Search.." required>
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
		</form>
	<div id="searchResult"></div>
	</div>
</div>

<body>
	<!-- <script>
$(document).ready(function() {
    $(function() {
        $("#search").autocomplete({     
            source : function(request, response) {
              $.ajax({
                   url : "searchAutoComplete",
                   type : "GET",
                   data : {
                          term : request.term
                   },
                   dataType : "json",
                   success : function(data) {
                         response(data);
                   }
            });
         }
     });
  });
});
</script> -->
	<script>
		document.getElementById("projectInput").addEventListener("keyup",function() {
						var prefix = this.value;
						console.log(prefix);
						var xhr = new XMLHttpRequest();
							xhr.open("GET", "./searchProjects?prefix=" + prefix, true);
							xhr.send(null);
							xhr.addEventListener(
											'load', function() {
												var projects = JSON
														.parse(xhr.responseText);
												var html = '';
												for (var index = 0; index < projects.length; index++) {
													html += "<p> " + projects[index] + " </p>";
												}
												document.getElementById('searchResult').innerHTML = html;
											});
						});
	</script>
</body>
</html>