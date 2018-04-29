
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="navigation-bar.jsp"></jsp:include>

<title>Sign up</title>
</head>
<body>
	<form action="register" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>Enter email</td>
				<td><input type="email" name="email" placeholder="email"
					required></td>
			</tr>
			<tr>
				<td>Enter full name</td>
				<td><input type="text" name="username" placeholder="username"
					required></td>
			</tr>
			<tr>
				<td>Create password</td>
				<td><input type="password" name="password"
					placeholder="password" required></td>
			</tr>
			<tr>
				<td>Confirm password</td>
				<td><input type="password" name="confirmPassword"
					placeholder="confirm password" required></td>
			</tr>
		</table>

		<tr>
			<td>Upload file</td>
			<td><input type="file" accept="image/*" name="singleFile"><br>
				<input type="submit" value="Sign up"> <br></td>
	</form>
	<br>
	<!-- hyper link to log in -->
	<c:url var="URL" value="login">
		<c:param name="param" value="${parameter}" />
	</c:url>
	<a href="<c:out value="${URL}"/>">Log in</a>
</body>
</html>