
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="navigation-bar.jsp"></jsp:include>
 <%-- <head>

    <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/register.css"/>">

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
 --%>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
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
    <div class="card card-register mx-auto mt-5">
      <div class="card-header">Sign up for Jira <small>It's free!</small></div>
      <div class="card-body">
        <form action = "register" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label for="exampleInputName">Full name</label>
                <input class="form-control" id="exampleInputName" type="text" name ="username" aria-describedby="nameHelp" placeholder="Enter full name"
                required = "required" pattern =".{4,}" title="minimum 4 characters" required autocomplete ="off">
              </div>
            </div>
          </div>
          <div class="form-group">
            <label for="exampleInputEmail1">Email address</label>
            <input class="form-control" id="exampleInputEmail1" name="email" type="email" aria-describedby="emailHelp" placeholder="Enter email"
            required= "required" pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?" autocomplete = "off">
          </div>
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label for="exampleInputPassword1">Password</label>
                <input class="form-control" id="exampleInputPassword1" name="password" type="password" placeholder="Password"
                required = "required" pattern =".{4,}" title="Minimum 4 characters required" autocomplete ="off">
              </div>
              <div class="col-md-6">
                <label for="exampleConfirmPassword">Confirm password</label>
                <input class="form-control" id="exampleConfirmPassword" name="confirmPassword" type="password" placeholder="Confirm password" 
                 required = "required" pattern =".{4,}" title="Minimum 4 characters required" autocomplete ="off">
              </div>
            </div>
          </div>
            <div class="form-group">
            <label for="exampleInputEmail1">Upload image</label>
            <input type="file" accept="image/*" name="singleFile">
          </div>
          <input class="btn btn-primary btn-block" type="submit" required="" value="Sign up"/>     
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="http://localhost:8080/Jira/">Already have an account? Log in</a>
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
<%-- <body>

<script type="text/javascript" src="http://www.clubdesign.at/floatlabels.js"></script>

<div class="container">
        <div class="row centered-form">
        <div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
        	<div class="panel panel-default">
        		<div class="panel-heading">
			    		<h3 class="panel-title">Sign up for Jira <small>It's free!</small></h3>
			 			</div>
			 			<div class="panel-body">
			    		<form role="form" action = "register" method="post" enctype="multipart/form-data">
			    			<div class="row">
			    				<div class="col-xs-6 col-sm-6 col-md-6">
			    					<div class="form-group">
			                <input type="text" name="username" id="first_name" class="form-control input-sm floatlabel" placeholder="Full name">
			    					</div>
			    				</div>
			    				<!-- <div class="col-xs-6 col-sm-6 col-md-6">
			    					<div class="form-group">
			    						<input type="text" name="last_name" id="last_name" class="form-control input-sm" placeholder="Last Name">
			    					</div>
			    				</div> -->
			    			</div>

			    			<div class="form-group">
			    				<input type="email" name="email" id="email" class="form-control input-sm" placeholder="Email Address">
			    			</div>

			    			<div class="row">
			    				<div class="col-xs-6 col-sm-6 col-md-6">
			    					<div class="form-group">
			    						<input type="password" name="password" id="password" class="form-control input-sm" placeholder="Password">
			    					</div>
			    				</div>
			    				<div class="col-xs-6 col-sm-6 col-md-6">
			    					<div class="form-group">
			    						<input type="password" name="confirmPassword" id="password_confirmation" class="form-control input-sm" placeholder="Confirm Password">
			    					</div>
			    				</div>
			    			</div>
			    			<div class="col-xs-6 col-sm-6 col-md-6">
			    					<div class="form-group">
			    						<input type="file" accept="image/*" name="singleFile">
			    					</div>
			    				</div>
			    			</div>
			    			<input type="submit" value="Sign up" class="btn btn-info btn-block">
			    		</form>
			    	</div>
	    		</div>
    		</div>
    	</div>
    </div>
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
</html> --%>