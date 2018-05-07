<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
	<link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
	<link href="<c:url value="/css/sb-admin.css" />" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
	<link rel="stylesheet" href="<c:url value="/css/style.css" />">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>

<div class="card mb-3">
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
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">#</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">Project name</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;">Project type</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 145px;">Project category</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;">Project lead</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th rowspan="1" colspan="1">#</th>
										<th rowspan="1" colspan="1">Project name</th>
										<th rowspan="1" colspan="1">Project type</th>
										<th rowspan="1" colspan="1">Project category</th>
										<th rowspan="1" colspan="1">Project lead</th>
									</tr>
								</tfoot>
								<tbody>
									<c:choose>
										<c:when test="${projects.size() > 0}">
												<c:forEach items="${ projects }" var="p" varStatus="loop">
													<c:if test="${loop.index % 2 == 0}">
													<tr role="row" class="even">
														<td>${(currentRowsOfPage * currentPage) + (loop.index + 1)}</td>
														<td>
														<a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a>
														</td>
														<td>${p.projectType}</td>
														<td>${p.projectCategory}</td>
														<td>
														<a href="http://localhost:8080/Jira/userId/${p.id}">${p.projectLead}</a>
														</td>
													</tr>
													</c:if>
													<c:if test="${loop.index % 2 == 1}">
													<tr role="row" class="odd">
														<td>${(currentRowsOfPage * currentPage) + (loop.index + 1)}</td>
														<td>
														<a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a>
														</td>
														<td>${p.projectType}</td>
														<td>${p.projectCategory}</td>
														<td>
														<a href="http://localhost:8080/Jira/userId/${p.id}">${p.projectLead}</a>
														</td>
													</tr>
													</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
										 	<td colspan="10"><h3><b>No results found!</b></h3></td>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>