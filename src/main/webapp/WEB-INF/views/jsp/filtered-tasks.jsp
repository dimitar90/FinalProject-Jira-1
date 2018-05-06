<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
	</c:if>

<body>
		<jsp:include page="filter-form.jsp"></jsp:include>

		<br>
		<div class="card-header">
			<i class="fa fa-table">FILTERED TASKS</i> 
		</div>
		<jsp:include page="table-for-tasks-withou-pagination.jsp"></jsp:include>
</body>
</html>