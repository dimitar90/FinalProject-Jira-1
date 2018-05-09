<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<title>Task detail</title>
<link rel="stylesheet" href="<c:url value="/css/style.css" />">
<script src="<c:url value="/js/add-comment.js" />"></script>
<link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />"rel="stylesheet">
<link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
<link href="<c:url value="/css/sb-admin.css" />" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
</head>

<c:if test='${not empty sessionScope.user}'>
	<jsp:include page="nav-bar-main.jsp"></jsp:include>
</c:if>
<c:if test='${empty sessionScope.user}'>
	<jsp:include page="navigation-bar.jsp"></jsp:include>
</c:if>

<body>
	<h4>
		<b>Images:</b>
	</h4>
	<c:if test="${task.imageUrls.size() == 0 }">
		<h5>
			<b>No images. </b>
		</h5>
		<br>
	</c:if>

	<c:if test="${task.imageUrls.size() > 0 }">
		<div class="et_pb_gallery_items et_post_gallery" data-per_page="3">
			<c:forEach items="${task.imageUrls }" var="url">
				<span
					class="et_pb_gallery_item et_pb_grid_item et_pb_bg_layout_light">
					<span class="et_pb_gallery_image landscape"> <a href="data:image/jpeg;base64,${url}" title="27747975_2074584502761278_1171650822192642176_o"> <img
							width=300px src="data:image/jpeg;base64,${url}" alt="27747975_2074584502761278_1171650822192642176_o" class="lazyloaded">
					</a>
				</span>
				</span>
			</c:forEach>
		</div>
	</c:if>

	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> <b>TASK DETAIL</b>
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
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 202px;">Project</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 202px;">Summary</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 202px;">Description</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 202px;">Creator</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 304px;">Assignee</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 145px;">Start date</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 71px;">Due date</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 140px;">Priority</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 114px;">State</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 114px;">Issue type</th>
										<th tabindex="0" aria-controls="dataTable" rowspan="1"
											colspan="1" style="width: 114px;">Actions</th>
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
												<c:if test="${(user.id == task.creator.id || user.id == task.assignee.id)}">
													<a href="../edit/${ task.id }">[Edit]</a>
													<a href="../delete/${ task.id }">[Delete]</a>
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

	<div class="container">
		<c:if test='${not empty sessionScope.user}'>
			<form name="commentForm">
				<input type="hidden" value="${ task.id }" name="taskId">
				<textarea rows="5" name="description"></textarea>
				<input type="button" value="Add comment" onclick="addComment()">
			</form>
		</c:if>
	</div>
	<div class="container" id="comments">
		<c:forEach items="${ task.comments }" var="c" varStatus="loop">
			<div class="row">
				<div class="col-md-2">
					<div>
						<div>
							<div>
								<span><img src="<c:url value="/userAvatar/${ c.userAvatarName }" />" height="100" width="100"></span>
							</div>
						</div>
						<div>
							<p>${c.username}</p>
						</div>
					</div>
				</div>
				<div class="col-md-10">
					<div>
						<div>
							<p>
							<b>Written on: </b>${c.dateTime.year}/${c.dateTime.monthValue}/${c.dateTime.dayOfMonth} ${c.dateTime.hour}:${c.dateTime.minute}:${c.dateTime.second}
								</p>${c.description}
							</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>