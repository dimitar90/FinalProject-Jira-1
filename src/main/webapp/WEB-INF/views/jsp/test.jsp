<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid">
		<!-- Breadcrumbs-->
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
			<li class="breadcrumb-item active">Tables</li>
		</ol>
		<!-- Example DataTables Card-->
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
											<th class="sorting" tabindex="0" aria-controls="dataTable"
												rowspan="1" colspan="1" style="width: 168px;"
												aria-label="Name: activate to sort column ascending">Name</th>
											<th class="sorting" tabindex="0" aria-controls="dataTable"
												rowspan="1" colspan="1" style="width: 260px;"
												aria-label="Position: activate to sort column ascending">Position</th>
											<th class="sorting" tabindex="0" aria-controls="dataTable"
												rowspan="1" colspan="1" style="width: 121px;"
												aria-label="Office: activate to sort column ascending">Office</th>
											<th class="sorting" tabindex="0" aria-controls="dataTable"
												rowspan="1" colspan="1" style="width: 57px;"
												aria-label="Age: activate to sort column ascending">Age</th>
											<th class="sorting_desc" tabindex="0"
												aria-controls="dataTable" rowspan="1" colspan="1"
												style="width: 117px;"
												aria-label="Start date: activate to sort column ascending"
												aria-sort="descending">Start date</th>
											<th class="sorting" tabindex="0" aria-controls="dataTable"
												rowspan="1" colspan="1" style="width: 95px;"
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
											<td class="">Thor Walton</td>
											<td>Developer</td>
											<td>New York</td>
											<td>61</td>
											<td class="sorting_1">2013/08/11</td>
											<td>$98,540</td>
										</tr>
										<tr role="row" class="even">
											<td class="">Quinn Flynn</td>
											<td>Support Lead</td>
											<td>Edinburgh</td>
											<td>22</td>
											<td class="sorting_1">2013/03/03</td>
											<td>$342,000</td>
										</tr>
										<tr role="row" class="odd">
											<td class="">Jennifer Acosta</td>
											<td>Junior Javascript Developer</td>
											<td>Edinburgh</td>
											<td>43</td>
											<td class="sorting_1">2013/02/01</td>
											<td>$75,650</td>
										</tr>
										<tr role="row" class="even">
											<td class="">Haley Kennedy</td>
											<td>Senior Marketing Designer</td>
											<td>London</td>
											<td>43</td>
											<td class="sorting_1">2012/12/18</td>
											<td>$313,500</td>
										</tr>
										<tr role="row" class="odd">
											<td class="">Brielle Williamson</td>
											<td>Integration Specialist</td>
											<td>New York</td>
											<td>61</td>
											<td class="sorting_1">2012/12/02</td>
											<td>$372,000</td>
										</tr>
										<tr role="row" class="even">
											<td class="">Michael Silva</td>
											<td>Marketing Designer</td>
											<td>London</td>
											<td>66</td>
											<td class="sorting_1">2012/11/27</td>
											<td>$198,500</td>
										</tr>
										<tr role="row" class="odd">
											<td class="">Bradley Greer</td>
											<td>Software Engineer</td>
											<td>London</td>
											<td>41</td>
											<td class="sorting_1">2012/10/13</td>
											<td>$132,000</td>
										</tr>
										<tr role="row" class="even">
											<td class="">Dai Rios</td>
											<td>Personnel Lead</td>
											<td>Edinburgh</td>
											<td>35</td>
											<td class="sorting_1">2012/09/26</td>
											<td>$217,500</td>
										</tr>
										<tr role="row" class="odd">
											<td class="">Herrod Chandler</td>
											<td>Sales Assistant</td>
											<td>San Francisco</td>
											<td>59</td>
											<td class="sorting_1">2012/08/06</td>
											<td>$137,500</td>
										</tr>
										<tr role="row" class="even">
											<td class="">Zorita Serrano</td>
											<td>Software Engineer</td>
											<td>San Francisco</td>
											<td>56</td>
											<td class="sorting_1">2012/06/01</td>
											<td>$115,000</td>
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
	</div>
</body>
</html>