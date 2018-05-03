<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="navigation-bar.jsp"></jsp:include>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
    <%-- <%@ page import="com.jira.model.pojo.User" %>  --%>
 <head>
 <link rel="stylesheet" href="<c:url value= "/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">	  
	 </head>
<body>
<div class="container">
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
<hr>
</body>
</html>