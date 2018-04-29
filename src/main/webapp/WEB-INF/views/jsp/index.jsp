<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <jsp:include page="navigation-bar.jsp"></jsp:include>

<title>Log in</title>
</head>
<body>
	<h1>Log in to your account</h1>
	<form action="login" method="post">
		<table>
			<tr>
				<td>Enter email</td>
				<td><input type="email" name="email" required></td>
			</tr>
			<tr>
				<td>Enter password</td>
				<td><input type="password" name="password" required></td>
			</tr>
		</table>
		<input type="submit" value="Log in">
					<br>
				<a href="register">Sign up for and account</a>		
	</form>
</body>
</html>