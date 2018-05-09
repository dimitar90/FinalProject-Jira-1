<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>
<c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
</c:if>
 <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
 <link rel="stylesheet" href="<c:url value= "/css/user.css"/>">



<title>IT Talents</title>
</head>
	<body>

<h2>${userName} projects</h2>

<!-- <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for names.." title="Type in a name"> -->


<form class="example" action="../../search" method="post">
  <input type="text" placeholder="Search..." name="search"  id="myInput" onkeyup="myFunction()" title="Type in a name" required autocomplete="off">
 
</form>
 
 <br>
<table id="myTable">
  <tr class="header">
    <th style="width:60%;">Project name</th>
    <th style="width:20%;">Project type</th>
    <th style="width:20%;">Project category</th>
  </tr>
  	<c:forEach items="${ userProjects }" var="p">
  <tr>
    <td><a href="http://localhost:8080/Jira/projects/projectId/${p.id}">${p.name}</a></td>
    <td>${p.projectType}</td>
    <td>${p.projectCategory}</td>
  </tr>
  </c:forEach>
</table>

<script>
function myFunction() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("myTable");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}
</script>

</body>
