
<%-- <%@page import="com.jira.model.pojo.User"%> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="navigation-bar.jsp"></jsp:include>
<body>
	
	<h1>Welcome, ${user.name}</h1>
	
	<form action="./logout">
	    <table>
		       <tr>
		        <td>			
			      <input type="submit" value="Logout">
		         </td>
	          </tr>
       </table>
   </form>
		<br>
		<form action="./editProfile">
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