<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Charts</title>

<link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/sb-admin.css" />" rel="stylesheet">

</head>

<c:if test='${not empty sessionScope.user}'>
	<jsp:include page="nav-bar-main.jsp"></jsp:include>
</c:if>
<c:if test='${empty sessionScope.user}'>
	<jsp:include page="navigation-bar.jsp"></jsp:include>
</c:if>

<div>
	<form
		action="<c:url value="http://localhost:8080/Jira/charts/taskInfo" />"
		method="POST">
		<div class="row"
			style="height: 20%; display: flex; justify-content: center;">
			<div class="col-sm-3"
				style="display: table-cell; vertical-align: middle; text-align: center">
				<div class="form-group">
					<label><b>Please select date range:</b>&ensp;<b>FROM</b></label>
					<div class="input-group">
						<div class="input-group-addon">
							<i class="fa fa-clock-o"></i>
						</div>
						<input type="date" class="form-control pull-right"
							name="firstDate">
					</div>
				</div>
			</div>

			<div class="col-sm-3"
				style="display: table-cell; vertical-align: middle; text-align: center">
				<div class="form-group">
					<label><b>TO</b></label>
					<div class="input-group">
						<div class="input-group-addon">
							<i class="fa fa-clock-o"></i>
						</div>
						<input type="date" class="form-control pull-right"
							name="secondDate">
					</div>
				</div>
			</div>
			<div class="col-sm-3"
				style="display: table-cell; vertical-align: middle; text-align: center">
				<div class="form-group">
					<label><b>Valid date range</b></label> <input type="text"
						class="form-control pull-right" name="validLimits"
						value="${ validDataRange }" disabled>
				</div>
			</div>
			<div class="col-sm-1"
				style="display: table-cell; vertical-align: middle; text-align: center">
				<div class="form-group">
					<label><b>Action</b></label><br>
					<button type="submit" class="btn btn-default">Filter</button>
				</div>
			</div>
		</div>
	</form>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="card mb-3">
					<div class="card-header">
						<i class="fa fa-pie-chart"></i><b>Pie Chart For Issue Types</b>
					</div>
					<div class="card-body">
						<div
							style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"
							class="chartjs-size-monitor">
							<div class="chartjs-size-monitor-expand"
								style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
								<div
									style="position: absolute; width: 1000000px; height: 1000000px; left: 0; top: 0"></div>
							</div>
							<div class="chartjs-size-monitor-shrink"
								style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
								<div
									style="position: absolute; width: 200%; height: 200%; left: 0; top: 0"></div>
							</div>
						</div>
						<c:if test="${ isIssuesResults eq false }">
							<h3>
								<b>No results found!</b>
							</h3>
						</c:if>

						<script type="text/javascript"
							src="https://www.gstatic.com/charts/loader.js"></script>
						<script type="text/javascript">
				      google.charts.load("current", {packages:["corechart"]});
				      google.charts.setOnLoadCallback(drawChart);
				      function drawChart() {
				        var data = google.visualization.arrayToDataTable([
				          ['Task', 'Hours per Day'],
				          ['New Feature',     ${ issues.get("New Feature") }],
				          ['Bug',      ${ issues.get("Bug") }],
				          ['Story',  ${ issues.get("Story") }],
				          ['Task', ${ issues.get("Task") }],
				          ['Improvement',    ${ issues.get("Improvement") }]
				        ]);
				
				        var options = {
				          pieHole: 0.4,
				        };
				
				        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
				        chart.draw(data, options);
				      }
				    </script>
						<div id="donutchart" style="width: 900px; height: 500px;"></div>

					</div>
					<div class="card-footer small text-muted">${ currentViewDateRange }</div>
				</div>
			</div>

			<div class="col-lg-12">
				<div class="card mb-3">
					<div class="card-header">
						<i class="fa fa-pie-chart"></i> <b>Pie Chart For States</b>
					</div>
					<div class="card-body">
						<div
							style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"
							class="chartjs-size-monitor">
							<div class="chartjs-size-monitor-expand"
								style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
								<div
									style="position: absolute; width: 1000000px; height: 1000000px; left: 0; top: 0"></div>
							</div>
							<div class="chartjs-size-monitor-shrink"
								style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
								<div
									style="position: absolute; width: 200%; height: 200%; left: 0; top: 0"></div>
							</div>
						</div>
						<c:if test="${ isStatesResults eq false }">
							<h3>
								<b>No results found!</b>
							</h3>
						</c:if>

						<script type="text/javascript"
							src="https://www.gstatic.com/charts/loader.js"></script>
						<script type="text/javascript">
			      google.charts.load("current", {packages:["corechart"]});
			      google.charts.setOnLoadCallback(drawChart);
			      function drawChart() {
			        var data = google.visualization.arrayToDataTable([
			          ['Task', 'Hours per Day'],
			          ['To do', ${ states.get("To do") }],
			          ['In progress', ${ states.get("In progress") }],
			          ['Code Review', ${ states.get("Code Review") }],
			          ['Done', ${ states.get("Done") }]
			        ]);
			
			        var options = {
			          is3D: true,
			        };
			
			        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
			        chart.draw(data, options);
			      }
			    </script>
						<div id="piechart_3d" style="width: 900px; height: 500px;"></div>
					</div>
					<div class="card-footer small text-muted">${ currentViewDateRange }</div>
				</div>
			</div>
		</div>
	</div>
</div>