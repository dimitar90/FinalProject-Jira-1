<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en" >

<head>

 <title>IT Talents</title>
  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<link href="<c:url value="/css/style.css" />" rel="stylesheet">  
  
</head>
  
<div id="main">
  <div class="container">
    <nav>
      <div class="nav-fostrap">
        <ul>
          <li><a href="#">Jira</a></li>
          <li><a href="javascript:void(0)">Issues<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="http://localhost:8080/Jira/tasks/create">Create issue</a></li>
              <li><a href="http://localhost:8080/Jira/tasks/all/0">Show all issues</a></li>
              <li><a href="http://localhost:8080/Jira/tasks/usertasks">My open issues</a> </li>
            </ul>
          </li>
          <li><a href="javascript:void(0)" >Projects<span class="arrow-down"></span></a>
            <ul  id="showfront" class="dropdown"  >
              <li><a href="http://localhost:8080/Jira/projects/create">Create project</a></li>
              <li><a href="http://localhost:8080/Jira/projects/showAllSoftware">Software</a></li>
              <li><a href="http://localhost:8080/Jira/projects/showAllBusiness">Business</a></li>
              <li><a href="http://localhost:8080/Jira/projects/all/0">View all projects</a></li>
            </ul>
          </li>
          <li><a href="javascript:void(0)" >Export<span class="arrow-down"></span></a>
            <ul class="dropdown">
            	<li><a href="http://localhost:8080/Jira/export/pdf">Into PDF</a></li>
            </ul>
          </li>
          <li><a href="javascript:void(0)" >Charts<span class="arrow-down"></span></a>
            <ul class="dropdown">
            	<li><a href="http://localhost:8080/Jira/charts/taskInfo">Tasks info</a></li>
            </ul>
          </li>
           <li>
           <li>
           <a href="javascript:void(0)" >Menu<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="http://localhost:8080/Jira/logout">Logout</a></li>
              <li><a href="http://localhost:8080/Jira/myProfile">My profile</a></li>
              <li><a href="http://localhost:8080/Jira/editProfile">Edit profile</a></li>
            </ul>
          </li>
          </ul>
		</div>
       </nav>
      </div>
      <div class="nav-bg-fostrap">
        <div class="navbar-fostrap"> <span></span> <span></span> <span></span> </div>
        <a href="" class="title-mobile">Fostrap</a>
      </div>
    </nav>
    <div class='content'>
    </div>
</div>

 
</head>
<body>


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