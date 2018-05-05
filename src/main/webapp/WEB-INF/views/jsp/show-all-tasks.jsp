<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
<link href="<c:url value="/css/sb-admin.css" />" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
<link rel="stylesheet" href="<c:url value="/css/style.css" />">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<c:set value="7" var="countOfTableCols"/>
</head>

<jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
	<jsp:include page="filter-form.jsp"></jsp:include>
			
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> <b>ALL TASKS</b>
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<div id="dataTable_wrapper"
					class="dataTables_wrapper container-fluid dt-bootstrap4">
					
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="dataTables_length" id="dataTable_length">
								<form action="../../tasks/goToPage" method="POST">
									<b>Go to page: </b>
									<input type="number" name="page" required>
									<input type="submit" value="Go" />
								</form>
							</div>
						</div>
						<div class="col-sm-12 col-md-6">
							<div id="dataTable_filter" class="dataTables_filter">
								<form action="../../tasks/searchByName" method="POST">
									<label><b>Search by part of summary:</b><input class="form-control form-control-sm" placeholder="" aria-controls="dataTable" type="search" name="search"></label>
								</form>
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
									<c:choose>
										<c:when test="${ tasks.size() > 0 }">
											<c:forEach items="${ tasks }" var="t" varStatus="loop">	
												<c:if test="${loop.index % 2 == 0}">
													<tr role="row" class="even">
												</c:if>
												<c:if test="${loop.index % 2 != 0}">
													<tr role="row" class="odd">
												</c:if>
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
											</c:forEach>
										</c:when>									
										<c:otherwise>
											<td colspan="${ countOfTableCols }"><h3><b>No results found!</b></h3></td>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-5">
							<div class="dataTables_info" id="dataTable_info" role="status"
								aria-live="polite">Showing <b>${ (currentRowsOfPage * currentPage) + 1 }</b> to <b>${ (currentRowsOfPage * currentPage) + currentRowsOfPage }</b> of <b>${ countOfTasks }</b> tasks in <b>${ noOfPages + 1 }</b> pages</div>
						</div>
						<div class="col-sm-12 col-md-7">
							<div class="dataTables_paginate paging_simple_numbers"
								id="dataTable_paginate">
								<ul class="pagination">
									<%-- First button --%>
									<c:if test="${ currentPage == 0 }">
										<li class="paginate_button page-item previous disabled" id="dataTable_previous">
											<a href="../../tasks/all/0" aria-controls="dataTable" class="page-link">First</a>
										</li>
									</c:if>
									<c:if test="${ currentPage > 0 }">
										<li class="paginate_button page-item previous" id="dataTable_previous">
											<a href="../../tasks/all/0" aria-controls="dataTable" class="page-link">First</a>
										</li>
									</c:if>
								
									<%-- Previous button --%>
									<c:if test="${ currentPage <= 0 }">
										<li class="paginate_button page-item previous disabled" id="dataTable_previous">
											<a href="../../tasks/all/${ currentPage - 1 }" aria-controls="dataTable" class="page-link">Previous</a>
										</li>
									</c:if>
									<c:if test="${ currentPage > 0 }">
										<li class="paginate_button page-item previous" id="dataTable_previous">
											<a href="../../tasks/all/${ currentPage - 1 }" aria-controls="dataTable" class="page-link">Previous</a>
										</li>
									</c:if>
									
									<%-- loop for button for other range pages 
									<c:forEach begin="${ firstIndexPaginationLoop }" end="${ lastIndexPaginationLoop }" varStatus="loop">
										 <c:if test="${loop.index <= 2 }">
										 	 <c:if test="${ (currentPage + loop.index ) <= noOfPages }">
												<li class="paginate_button page-item active"><a href="../../tasks/all/${currentPage}"
													aria-controls="dataTable" data-dt-idx="1" tabindex="0"
													class="page-link">${ currentPage + loop.index }</a></li>
											</c:if>
											<c:if test="${ (currentPage + loop.index )  > noOfPages }">
												<li class="paginate_button page-item active disabled"><a href="../../tasks/all/${currentPage}"
													aria-controls="dataTable" data-dt-idx="1" tabindex="0"
													class="page-link">${ currentPage + loop.index }</a></li>
											</c:if>
										 </c:if>
										 
										 <c:if test="${loop.index > 2  }">
										 	<c:if test="${ (currentPage + loop.index ) <= noOfPages }">
												<li class="paginate_button page-item active"><a href="../../tasks/all/${currentPage}"
													aria-controls="dataTable" data-dt-idx="1" tabindex="0"
													class="page-link">${ (currentPage + 1) + loop.index }</a></li>
											   </c:if>
											<c:if test="${ (currentPage + loop.index )  > noOfPages }">
												<li class="paginate_button page-item active disabled"><a href="../../tasks/all/${currentPage}"
													aria-controls="dataTable" data-dt-idx="1" tabindex="0"
													class="page-link">${ (currentPage + 1) + loop.index }</a></li>
											   </c:if>
										 </c:if>
	   									
									</c:forEach> --%>
									
									<li class="paginate_button page-item next disabled" id="dataTable_next">
										<a aria-controls="dataTable"  tabindex="0" class="page-link">${ currentPage + 1 }</a>
									</li>
								
									<%-- Next button --%>
									<c:if test="${currentPage + 1 <= noOfPages}">
										<li class="paginate_button page-item next" id="dataTable_next">
											<a href="../../tasks/all/${ currentPage + 1 }" aria-controls="dataTable" class="page-link">Next</a>
										</li>
									</c:if>
									<c:if test="${currentPage + 1 > noOfPages}">
										<li class="paginate_button page-item next disabled" id="dataTable_next">
											<a href="../../tasks/all/${ currentPage + 1 }" aria-controls="dataTable" class="page-link">Next</a>
										</li>
									</c:if>
									
									<%-- Last button --%>
									<c:if test="${currentPage >= noOfPages}">
										<li class="paginate_button page-item next disabled" id="dataTable_next">
											<a href="../../tasks/all/${ noOfPages }" aria-controls="dataTable" class="page-link">Last</a>
										</li>
									</c:if>
									<c:if test="${currentPage < noOfPages}">
										<li class="paginate_button page-item next" id="dataTable_next">
											<a href="../../tasks/all/${ noOfPages }" aria-controls="dataTable" class="page-link">Last</a>
										</li>
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