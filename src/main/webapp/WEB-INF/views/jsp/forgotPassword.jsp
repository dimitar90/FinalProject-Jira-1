<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
</c:if>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
    <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
  <title>IT Talents</title>
  <!-- Bootstrap core CSS-->
  <!-- Custom fonts for this template-->
    <link rel="stylesheet" href="<c:url value="/css/font-awesome.min.css" />">
   <link rel="stylesheet" href="<c:url value="/css/bootsrap2.css" />">
  <!-- Custom styles for this template-->
  <link rel="stylesheet" href="<c:url value="/css/admin-3.css" />">
</head>
<body class="bg-dark">
  <div class="container">
    <div class="card card-login mx-auto mt-5">
      <div class="card-header">Reset Password</div>
      <div class="card-body">
        <div class="text-center mt-4 mb-5">
          <h4>Forgot your password?</h4>
          <p>Enter your email address and we will send you instructions on how to reset your password.</p>
        </div>
         <c:if test="${ requestScope.errorMsg != null}">
          <div class="form-group">
            <div class="form-check">
              <div class="alert alert-warning" role="alert">
					${errorMsg} <strong>Try again!</strong>
				</div>
				</c:if>
        
        <form action = "<c:url value='/forgotPassword'></c:url>" method = "post">
          <div class="form-group">
            <input class="form-control" id="exampleInputEmail1" name = "email" type="email" aria-describedby="emailHelp" placeholder="Enter new email address.."
             required= "required" pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?" autocomplete = "off">
          </div>
          <input class="btn btn-primary btn-block" type="submit" value="Reset password" /> 
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="http://localhost:8080/Jira/register">Register an Account</a>
          <a class="d-block small" href="http://localhost:8080/Jira/">Login Page</a>
        </div>
      </div>
    </div>
  </div>
  <!-- Bootstrap core JavaScript-->




</body>
</html>