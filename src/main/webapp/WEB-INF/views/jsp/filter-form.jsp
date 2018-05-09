<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form action="<c:url value="http://localhost:8080/Jira/tasks/filter" />"
	method="POST">
	<div class="row"
		style="height: 15%; display: flex; justify-content: center;">
		<div class="col-sm-3"
			style="display: table-cell; vertical-align: middle; text-align: center">
			<div class="form-group">
				<label ><b>Filter between two due dates:&ensp;FROM</b></label>
				<div class="input-group">
					<div class="input-group-addon">
						<i class="fa fa-clock-o"></i>
					</div>
					<input type="date" class="form-control pull-right" name="firstDate">
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
	</div>

	<div class="row"
		style="height: 8%; display: flex; justify-content: center;">
		<c:forEach items="${ issueTypes }" var="it">
			<div class="col-sm-2"
				style="display: table-cell; vertical-align: middle; text-align: left">
				<div class="form-group">
					<div class="input-group">
						<input type="checkbox" name="selectedIssueTypeIds"
							value="${it.id}"><b>${it.type.value}</b>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<div class="row"
		style="height: 8%; display: flex; justify-content: center;">
		<div class="form-group">
			<button type="submit" class="btn btn-default">Combined
				Search</button>
		</div>
	</div>
</form>