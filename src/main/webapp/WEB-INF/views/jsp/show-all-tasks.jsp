<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

<jsp:include page="navigation-bar.jsp"></jsp:include>
  
<body>
	<jsp:include page="filter-form.jsp"></jsp:include>
			
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> ALL TASKS
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<div id="dataTable_wrapper"
					class="dataTables_wrapper container-fluid dt-bootstrap4">
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="dataTables_length" id="dataTable_length">
								<form action="../../tasks/goToPage" method="POST">
								Go to page: <input type="number" name="page">
											<input type="submit" value="Go" />
								</form>
							</div>
						</div>
						<div class="col-sm-12 col-md-6">
							<div id="dataTable_filter" class="dataTables_filter">
								<label>Search:<input
									class="form-control form-control-sm" placeholder=""
									aria-controls="dataTable" type="search"></label>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12">
							<table class="table table-bordered dataTable" id="dataTable"
								role="grid" aria-describedby="dataTable_info"
								style="width: 100%;" width="100%" cellspacing="0">
								<thead>
									<tr role="row">
										<th tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">#</th>
										<th tabindex="0" aria-controls="dataTable"
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
										<td><a href="../detail/${ t.id }">Show details</a></td>
										<td>
											<c:if test="${not empty user}">
												<c:if
													test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
													<a href="../edit/${ t.id }">[Edit]</a>
													<a href="../delete/${ t.id }">[Delete]</a>
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
										<td><a href="../detail/${ t.id }">Show details</a></td>
										<td>
											<c:if test="${not empty user}">
												<c:if
													test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
													<a href="../edit/${ t.id }">[Edit]</a>
													<a href="../delete/${ t.id }">[Delete]</a>
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
					<div class="row">
						<div class="col-sm-12 col-md-5">
							<div class="dataTables_info" id="dataTable_info" role="status"
								aria-live="polite">Showing ${ (currentRowsOfPage * currentPage) + 1 } to ${ (currentRowsOfPage * currentPage) + currentRowsOfPage } of ${ countOfTasks } tasks in ${ noOfPages + 1 } pages</div>
						</div>
						<div class="col-sm-12 col-md-7">
							<div class="dataTables_paginate paging_simple_numbers"
								id="dataTable_paginate">
								<ul class="pagination">
									<c:if test="${ currentPage <= 0 }">
									<li class="paginate_button page-item previous disabled"
										id="dataTable_previous"><a href="../../tasks/all/${ currentPage - 1 }"
										aria-controls="dataTable" data-dt-idx="0" tabindex="0"
										class="page-link">Previous</a></li>
									</c:if>
									<c:if test="${ currentPage > 0 }">
									<li class="paginate_button page-item previous"
										id="dataTable_previous"><a href="../../tasks/all/${ currentPage - 1 }"
										aria-controls="dataTable" data-dt-idx="0" tabindex="0"
										class="page-link">Previous</a></li>
									</c:if>
									
									<c:if test="${ currentPage <= noOfPages }">
									<li class="paginate_button page-item active"><a href="../../tasks/all/${currentPage}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 1 }</a></li>
									</c:if>
									<c:if test="${ currentPage  > noOfPages }">
									<li class="paginate_button page-item active disabled"><a href="../../tasks/all/${currentPage}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 1 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 1  <= noOfPages }">
									<li class="paginate_button page-item "><a href="../../tasks/all/${currentPage + 1}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 2 }</a></li>
									</c:if>
									<c:if test="${ currentPage + 1  > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../tasks/all/${currentPage + 1}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 2 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 2 <= noOfPages }">
									<li class="paginate_button page-item "><a href="../../tasks/all/${currentPage + 2 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 3 }</a></li>
									</c:if>
										<c:if test="${ currentPage + 2 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../tasks/all/${currentPage + 2 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 3 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 3 <= noOfPages }">
									<li class="paginate_button page-item"><a href="../../tasks/all/${currentPage + 3 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 4 }</a></li>
									</c:if>
										<c:if test="${ currentPage + 3 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../tasks/all/${currentPage + 3 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 4 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 4 <= noOfPages }">
									<li class="paginate_button page-item"><a href="../../tasks/all/${currentPage + 4 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 5 }</a></li>
									</c:if>
									<c:if test="${ currentPage +4 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../tasks/all/${currentPage + 4 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 5 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 5 <= noOfPages }">
									<li class="paginate_button page-item"><a href="../../tasks/all/${currentPage + 5 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 6 }</a></li>
									</c:if>
									<c:if test="${ currentPage + 5 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../tasks/all/${currentPage + 5 }"
										aria-controls="dataTable" data-dt-idx="3" tabindex="0"
										class="page-link">${ currentPage + 6 }</a></li>
									</c:if>
									
									<c:if test="${currentPage + 1 <= noOfPages}">
									<li class="paginate_button page-item next" id="dataTable_next"><a
										href="../../tasks/all/${ currentPage + 1 }" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a></li>
									</c:if>
									<c:if test="${currentPage + 1 > noOfPages}">
									<li class="paginate_button page-item next disabled" id="dataTable_next"><a
										href="../../tasks/all/${ currentPage + 1 }" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a></li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>