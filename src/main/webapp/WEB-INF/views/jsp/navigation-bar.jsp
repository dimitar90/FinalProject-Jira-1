<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
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
			<a href="./createTask">Create issue</a> <a href="./getopentasks">My
				open issues</a> <a href="./showalltasks">Show all issues</a>
		</div>
	</div>
	<div class="dropdown">
		<button class="dropbtn">
			Projects <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
			<a href="./submitProject"> Create a project</a> <a
				href="./showAllSoftware">Software</a> 
				<a href="./showAllBusiness">Business</a>
			<a href="./showAllProjects">View all projects</a>
		</div>
	</div>

	<div class="dropdown">
		<button class="dropbtn">
			Export <i class="fa fa-caret-down"></i>
		</button>
		<div class="dropdown-content">
			<a href="./exporttopdf">PDF export</a>
		</div>
	</div>

	<c:if test="${not empty sessionScope.user}">
		<div class="dropdown">
			<button class="dropbtn">
				Your profile <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="./editProfile"> Edit profile</a>
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
		<form action="search" method="post">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
			<input type="text" id="search" name="project" placeholder="Search.."
				required>
		</form>
	</div>
</div>
<body>
<script>
$(document).ready(function() {
    $(function() {
        $("#search").autocomplete({     
            source : function(request, response) {
              $.ajax({
                   url : "searchServlet",
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
</script>
</body>
</html>