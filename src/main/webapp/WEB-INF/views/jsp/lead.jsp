<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <jsp:include page="navigation-bar.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">	
<title>Project lead profile view</title>
	
	<br>
	<div>
	<img src="../getPicSession?dto=${dto.email} " height="100" width="100" class="avatar img-circle" alt="avatar">
	</div>
	
	<div>
	<ul>
  	<li>${dto.name} </li>
  	<li>${dto.email} </li>
	</ul>
	</div>
</head>
</body>
</html>