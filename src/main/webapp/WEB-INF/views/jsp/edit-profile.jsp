<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.jira.model.pojo.User" %> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <jsp:include page="navigation-bar.jsp"></jsp:include>

<body>
<div class="container">
    <h1>Edit Profile</h1>
  	<hr>
	<div class="row">
      <!-- left column -->
      <div class="col-md-3">
        <div class="text-center">
			<img src="getPic?user=${user.getEmail()} " height="100" width="100" class="avatar img-circle" alt="avatar">
          <h6>Change your photo here</h6>
          <form action="./changePic" method="post" enctype="multipart/form-data">
          <input type="file" accept="image/*" name="file change"><br>
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
              <input class="form-control" type="password" placeholder="put password.." name="old pass" required>
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">Confirm password:</label required>
            <div class="col-md-8">
              <input class="form-control" type="password" placeholder="confirm your password.." name="old conf pass">
            </div>
          </div>
          <div class="form-group">
            <label class="col-lg-3 control-label">New name:</label>
            <div class="col-lg-8">
              <input class="form-control" type="text" placeholder="new name here.." name="new name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 control-label">New password:</label>
            <div class="col-md-8">
              <input class="form-control" type="password" placeholder="new password.." name="new pass">
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