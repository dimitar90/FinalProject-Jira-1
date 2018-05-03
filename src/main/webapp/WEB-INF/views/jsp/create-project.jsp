<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/table.css" />">

<jsp:include page="nav-bar-main.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IT Talents</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
</head>
<body>
	<form class="form-horizontal" action="create" method="post">
		<fieldset>

			<!-- Form Name -->
			<legend>Create project here</legend>

			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="pname">Project
					name</label>
				<div class="col-md-4">
					<input id="pname" name="projectName" type="text"
						placeholder="project name" class="form-control input-md"
						required="">

				</div>
			</div>
			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="PTYPE">Project
					type</label>
				<div class="col-md-4">
					<select id="PTYPE" name="projectType" class="form-control">
						<c:forEach items="${ projectTypes }" var="p">
							<option value="${ p.id }">${ p.type.getValue() }</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="PTYPE">Project
					type</label>
				<div class="col-md-4">
					<select id="PTYPE" name="projectCategory" class="form-control">
						<c:forEach items="${ projectCategories }" var="p">
							<option value="${ p.id }">${ p.category.getValue() }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<!-- Button (Double) -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="button1idFFF"></label>
				<div class="col-md-8">
					<button id="button1idFFF" type="submit" name="button1idFFF"
						class="btn btn-success">Create</button>
					<button id="button2id" name="button2id" class="btn btn-danger">Cancel</button>
				</div>
			</div>

		</fieldset>
	</form>

</body>
</html>
<%--  <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
</html>  --%>