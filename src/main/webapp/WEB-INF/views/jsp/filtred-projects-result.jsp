<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="nav-bar-main.jsp"></jsp:include>


<body>	
<jsp:include page="category-filter-form.jsp"></jsp:include>

	<br>
		<div class="card-header">
			<i class="fa fa-table">FILTERED PROJECTS</i> 
		</div>

		<jsp:include page="table-for-projects-no-pagination.jsp"></jsp:include>

</body>
</html>