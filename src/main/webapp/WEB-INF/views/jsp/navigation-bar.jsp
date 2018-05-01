<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<!-- <script src="//code.jquery.com/jquery-1.10.2.js"></script> -->
<!-- <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script> -->



<link rel="stylesheet" type="text/css" href="navbar.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	font-family: Arial, Helvetica, sans-serif;
}

.navbar {
	overflow: hidden;
	background-color: #808080;
}

.navbar a {
	float: left;
	display: block;
	color: black;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	font-size: 17px;
}

.navbar a:hover {
	background-color: #ddd;
	color: black;
}

.navbar a.active {
	background-color: #2196F3;
	color: white;
}

.navbar .search-container {
	float: right;
}

.navbar input[type=text] {
	padding: 6px;
	margin-top: 8px;
	font-size: 17px;
	border: none;
}

.navbar .search-container button {
	float: right;
	padding: 6px 10px;
	margin-top: 8px;
	margin-right: 16px;
	background: #ddd;
	font-size: 17px;
	border: none;
	cursor: pointer;
}

.navbar .search-container button:hover {
	background: #ccc;
}

@media screen and (max-width: 600px) {
	.navbar .search-container {
		float: none;
	}
	.navbar a, .navbar input[type=text], .navbar .search-container button {
		float: none;
		display: block;
		text-align: left;
		width: 100%;
		margin: 0;
		padding: 14px;
	}
	.navbar input[type=text] {
		border: 1px solid #ccc;
	}
}
</style>
</head>

<div class="navbar">
	<a href="./login">Jira</a>
	<div class="dropdown">
		<button class="dropbtn">
			Issues <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
			<a href="http://localhost:8080/Jira/tasks/create">Create issue</a>
			 <a href="http://localhost:8080/Jira/tasks/usertasks">My open issues</a> 
			 <a href="http://localhost:8080/Jira/tasks/all/0">Show all issues</a>
		</div>
	</div>
	<div class="dropdown">
		<button class="dropbtn">
			Projects <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
			<a href="http://localhost:8080/Jira/projects/create"> Create a project</a> <a
				href="http://localhost:8080/Jira/projects/showAllSoftware">Software</a> 
				<a href="http://localhost:8080/Jira/projects/showAllBusiness">Business</a>
			<a href="http://localhost:8080/Jira/projects/showAllProjects">View all projects</a>
		</div>
	</div>

	<div class="dropdown">
		<button class="dropbtn">
			Export <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
			<a href="http://localhost:8080/Jira/export/pdf">PDF export</a>
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