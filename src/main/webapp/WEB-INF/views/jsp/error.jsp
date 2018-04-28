<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@page isErrorPage="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<title>Error</title>
</head>
<body>
	<h1>Error. . . Please dont report me!</h1>
	<% Exception e = (Exception) request.getAttribute("exception"); %>
	<h2>Reason: <%out.print(e.getMessage()); %></h2>
	<a href="index.jsp">Back to log in, sorry about that</a>
</body>
</html>