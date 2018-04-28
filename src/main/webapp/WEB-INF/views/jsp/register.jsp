	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <jsp:include page="navigation-bar.jsp"></jsp:include>

<title>Sign up</title>
</head>
<body>
	<form action="register" method="POST" enctype="multipart/form-data">>
				<table>
					<tr>
						<td>
							Enter email
						</td>
						<td>
							<input type="email" name="email" placeholder="email" required>
						</td>
					</tr>
					<tr>
						<td>
							Enter full name
						</td>
						<td>
							<input type="text" name="username" placeholder="username" required>
						</td>
					</tr>
					<tr>
						<td>
							Create password
						</td>
						<td>
							<input type="password" name="password" placeholder="password" required>
						</td>
					</tr>
					<tr>
						<td>
							Confirm password
						</td>
						<td>
							<input type="password" name="confirm password" placeholder="confirm password" required>
						</td>
					</tr>
					</table>
					
					<tr>
						<td>
							Upload file
						</td>
						<td>
							<input type="file" accept="image/*" name="create"><br>
							<input type="submit" value="Sign up">
					<br>
						</td>
			</form>
			<br>
				Already have an Jira account? <a href="index.jsp">Log in</a>	
	</body>
</html>