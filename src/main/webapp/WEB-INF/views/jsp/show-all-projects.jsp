<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>
<link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
<link href="<c:url value="/css/sb-admin.css" />" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/navbar.css" />">
<link rel="stylesheet" href="<c:url value="/css/style.css" />">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

<title>IT Talents</title>
</head>

<body>
<c:set value="7" var="countOfTableCols"/>			


	<c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
	</c:if>
	
	<jsp:include page="category-filter-form.jsp"></jsp:include>
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> <b>ALL PROJECTS</b>
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
											rowspan="1" colspan="1" style="width: 202px;">#</th>
										<th tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;">Project Name</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;">Project Type</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 145px;">Project Category</th>
										<th  tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;">Project Lead</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th rowspan="1" colspan="1">#</th>
										<th rowspan="1" colspan="1">Project Name</th>
										<th rowspan="1" colspan="1">Project Type</th>
										<th rowspan="1" colspan="1">Project Category</th>
										<th rowspan="1" colspan="1">Project Lead</th>
									</tr>
								</tfoot>
								<tbody>
									<c:choose>
										<c:when test="${ projects.size() > 0 }">
											<c:forEach items="${ projects }" var="p" varStatus="loop">	
												<c:if test="${loop.index % 2 == 0}">
													<tr role="row" class="even">
												</c:if>
												<c:if test="${loop.index % 2 != 0}">
													<tr role="row" class="odd">
												</c:if>
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
											<a href="../../projects/all/0" aria-controls="dataTable" class="page-link">First</a>
										</li>
									</c:if>
									<c:if test="${ currentPage > 0 }">
										<li class="paginate_button page-item previous" id="dataTable_previous">
											<a href="../../projects/all/0" aria-controls="dataTable" class="page-link">First</a>
										</li>
									</c:if>
								
									<%-- Previous button --%>
									<c:if test="${ currentPage <= 0 }">
										<li class="paginate_button page-item previous disabled" id="dataTable_previous">
											<a href="../../projects/all/${ currentPage - 1 }" aria-controls="dataTable" class="page-link">Previous</a>
										</li>
									</c:if>
									<c:if test="${ currentPage > 0 }">
										<li class="paginate_button page-item previous" id="dataTable_previous">
											<a href="../../projects/all/${ currentPage - 1 }" aria-controls="dataTable" class="page-link">Previous</a>
										</li>
									</c:if>
									
									<li class="paginate_button page-item next disabled" id="dataTable_next">
										<a aria-controls="dataTable"  tabindex="0" class="page-link">${ currentPage + 1 }</a>
									</li>
								
									<%-- Next button --%>
									<c:if test="${currentPage + 1 <= noOfPages}">
										<li class="paginate_button page-item next" id="dataTable_next">
											<a href="../../projects/all/${ currentPage + 1 }" aria-controls="dataTable" class="page-link">Next</a>
										</li>
									</c:if>
									<c:if test="${currentPage + 1 > noOfPages}">
										<li class="paginate_button page-item next disabled" id="dataTable_next">
											<a href="../../projects/all/${ currentPage + 1 }" aria-controls="dataTable" class="page-link">Next</a>
										</li>
									</c:if>
									
									<%-- Last button --%>
									<c:if test="${currentPage >= noOfPages}">
										<li class="paginate_button page-item next disabled" id="dataTable_next">
											<a href="../../projects/all/${ noOfPages }" aria-controls="dataTable" class="page-link">Last</a>
										</li>
									</c:if>
									<c:if test="${currentPage < noOfPages}">
										<li class="paginate_button page-item next" id="dataTable_next">
											<a href="../../projects/all/${ noOfPages }" aria-controls="dataTable" class="page-link">Last</a>
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




					<%-- <div class="row">
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
											aria-label="Name: activate to sort column descending">Project Name</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;"
											aria-label="Position: activate to sort column ascending">Project Type</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width:145px;"
											aria-label="Office: activate to sort column ascending">Project Category</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;"
											aria-label="Age: activate to sort column ascending">Project Lead</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th rowspan="1" colspan="1">#</th>
										<th rowspan="1" colspan="1">Project Name</th>
										<th rowspan="1" colspan="1">Project Type</th>
										<th rowspan="1" colspan="1">Project Category</th>
										<th rowspan="1" colspan="1">Project Lead</th>
									</tr>
								</tfoot>
								<tbody>
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
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-5">
							<div class="dataTables_info" id="dataTable_info" role="status"
								aria-live="polite">Showing ${ (currRecordPage * currentPage) + 1 } to ${ (currRecordPage * currentPage) + currRecordPage } of ${ projectCount } projects in ${ noOfPages } pages</div>
						</div>
						<div class="col-sm-12 col-md-7">
							<div class="dataTables_paginate paging_simple_numbers"
								id="dataTable_paginate">
								<ul class="pagination">
									<c:if test="${ currentPage <= 0 }">
									<li class="paginate_button page-item previous disabled"
										id="dataTable_previous"><a href="../../projects/all/${ currentPage - 1 }"
										aria-controls="dataTable" data-dt-idx="0" tabindex="0"
										class="page-link">Previous</a></li>
									</c:if>
									<c:if test="${ currentPage > 0 }">
									<li class="paginate_button page-item previous"
										id="dataTable_previous"><a href="../../projects/all/${ currentPage - 1 }"
										aria-controls="dataTable" data-dt-idx="0" tabindex="0"
										class="page-link">Previous</a></li>
									</c:if>
									
									<c:if test="${ currentPage <= noOfPages }">
									<li class="paginate_button page-item active"><a href="../../projects/all/${currentPage}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 1 }</a></li>
									</c:if>
									<c:if test="${ currentPage  > noOfPages }">
									<li class="paginate_button page-item active disabled"><a href="../../projects/all/${currentPage}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 1 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 1  <= noOfPages }">
									<li class="paginate_button page-item "><a href="../../projects/all/${currentPage + 1}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 2 }</a></li>
									</c:if>
									<c:if test="${ currentPage + 1  > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../projects/all/${currentPage + 1}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 2 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 2 <= noOfPages }">
									<li class="paginate_button page-item "><a href="../../projects/all/${currentPage + 2 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 3 }</a></li>
									</c:if>
										<c:if test="${ currentPage + 2 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../projects/all/${currentPage + 2 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 3 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 3 <= noOfPages }">
									<li class="paginate_button page-item"><a href="../../projects/all/${currentPage + 3 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 4 }</a></li>
									</c:if>
										<c:if test="${ currentPage + 3 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../projects/all/${currentPage + 3 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 4 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 4 <= noOfPages }">
									<li class="paginate_button page-item"><a href="../../projects/all/${currentPage + 4 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 5 }</a></li>
									</c:if>
									<c:if test="${ currentPage +4 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../projects/all/${currentPage + 4 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 5 }</a></li>
									</c:if>
									
									<c:if test="${ currentPage + 5 <= noOfPages }">
									<li class="paginate_button page-item"><a href="../../projects/all/${currentPage + 5 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 6 }</a></li>
									</c:if>
									<c:if test="${ currentPage + 5 > noOfPages }">
									<li class="paginate_button page-item disabled"><a href="../../projects/all/${currentPage + 5 }"
										aria-controls="dataTable" data-dt-idx="3" tabindex="0"
										class="page-link">${ currentPage + 6 }</a></li>
									</c:if>
									
									<c:if test="${currentPage + 1 <= noOfPages}">
									<li class="paginate_button page-item next" id="dataTable_next"><a
										href="../../projects/all/${ currentPage + 1 }" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a></li>
									</c:if>
									<c:if test="${currentPage + 1 > noOfPages}">
									<li class="paginate_button page-item next disabled" id="dataTable_next"><a
										href="../../projects/all/${ currentPage + 1 }" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a></li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> --%>
	
	
<!-- 	<script>

//quick search regex
<!-- var qsRegex;

//init Isotope
var $grid = $('.grid').isotope({
	itemSelector: '.grid-item',
	layoutMode: 'vertical',
	percentPosition: true,
	hiddenStyle: {
		opacity: 0
	},
	visibleStyle: {
		opacity: 1
	},
	filter: function() {
		return qsRegex ? $(this).text().match( qsRegex ) : true;
	}
});

//use value of search field to filter
var $quicksearch = $('.quicksearch').keyup( debounce( function() {
	qsRegex = new RegExp( $quicksearch.val(), 'gi' );
	$grid.isotope();
}, 300 ) );

$grid.on('shown.bs.collapse hidden.bs.collapse', function() {
$grid.isotope('layout');
})

//debounce so filtering doesn't happen every millisecond
function debounce( fn, threshold ) {
	var timeout;
	return function debounced() {
		if ( timeout ) {
			clearTimeout( timeout );
		}
		function delayed() {
			fn();
			timeout = null;
		}
		timeout = setTimeout( delayed, threshold || 100 );
	}
}
</script> -->
</body>
</html>