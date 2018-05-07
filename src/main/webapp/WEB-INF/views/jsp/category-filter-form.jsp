<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form  action="<c:url value="http://localhost:8080/Jira/projects/filterCategory" />" method="POST">
		<span>
			Filter by category
			<c:forEach items="${ categories }" var="c">
			<input type="checkbox" name="selectedCategoriesId" value="${c.id}"> ${c.category.value}
		</c:forEach>
		</span>
		<br>
		<input type="submit" value="Filter" />
</form>