<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<jsp:include page="nav-bar-main.jsp"></jsp:include>
<link rel="stylesheet" href="<c:url value= "/css/style.css"/>">


<title>IT Talents</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
</head>


<body>

	<form class="form-horizontal" action="create" method="post">
		<fieldset>
 						<c:if test="${ requestScope.errorMsg != null}">
         									 <div class="form-group">
          									  <div class="form-check">
         								     <div class="alert alert-danger" role="alert">
											${errorMsg} <strong>Try again!</strong>
												</div>
											</c:if>
			<!-- Form Name -->
			<legend>Create project here</legend>

			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="pname">Project
					name</label>
				<div class="col-md-4">
					<input id="pname" name="projectName" type="text"
						placeholder="project name" class="form-control input-md"
						required="required" autocomplete="off">

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
					category</label>
				<div class="col-md-4">
					<select id="PTYPE" name="projectCategory" class="form-control">
						<c:forEach items="${ projectCategories }" var="p">
							<option value="${ p.id }">${ p.category.getValue() }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
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
