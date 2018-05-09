<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>


<head>

<jsp:include page="nav-bar-main.jsp"></jsp:include>
<link rel="stylesheet" href="<c:url value="/css/style.css" />">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>CoPilot</title>
<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css" />">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
	
<link rel="stylesheet" href="<c:url value="/css/AdminLTE.min.css" />">

<link rel="stylesheet" href="<c:url value="/css/skin-blue.min.css" />">
<link rel="stylesheet" href="<c:url value="/css/pace.min.css" />">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<link href="/static/css/app.f8ce4e197be91b0a3d5c93d690914585.css"
	rel="stylesheet">
</head>
<body>
	<div class="row center-block">
		<h2>Simple chart</h2>
		<div class="col-md-12">
			<div class="box">
				<div class="box-header">
					<h3 class="box-title">Project progress by categories</h3>
				</div>
				<div class="box-body no-padding table-responsive">
					<table class="table table-striped">
						<tbody>
							<tr>
								<th style="width: 10px;">#</th>
								<th>Projects categories</th>
								<th>Progress</th>
								<th style="width: 40px;">Label</th>
							</tr>
							<tr>
								<td>1.</td>
								<td>Atlassian Add-Ons</td>
								<td><div class="progress progress-xs">
										<div class="progress-bar progress-bar-danger"
											style="width: 90%"></div>
									</div></td>
								<td><span class="badge bg-red">${categories.get("Atlassian Add-ons")}%</span></td>
							</tr>
							<tr>
								<td>2.</td>
								<td>No Category</td>
								<td><div class="progress progress-xs">
										<div class="progress-bar progress-bar-yellow"
											style="width: 30%"></div>
									</div></td>
								<td><span class="badge bg-yellow">${categories.get("No Category")}%</span></td>
							</tr>
							<tr>
								<td>3.</td>
								<td>Atlassian Products</td>
								<td><div
										class="progress progress-xs progress-striped active">
										<div class="progress-bar progress-bar-primary"
											style="width: 30%"></div>
									</div></td>
								<td><span class="badge bg-light-blue">${categories.get("Atlassian Products")}%</span></td>
							</tr>
							<tr>
								<td>4.</td>
								<td>Common moduls</td>
								<td><div
										class="progress progress-xs progress-striped active">
										<div class="progress-bar progress-bar-success"
											style="width: 20%"></div>
									</div></td>
								<td><span class="badge bg-green">${categories.get("Common Moduls")}%</span></td>
							</tr>
							<tr>
								<td>5.</td>
								<td>Confluence Plugins</td>
								<td><div
								<c:set target="${categories}" property="1"  value="Confluence Plugins" />
								<c:set var="categories"></c:set>
								
										class="progress progress-xs progress-striped active">
										<div class="progress-bar progress-bar-success"
											style="width: ${categories.get("Confluence Plugins")}%"></div>
									</div></td>
								<td><span class="badge bg-green">${Confluence Plugins}%</span></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
<body>
</html>