	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<head>
<link rel="stylesheet" href="<c:url value="/css/style.css" />">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script src="<c:url value="/js/add-comment.js" />"></script>
<link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
<link href="<c:url value="/css/sb-admin.css" />" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
</head>

<jsp:include page="navigation-bar.jsp"></jsp:include>
<body>
		Images:
		<c:if test="${task.imageUrls.size() == 0 }">
			No images. <br>
		</c:if>
		<c:if test="${task.imageUrls.size() > 0 }">
			<br>
			<c:forEach items= "${task.imageUrls }" var="url">
				<img src="data:image/jpeg;base64,${url}"/>		
			</c:forEach>
		</c:if>
	
    <div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> TASK DETAIL
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<div id="dataTable_wrapper"
					class="dataTables_wrapper container-fluid dt-bootstrap4">
					<div class="row">
						<div class="col-sm-12">
							<table class="table table-bordered dataTable" id="dataTable"
								role="grid" aria-describedby="dataTable_info"
								style="width: 100%;" width="100%" cellspacing="0">
								<thead>
									<tr role="row">
										<th tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">Project</th>
										<th tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">Summary</th>
										<th tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">Description</th>
										<th tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">Creator</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;">Assignee</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 145px;">Start date</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;">Due date</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 140px;">Priority</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;">State</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;">Issue type</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;">Actions</th>
									</tr>
								</thead>
								<tbody>
									<tr role="row" class="even">
										<td>${task.project.name}</td>
										<td>${task.summary}</td>
										<td>${task.description}</td>
										<td>${task.creator.name}</td>
										<td>${task.assignee.name}</td>
										<td>${task.startDate}</td>
										<td>${task.dueDate}</td>
										<td>${task.priority.type.value}</td>
										<td>${task.state.type.value}</td>
										<td>${task.issue.type.value}</td>
										<td>
											<c:if test="${not empty user}">
												<c:if test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
													<a href="../edit/${ t.id }">[Edit]</a>
													<a href="../delete/${ t.id }">[Delete]</a>
												</c:if>
											</c:if>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		
  <div class="card mb-3">
		<div class="card-body">
			<div class="table-responsive">
		<div class="card-header">
				<c:if test= '${not empty sessionScope.user}'>
						<form name="commentForm">
							<input type="hidden" value="${ task.id }" name="taskId">
								<input type="text" name ="description" >
							<input type="button" value="Add comment" onclick="addComment()">
						</form>
				</c:if>
			</div>
				<div id="dataTable_wrapper"
					class="dataTables_wrapper container-fluid dt-bootstrap4">
					<div class="row">
						<div class="col-sm-12">
							<table id="comments" class="table table-bordered dataTable" id="dataTable"
								role="grid" aria-describedby="dataTable_info"
								style="width: 100%;" width="100%" cellspacing="0">
								<thead>
									<tr role="row">
										<th tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="max-width: 202px;">Author</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="max-width: 202px;">Written on</th>
										<th tabindex="0" style="max-width: 202px;"
											rowspan="1" colspan="1" style="width: 202px;">Content</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${ task.comments }" var="c" varStatus="loop">
										<c:if test="${ loop.index % 2 == 0 }">
											<tr role="row" class="even">
												<td>${c.user.name}</td>
												 <td>
												 	${c.dateTime.year}/${c.dateTime.monthValue}/${c.dateTime.dayOfMonth} ${c.dateTime.hour}:${c.dateTime.minute}:${c.dateTime.second}
												</td>
												
												<td>${c.description}</td>
											</tr>
										</c:if>
										<c:if test="${ loop.index % 2 == 1 }">
											<tr role="row" class="odd">
												<td>${c.user.name}</td>
												 <td>
												 	${c.dateTime.year}/${c.dateTime.monthValue}/${c.dateTime.dayOfMonth} ${c.dateTime.hour}:${c.dateTime.minute}:${c.dateTime.second}
												</td>
												<td>${c.description}</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>