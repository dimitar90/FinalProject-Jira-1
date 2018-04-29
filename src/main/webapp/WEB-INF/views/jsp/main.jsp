
<%-- <%@page import="com.jira.model.pojo.User"%> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="navigation-bar.jsp"></jsp:include>
<body>
	
	<h1>Welcome, ${user.name}</h1>
	<table>
	<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th class="project">Project category</th>
			<th class="project">Project lead</th>
		</tr>
		<c:forEach items="${ dtoProjects }" var="p">
		<tr>	
			<td class="even"> <a href="./projectId/${p.id}"> ${p.name} </td>
			<td>${p.projectType}</td>
			<td class="project"> ${p.projectCategory}</td>
			<td class="project">${p.projectLead}</td>
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
</body>
</html>