<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    	  <jsp:include page="navigation-bar.jsp"></jsp:include>
    
  <head>
	  <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
<title>Create your project here</title>
</head>
<body>
	<h3>Create project</h3><br>
	<form action="./create" method="post">
	<table>
		<tr>
		<td>
		ProjectName
			
		<input type="text" name="projectName" placeholder="project name" required>
			</td>
		</tr>
		
		<tr>
			<td><br>
	Project type:  <select name="projectType"> 
		<c:forEach items = "${ projectTypes }" var = "p">
			<option value = "${ p.id }">${ p.type.getValue() }</option>
		</c:forEach>
			   </select>
			  </td>
	    </tr><br>
	    
	    <tr>
			<td>
	Project category:  <select name="projectCategory"> 
		<c:forEach items = "${ projectCategories }" var = "c">
			<option value = "${ c.id }">${ c.category.getValue() }</option>
		</c:forEach>
			   </select>
			  </td>
	    </tr><br>
	</table><br>
	<button type="submit">Create</button> 
   </form>
</body>
</html>