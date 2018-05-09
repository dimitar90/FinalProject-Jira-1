<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	
    <html>
 <head>
 <c:if test= '${not empty sessionScope.user}'>
			<jsp:include page="nav-bar-main.jsp"></jsp:include>
	</c:if>
	<c:if test= '${empty sessionScope.user}'>
			<jsp:include page="navigation-bar.jsp"></jsp:include>
</c:if>
<%--	<link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css" />">	  --%> 
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
                        <h4 class="page-title">${user.name}</h4> 
                        </div>
                    <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                       <!--  <a href="https://wrappixel.com/templates/ampleadmin/" target="_blank" class="btn btn-danger pull-right m-l-20 hidden-xs hidden-sm waves-effect waves-light">Upgrade to Pro</a> -->
                        <ol class="breadcrumb">
                          <!--   <li><a href="#">Dashboard</a></li> -->
                            <li class="active">Jira profile</li>
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
                                    <c:if test="${ requestScope.errorMsg != null}">
         								 <div class="form-group">
          								  <div class="form-check">
         								     <div class="alert alert-danger" role="alert">
											${errorMsg} <strong>Try again!</strong>
												</div>
											</c:if>
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
</body>
</html>
