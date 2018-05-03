<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form  action="<c:url value="./filter" />" method="POST">
		<span>
			Filter by issue type
			<c:forEach items="${ issueTypes }" var="it">
			<input type="checkbox" name="selectedIssueTypeIds" value="${it.id}"> ${it.type.value}
		</c:forEach>
		</span>
		<br>
		<span>
			Show tasks between <input type="date" name="firstDate"> 
			and <input type="date" name="secondDate"> due date
		</span>
		<input type="submit" value="Filter" />
</form>