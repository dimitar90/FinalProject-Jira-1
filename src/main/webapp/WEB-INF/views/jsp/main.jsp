
<%-- <%@page import="com.jira.model.pojo.User"%> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="navigation-bar.jsp"></jsp:include>
<body>
	
	<h1>Welcome, ${user.name}</h1>
	<br>
	<br>
	<h2>Your own projects</h2>
	<table>
		<tr>
			<th> name of project </th>
			<th> type of project </th>
			<th class="project"> category of project </th>
		</tr>
		<c:forEach items="${ myProjects }" var="p">
		<tr>	
			<td class="even"> 
			<a href="./projectId/${p.id}">${p.name}</a> 
			 </td>
			<td>${p.projectType}</td>
			<td class="project"> ${p.projectCategory}</td>
			<td class="project">
			</td>
		</tr>
		</c:forEach>
	</table>
	
	<form action="logout">
	    <table>
		       <tr>
		        <td>			
			      <input type="submit" value="Logout">
		         </td>
	          </tr>
       </table>
   </form>
		<br>
		<form action="editProfile" method="get">
	    <table>
		       <tr>
		        <td>			
			      <input type="submit" value="Edit profil">
		         </td>
	          </tr>
       </table>
   </form>
   <br>
   <form action="myProfile" method="get">
	    <table>
		       <tr>
		        <td>			
			      <input type="submit" value="My profil">
		         </td>
	          </tr>
       </table>
   </form>
</body>
</html>