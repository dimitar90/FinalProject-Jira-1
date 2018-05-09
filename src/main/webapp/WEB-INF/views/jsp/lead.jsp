<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IT Talents</title>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html">
<meta name="author" content="Jake Rocheleau">
	<link href="<c:url value="../css/myProfile.css" />" rel="stylesheet">
<script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<link rel="stylesheet" href="<c:url value="/css/style.css" />">

</head>

	<c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
	</c:if>
<body style="">
	<div id="w">
		<div id="content" class="clearfix">
			<div id="userphoto">
				<img src="http://localhost:8080/Jira/getPicLead?dto=${sessionScope.dto.email}" height="100" width="100" class="avatar img-circle" alt="avatar">
			</div>
			<h1>Jira profile</h1>
			<nav id="profiletabs">
			<ul class="clearfix">
			<!-- 	<li><a href="#bio" class="">Bio</a></li>
				<li><a href="#activity">Activity</a></li>
				<li><a href="#friends">Friends</a></li> -->
				<li><a href="#settings" class="sel">Personal info</a></li>
			</ul>
			</nav> 
			<section id="settings" class="">
			<p class="setting">
			<span>Full name:</span>
				${sessionScope.dto.name}
				
			</p>
			<p class="setting">
			<span>Email:</span>
				${sessionScope.dto.email}
			</p>
			<c:if test="${not empty sessionScope.user}">
			
			<c:if test="${ sessionScope.dto.dtoProjectsCount > 0 }">
			<p class="setting">
				<span>Creator on: </span>
				<a href="http://localhost:8080/Jira/projects/leadProjects/0">${dto.dtoProjectsCount} projects</a>
			</p>
			</c:if>
			<c:if test="${ sessionScope.dto.dtoProjectsCount <= 0 }">
			<p class="setting">
				<span>Creator on: </span>
				NO PROJECTS
			</p>
			</c:if>
			</c:if>
			</section>
		</div>
	</div>
	<script data-cfasync="false"
		src="/cdn-cgi/scripts/d07b1474/cloudflare-static/email-decode.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#profiletabs ul li a').on('click', function(e) {
				e.preventDefault();
				var newcontent = $(this).attr('href');

				$('#profiletabs ul li a').removeClass('sel');
				$(this).addClass('sel');

				$('#content section').each(function() {
					if (!$(this).hasClass('hidden')) {
						$(this).addClass('hidden');
					}
				});

				$(newcontent).removeClass('hidden');
			});
		});
	</script>

</body>
</body>