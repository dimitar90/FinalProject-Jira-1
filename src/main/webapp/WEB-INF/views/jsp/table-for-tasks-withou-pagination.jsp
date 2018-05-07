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
		<div class="card-header">
			<i class="fa fa-table"></i><b>${ tableName }</b>
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
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">#</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">Project</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;">Summary</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 145px;">Assignee</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;">Due date</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 140px;">Priority</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;">State</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;">Details</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;">Actions</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th rowspan="1" colspan="1">#</th>
										<th rowspan="1" colspan="1">Project</th>
										<th rowspan="1" colspan="1">Summary</th>
										<th rowspan="1" colspan="1">Assignee</th>
										<th rowspan="1" colspan="1">Due date</th>
										<th rowspan="1" colspan="1">Priority</th>
										<th rowspan="1" colspan="1">State</th>
										<th rowspan="1" colspan="1">Details</th>
										<th rowspan="1" colspan="1">Actions</th>
									</tr>
								</tfoot>
								<tbody>
									<c:choose>
										<c:when test="${tasks.size() > 0}">
												<c:forEach items="${ tasks }" var="t" varStatus="loop">
													<c:if test="${loop.index % 2 == 0}">
													<tr role="row" class="even">
														<td>${(currentRowsOfPage * currentPage) + (loop.index + 1)}</td>
														<td>${t.project.name}</td>
														<td>${t.summary}</td>
														<td>${t.assignee.name}</td>
														<td>${t.dueDate}</td>
														<td>${t.priority.type.value}</td>
														<td>${t.state.type.value}</td>
														<td><a href="./detail/${ t.id }">Show details</a></td>
														<td>
															<c:if test="${not empty user}">
																<c:if
																	test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
																	<a href="./edit/${ t.id }">[Edit]</a>
																	<a href="./delete/${ t.id }">[Delete]</a>
																</c:if>
															</c:if>
														</td>
													</tr>
													</c:if>
													<c:if test="${loop.index % 2 == 1}">
													<tr role="row" class="odd">
														<td>${(currentRowsOfPage * currentPage) + (loop.index + 1)}</td>
														<td>${t.project.name}</td>
														<td>${t.summary}</td>
														<td>${t.assignee.name}</td>
														<td>${t.dueDate}</td>
														<td>${t.priority.type.value}</td>
														<td>${t.state.type.value}</td>
														<td><a href="./detail/${ t.id }">Show details</a></td>
														<td>
															<c:if test="${not empty user}">
																<c:if
																	test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
																	<a href="./edit/${ t.id }">[Edit]</a>
																	<a href="./delete/${ t.id }">[Delete]</a>
																</c:if>
															</c:if>
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