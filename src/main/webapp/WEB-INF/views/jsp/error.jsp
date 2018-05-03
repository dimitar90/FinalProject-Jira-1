<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@page isErrorPage="true"%>
<head>
<link href="../../vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="../../vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="../../vendor/datatables/dataTables.bootstrap4.css"
	rel="stylesheet">
<link href="../../css/sb-admin.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../../css/table.css">
<title></title>
</head>
<body>
	<h1>Error. . . Please dont report me!</h1>
	<% Exception e = (Exception) request.getAttribute("exception"); %>
	<h2>Reason: <%out.print(e.getMessage()); %></h2>
	<a href="index.jsp">Back to log in, sorry about that</a>
</body>
</html>