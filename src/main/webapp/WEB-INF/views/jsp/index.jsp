<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html lang="en" >
<c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
</c:if>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
     <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
  <title>IT Talents</title>
  <!-- Bootstrap core CSS-->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom styles for this template-->
  <link href="css/sb-admin.css" rel="stylesheet">
</head>

<body class="bg-dark">
  <div class="container">
    <div class="card card-login mx-auto mt-5">
      <div class="card-header">Login</div>
      <div class="card-body">
        <form action = "login" method="post">
          <div class="form-group">
        
            <label for="exampleInputEmail1">Email address</label>
            <input class="form-control" id="exampleInputEmail1" name = "email" type="email" aria-describedby="emailHelp" placeholder="Enter email"
            required = "required" pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?" autocomplete = "off">
          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input class="form-control" id="exampleInputPassword1" name = "password" type="password" placeholder="Password"
            required="required" pattern=".{4,}" title="Minimum 4 characters required" autocomplete="off">
          </div>
            <c:if test="${ requestScope.errorMsg != null}">
          <div class="form-group">
            <div class="form-check">
              <div class="alert alert-danger" role="alert">
					${errorMsg} <strong>Try again!</strong>
				</div>
				</c:if>
          </div>
          <input class="btn btn-primary btn-block" type="submit" value="Log in" />  
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="http://localhost:8080/Jira/register">Sign up for an account</a>
        </div>
        <div class="text-center">
          <a class="d-block small mt-3" href="http://localhost:8080/Jira/forgotPassword">forgot Password?</a>
        </div>
      </div>
    </div>
  </div>
  </div>
  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
</body>
</html>


