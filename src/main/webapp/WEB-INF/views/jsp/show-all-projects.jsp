<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<!-- Bootstrap core CSS-->	
<link href="../../vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom fonts for this template-->
<link href="../../vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<!-- Page level plugin CSS-->
<link href="../../vendor/datatables/dataTables.bootstrap4.css"
	rel="stylesheet">
<!-- Custom styles for this template-->
<link href="../../css/sb-admin.css" rel="stylesheet">
 <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">

<title>View all projects</title>
</head>
	  <jsp:include page="navigation-bar.jsp"></jsp:include><c:set var="first" value="0" />
<body>
	<%-- <h1>All projects</h1>
	<table>
		<tr>
			<th>Project name</th>
			<th>Project type</th>
			<th class="project">Project category</th>
			<th class="project">Project lead</th>
		</tr>
		<c:forEach items="${ allProjects }" var="p">
		<tr>	
			<td>
			<a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a>
			</td> 
			<td>${p.projectType}</td>
			<td class="project">${p.projectCategory}</td>
			<td>
			<a href="http://localhost:8080/Jira/leadId/${p.projectLeadId}">${p.projectLead}</a>
			</td>
		</tr>
		</c:forEach>
	</table>
	 --%>
	
	
	
	
	<!--            							 -->
	
	
		<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> ALL PROJECTS VIEW
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<div id="dataTable_wrapper"
					class="dataTables_wrapper container-fluid dt-bootstrap4">
					
					<%-- <div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="dataTables_length" id="dataTable_length">
							<!-- 	
								<form action="../../tasks/goToPage" method="POST">
								Go to page: <input type="number" name="page">
											<input type="submit" value="Go" />
								</form> -->
								<label>Show <select id="dataTable_length" name="dataTable_length"
									aria-controls="dataTable" class="form-control form-control-sm">
									<c:forEach items="${ allRowCounts }" var="rowCount">
										<c:if test="${rowCount == currentRowsOfPage}">
											<option value="${rowCount}" selected="selected">${ rowCount }</option>
										</c:if>
										<c:if test="${rowCount != currentRowsOfPage}">
											<option value="${rowCount}">${ rowCount }</option>
										</c:if>
									</c:forEach>
									</select> entries
								</label>
								
							</div>
						</div> --%>
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
											rowspan="1" colspan="1" style="width: 145px;"
											aria-label="Office: activate to sort column ascending">Project Category</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;"
											aria-label="Age: activate to sort column ascending">Project Lead</th>
										<!-- <th class="sorting" tabindex="0" aria-controls="dataTable"
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
											aria-label="Salary: activate to sort column ascending">Actions</th> -->
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th rowspan="1" colspan="1">#</th>
										<th rowspan="1" colspan="1">Project Name</th>
										<th rowspan="1" colspan="1">Project Type</th>
										<th rowspan="1" colspan="1">Project Category</th>
										<th rowspan="1" colspan="1">Project Lead</th>
										<!-- <th rowspan="1" colspan="1">Priority</th>
										<th rowspan="1" colspan="1">State</th>
										<th rowspan="1" colspan="1">Details</th>
										<th rowspan="1" colspan="1">Actions</th> -->
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
											<a href="http://localhost:8080/Jira/leadId/${p.projectLeadId}">${p.projectLead}</a>
										</td>
										<%-- <td>${t.priority.type.value}</td> --%>
										<%-- <td>${t.state.type.value}</td> --%>
										<%-- <td><a href="../detail/${ t.id }">Show details</a></td>
										<td>
											<c:if test="${not empty user}">
												<c:if
													test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
													<a href="../edit/${ t.id }">[Edit]</a>
													<a href="../delete/${ t.id }">[Delete]</a>
												</c:if>
											</c:if> --%>
										<!-- </td> -->
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
											<a href="http://localhost:8080/Jira/leadId/${p.projectLeadId}">${p.projectLead}</a>
										</td>
										<%-- <td>${t.priority.type.value}</td>
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
										</td> --%>
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
	</div>
	
	
	<%-- <div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> ALL PROJECTS
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
										<th class="sorting_asc" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;"
											aria-sort="ascending"
											aria-label="Name: activate to sort column descending">Project name</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;"
											aria-label="Position: activate to sort column ascending">Project type</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 145px;"
											aria-label="Office: activate to sort column ascending">Project category</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;"
											aria-label="Age: activate to sort column ascending">Project lead</th>
								</tr>
								</thead>
								<tfoot>
									<tr>
										<th rowspan="1" colspan="1">Project name</th>
										<th rowspan="1" colspan="1">Project type</th>
										<th rowspan="1" colspan="1">Project category</th>
										<th rowspan="1" colspan="1">Project lead</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach items="${ allProjects }" var="p" varStatus="loop">
									<c:if test="${loop.index % 2 == 0}">
									<tr role="row" class="even">
										<td>
										<a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a>
										</td>
										<td>${p.projectType}</td>
										<td>${p.projectCategory}</td>
										<td>${p.projectCategory}</td>
										<td>
										<a href="http://localhost:8080/Jira/leadId/${p.projectLeadId}">${p.projectLead}</a>
										</td>
									</tr>
									</c:if>
									<c:if test="${loop.index % 2 == 1}">
									<tr role="row" class="odd">
										<td><a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a></td>
										<td>${p.projectType}</td>
										<td>${t.assignee.name}</td>
										<td>${p.projectCategory}</td>
										<td><a href="http://localhost:8080/Jira/leadId/${p.projectLeadId}">${p.projectLead}</a></td>
									</tr>
									</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div> --%>
					

					<%-- <div class="row">
						<div class="col-sm-12 col-md-5">
							<div class="dataTables_info" id="dataTable_info" role="status"
								aria-live="polite">Showing ${ (currRecordPage * currentPage) + 1 } to ${ (currRecordPage * currentPage) + currRecordPage } of ${ projectCount } projects</div>
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
									<li class="paginate_button page-item active">
									<a href="../../projects/all/${currentPage}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 1 }</a>
										</li>
									<li class="paginate_button page-item "><a href="../../projects/all/${currentPage + 1}"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 2 }</a></li>
									<li class="paginate_button page-item "><a href="../../projects/all/${currentPage + 2 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 3 }</a></li>
									<li class="paginate_button page-item "><a href="../../projects/all/${currentPage + 3 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 4 }</a></li>
									<li class="paginate_button page-item "><a href="../../projects/all/${currentPage + 4 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 5 }</a></li>
									<li class="paginate_button page-item "><a href="../../projects/all/${currentPage + 5 }"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">${ currentPage + 6 }</a></li>
									<li class="paginate_button page-item next" id="dataTable_next"><a
										href="../../projects/all/${ currentPage + 1 }" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> --%>

	
	
	<!--            							 -->
	
	 <%-- 	if currentPage == firstPage button for first is hidden>
	 <c:if test="${currentPage > first}">
	 	<a href="${first}">First</a>
     </c:if>
     
    if currentPage == firstPage button for previous is hidden>
    <c:if test="${currentPage != first}">
    	<td><a href="${currentPage - 1}">Previous</a></td>
    </c:if>
    
    if currentPage == lastPage button for next is hidden>
    <c:if test="${currentPage < noOfPages}">
        <td><a href="${currentPage + 1}">Next</a></td>
    </c:if>
    
     if currentPage == lastPage button for last is hidden>
     <c:if test="${currentPage < noOfPages}">
			<a href="${noOfPages}">Last</a>
     </c:if> --%> 
</body>
</html>