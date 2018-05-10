<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>

 <title>IT Talents</title>
  
<link href="<c:url value="/css/style.css"/>" rel="stylesheet">  

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
  
</head>
  
<div id="main">
  <div class="container">
    <nav>
      <div class="nav-fostrap">
        <ul>
          <li><a href="#">Jira</a></li>
          <li><a href="javascript:void(0)">Issues<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="http://localhost:8080/Jira/tasks/all/0">Show all issues</a></li>
            </ul>
          </li>
          <li><a href="javascript:void(0)" >Projects<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="http://localhost:8080/Jira/projects/showAllSoftware">Software</a></li>
              <li><a href="http://localhost:8080/Jira/projects/showAllBusiness">Business</a></li>
              <li><a href="http://localhost:8080/Jira/projects/all/0">View all projects</a></li>
            </ul>
          </li>
           <li><a href="javascript:void(0)" >Menu<span class="arrow-down"></span></a>
            <ul class="dropdown">
              <li><a href="http://localhost:8080/Jira/register">Register</a></li>
              <li><a href="http://localhost:8080/Jira/">Login</a></li>
            </ul>
          </li>
		</div>
        </ul>
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
</div>

 
</head>
<body>


</html>