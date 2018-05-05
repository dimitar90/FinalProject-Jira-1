<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="nav-bar-main.jsp"></jsp:include>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
    <%-- <%@ page import="com.jira.model.pojo.User" %>  --%>
 <head>
<%--  <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">	  --%> 
	<link rel="stylesheet" href="<c:url value="/css/style.css" />">
    <title>IT Talents</title>
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/default.css" id="theme" rel="stylesheet">
 
	 </head>
<body>

<div class="container-fluid">
                <div class="row bg-title">
                    <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                        <h4 class="page-title">Jira profile</h4> 
                        </div>
                    <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                       <!--  <a href="https://wrappixel.com/templates/ampleadmin/" target="_blank" class="btn btn-danger pull-right m-l-20 hidden-xs hidden-sm waves-effect waves-light">Upgrade to Pro</a> -->
                        <ol class="breadcrumb">
                          <!--   <li><a href="#">Dashboard</a></li> -->
                            <li class="active">Profile Page</li>
                        </ol>
                    </div>
                </div>
                
                <!-- .row -->
                <div class="row">
                    <div class="col-md-4 col-xs-12">
                        <div class="white-box">
                        
                        <form action="./changePic" method="post" enctype="multipart/form-data">
                            <div class="user-bg">
                             <img src="getPicSession?user=${user.email}" width="65%" height="250" alt="user">
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <input type="file" accept="image/*" name="fileForChange" class="form-control form-control-line"> </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <button class="btn btn-success">Change picture</button>
                                    </div>
                                </div>
                            </div>
                            </form>
                            
                            
                        </div>
                    </div>
                    <div class="col-md-8 col-xs-12">
                        <div class="white-box">
                        
                        
                            <form class="form-horizontal form-material"  action="./editProfile" method="post">
                                <div class="form-group">
                                    <label for="example-email" class="col-md-12">Email</label>
                                    <div class="col-md-12">
                                        <input type="email" name ="email" placeholder="${user.email}" class="form-control form-control-line" name="example-email" id="example-email" required= "required" autocomplete = "off"> </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-12">Password</label>
                                    <div class="col-md-12">
                                        <input type="password" placeholder="******" name ="oldPass" value="password" class="form-control form-control-line" required= "required" autocomplete = "off"> </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-12">Confirm password</label>
                                    <div class="col-md-12">
                                        <input type="password" name = "oldConfPass" placeholder="******" class="form-control form-control-line" required= "required" autocomplete = "off"> </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-12">New name</label>
                                    <div class="col-md-12">
                                        <input type="text" name ="newName" placeholder="new full name" class="form-control form-control-line"> </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-12">New password</label>
                                    <div class="col-md-12">
                                        <input type="password" name ="newPass" placeholder="new password" class="form-control form-control-line"> </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <button class="btn btn-success">Update Profile</button>
                                    </div>
                                </div>
                                
                            </form>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
            </div>
<%-- <div class="container">
    <h1>Edit Profile</h1>
  	<hr>
	<div class="row">
      <div class="col-md-3">
        <div class="text-center">
			<img src="getPicSession?user=${user.email} " height="100" width="100" class="avatar img-circle" alt="avatar">
          <h6>Change your photo here</h6>
          <form action="./changePic" method="post" enctype="multipart/form-data">
          <input type="file" accept="image/*" name="fileForChange"><br>
		  <input type="submit" value="Save changes">
		  </form>
        </div>
      </div>
      
      <!-- edit form column -->
      <div class="col-md-9 personal-info">
        <div class="alert alert-info alert-dismissable"> 
          <i class="fa fa-coffee"></i>
        </div>
        <h3>Personal info</h3>
        
        <form class="form-horizontal" role="form" action="./editProfile" method="post">
           <div class="form-group">
            <label class="col-lg-3 control-label">Email:</label>
            <div class="col-lg-8">
              <input class="form-control" type="email" placeholder="Put your email here.." name="email" required>
            </div>
          </div>
           <div class="form-group">
            <label class="col-md-3 control-label">Password:</label>
            <div class="col-md-8">
              <input class="form-control" type="password" placeholder="put password.." name="oldPass" required>
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Confirm password:</label required>
            <div class="col-md-8">
              <input class="form-control" type="password" placeholder="confirm your password.." name="oldConfPass">
            </div>
          </div>
          <div class="form-group">
            <label class="col-lg-3 control-label">New name:</label>
            <div class="col-lg-8">
              <input class="form-control" type="text" placeholder="new name here.." name="newName">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">New password:</label>
            <div class="col-md-8">
              <input class="form-control" type="password" placeholder="new password.." name="newPass">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label"></label>
            <div class="col-md-8">
              <input type="submit" value="Save Changes">
              <span></span>
              <input type="reset" class="btn btn-default" value="Cancel">
            </div>
          </div>
        </form>
      </div>
  </div>
</div>
<hr> --%>
</body>
</html>