<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="table.css">
<jsp:include page="navigation-bar.jsp"></jsp:include>
<c:set var="user" value="${ user }" />
<c:set var="first" value="0" />

<body>
	<h3>All tasks:</h3>
	<br>

	<form style="display: inline-block" action="../filter" method="post">
		<p>Filter by issue type</p>
		<c:forEach items="${ issueTypes }" var="it">
			<input type="checkbox" name="selectedIssueTypeIds" value="${it.id}"> ${it.type.value} <br>
		</c:forEach>
		<p>Show tasks between</p>
		<input type="date" name="firstDate"> <br> and <br> <input
			type="date" name="secondDate"><br> due date <input
			type="submit" value="Filter">
	</form>

	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i> Data Table Example
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<div id="dataTable_wrapper"
					class="dataTables_wrapper container-fluid dt-bootstrap4">
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="dataTables_length" id="dataTable_length">
								<label>Show <select name="dataTable_length"
									aria-controls="dataTable" class="form-control form-control-sm"><option
											value="10">10</option>
										<option value="25">25</option>
										<option value="50">50</option>
										<option value="100">100</option></select> entries
								</label>
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
										<th class="sorting_asc" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 202px;"
											aria-sort="ascending"
											aria-label="Name: activate to sort column descending">Name</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 304px;"
											aria-label="Position: activate to sort column ascending">Position</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 145px;"
											aria-label="Office: activate to sort column ascending">Office</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 71px;"
											aria-label="Age: activate to sort column ascending">Age</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 140px;"
											aria-label="Start date: activate to sort column ascending">Start
											date</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1" style="width: 114px;"
											aria-label="Salary: activate to sort column ascending">Salary</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th rowspan="1" colspan="1">Name</th>
										<th rowspan="1" colspan="1">Position</th>
										<th rowspan="1" colspan="1">Office</th>
										<th rowspan="1" colspan="1">Age</th>
										<th rowspan="1" colspan="1">Start date</th>
										<th rowspan="1" colspan="1">Salary</th>
									</tr>
								</tfoot>
								<tbody>

									<tr role="row" class="odd">
										<td class="sorting_1">Airi Satou</td>
										<td>Accountant</td>
										<td>Tokyo</td>
										<td>33</td>
										<td>2008/11/28</td>
										<td>$162,700</td>
									</tr>
									<tr role="row" class="even">
										<td class="sorting_1">Angelica Ramos</td>
										<td>Chief Executive Officer (CEO)</td>
										<td>London</td>
										<td>47</td>
										<td>2009/10/09</td>
										<td>$1,200,000</td>
									</tr>
									<tr role="row" class="odd">
										<td class="sorting_1">Ashton Cox</td>
										<td>Junior Technical Author</td>
										<td>San Francisco</td>
										<td>66</td>
										<td>2009/01/12</td>
										<td>$86,000</td>
									</tr>
									<tr role="row" class="even">
										<td class="sorting_1">Bradley Greer</td>
										<td>Software Engineer</td>
										<td>London</td>
										<td>41</td>
										<td>2012/10/13</td>
										<td>$132,000</td>
									</tr>
									<tr role="row" class="odd">
										<td class="sorting_1">Brenden Wagner</td>
										<td>Software Engineer</td>
										<td>San Francisco</td>
										<td>28</td>
										<td>2011/06/07</td>
										<td>$206,850</td>
									</tr>
									<tr role="row" class="even">
										<td class="sorting_1">Brielle Williamson</td>
										<td>Integration Specialist</td>
										<td>New York</td>
										<td>61</td>
										<td>2012/12/02</td>
										<td>$372,000</td>
									</tr>
									<tr role="row" class="odd">
										<td class="sorting_1">Bruno Nash</td>
										<td>Software Engineer</td>
										<td>London</td>
										<td>38</td>
										<td>2011/05/03</td>
										<td>$163,500</td>
									</tr>
									<tr role="row" class="even">
										<td class="sorting_1">Caesar Vance</td>
										<td>Pre-Sales Support</td>
										<td>New York</td>
										<td>21</td>
										<td>2011/12/12</td>
										<td>$106,450</td>
									</tr>
									<tr role="row" class="odd">
										<td class="sorting_1">Cara Stevens</td>
										<td>Sales Assistant</td>
										<td>New York</td>
										<td>46</td>
										<td>2011/12/06</td>
										<td>$145,600</td>
									</tr>
									<tr role="row" class="even">
										<td class="sorting_1">Cedric Kelly</td>
										<td>Senior Javascript Developer</td>
										<td>Edinburgh</td>
										<td>22</td>
										<td>2012/03/29</td>
										<td>$433,060</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-5">
							<div class="dataTables_info" id="dataTable_info" role="status"
								aria-live="polite">Showing 1 to 10 of 57 entries</div>
						</div>
						<div class="col-sm-12 col-md-7">
							<div class="dataTables_paginate paging_simple_numbers"
								id="dataTable_paginate">
								<ul class="pagination">
									<li class="paginate_button page-item previous disabled"
										id="dataTable_previous"><a href="#"
										aria-controls="dataTable" data-dt-idx="0" tabindex="0"
										class="page-link">Previous</a></li>
									<li class="paginate_button page-item active"><a href="#"
										aria-controls="dataTable" data-dt-idx="1" tabindex="0"
										class="page-link">1</a></li>
									<li class="paginate_button page-item "><a href="#"
										aria-controls="dataTable" data-dt-idx="2" tabindex="0"
										class="page-link">2</a></li>
									<li class="paginate_button page-item "><a href="#"
										aria-controls="dataTable" data-dt-idx="3" tabindex="0"
										class="page-link">3</a></li>
									<li class="paginate_button page-item "><a href="#"
										aria-controls="dataTable" data-dt-idx="4" tabindex="0"
										class="page-link">4</a></li>
									<li class="paginate_button page-item "><a href="#"
										aria-controls="dataTable" data-dt-idx="5" tabindex="0"
										class="page-link">5</a></li>
									<li class="paginate_button page-item "><a href="#"
										aria-controls="dataTable" data-dt-idx="6" tabindex="0"
										class="page-link">6</a></li>
									<li class="paginate_button page-item next" id="dataTable_next"><a
										href="#" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="card-footer small text-muted">Updated yesterday at
			11:59 PM</div>
	</div>

	<table>
		<tr>
			<th>Project</th>
			<th>Summary</th>
			<th>Assignee</th>
			<th>Due date</th>
			<th>Priority</th>
			<th>State</th>
			<th>Details</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>

		<c:forEach items="${ tasks }" var="t">
			<tr>
				<td>${t.project.name}</td>
				<td>${t.summary}</td>
				<td>${t.assignee.name}</td>
				<td>${t.dueDate}</td>
				<td>${t.priority.type.value}</td>
				<td>${t.state.type.value}</td>
				<td><a href="../detail/${ t.id }">Show details</a></td>
				<c:if test="${not empty user}">
					<c:if
						test="${(user.id == t.creator.id || user.id == t.assignee.id)}">
						<td><a href="../edit/${ t.id }">Edit</a></td>
						<td><a href="../delete/${ t.id }">Delete</a></td>
					</c:if>
				</c:if>
			</tr>
			<br>
		</c:forEach>
	</table>

	<%--if currentPage == firstPage button for first is hidden --%>
	>
	<c:if test="${currentPage > first}">
		<a href="${first}">First</a>
	</c:if>

	<%--if currentPage == firstPage button for previous is hidden --%>
	>
	<c:if test="${currentPage != first}">
		<td><a href="${currentPage - 1}">Previous</a></td>
	</c:if>

	<%--if currentPage == lastPage button for next is hidden --%>
	>
	<c:if test="${currentPage < noOfPages}">
		<td><a href="${currentPage + 1}">Next</a></td>
	</c:if>

	<%--if currentPage == lastPage button for last is hidden --%>
	>
	<c:if test="${currentPage < noOfPages}">
		<a href="${noOfPages}">Last</a>
	</c:if>
</body>
</html>