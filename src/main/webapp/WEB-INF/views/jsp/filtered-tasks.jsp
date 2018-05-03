<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
		<jsp:include page="filter-form.jsp"></jsp:include>

		<br>
		<div class="card-header">
			<i class="fa fa-table">FILTERED TASKS</i> 
		</div>
		<jsp:include page="table-for-tasks-withou-pagination.jsp"></jsp:include>
</body>
</html>