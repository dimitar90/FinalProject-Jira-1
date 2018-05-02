<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="../vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
<link href="../css/sb-admin.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../css/table.css">
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
										<th class="sorting_asc" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;"
											aria-sort="ascending"
											aria-label="Name: activate to sort column descending">#</th>
										<th class="sorting_asc" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;"
											aria-sort="ascending"
											aria-label="Name: activate to sort column descending">Project</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;"
											aria-label="Position: activate to sort column ascending">Summary</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 145px;"
											aria-label="Office: activate to sort column ascending">Assignee</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;"
											aria-label="Age: activate to sort column ascending">Due
											date</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 140px;"
											aria-label="Start date: activate to sort column ascending">Priority</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;"
											aria-label="Salary: activate to sort column ascending">State</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;"
											aria-label="Salary: activate to sort column ascending">Details</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;"
											aria-label="Salary: activate to sort column ascending">Actions</th>
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
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>