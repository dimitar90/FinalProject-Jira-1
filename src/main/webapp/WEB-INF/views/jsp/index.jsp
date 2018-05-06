<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" >
<jsp:include page="navigation-bar.jsp"></jsp:include>

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
          <div class="form-group">
            <div class="form-check">
              <!-- <label class="form-check-label">
                <input class="form-check-input" type="checkbox">Remember Password</label>
            </div> -->
          </div>
          <input class="btn btn-primary btn-block" type="submit" value="Log in" />  
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="http://localhost:8080/Jira/register">Sign up for an account</a>
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


<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>	
 <jsp:include page="navigation-bar.jsp"></jsp:include>
 <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bashIndex.css" />">

<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
 </head>

<!------ Include the above in your HEAD tag ---------->

    <div class="container">
        <div class="card card-container">
            <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
            <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
            <p id="profile-name" class="profile-name-card"></p>
            <form class="form-signin" action = "login" method="post">
                <span id="reauth-email" class="reauth-email"></span>
                <input type="email" name = "email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                <input type="password" name = "password" id="inputPassword" class="form-control" placeholder="Password" required>
                <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
            </form><!-- /form -->
            <a href="http://localhost:8080/Jira/register"> Sign up for an account</a>
        </div><!-- /card-container -->
    </div><!-- /container -->

</body>
</html> --%>