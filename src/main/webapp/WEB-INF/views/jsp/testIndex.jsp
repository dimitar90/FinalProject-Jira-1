<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<jsp:include page="nav-bar-main.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IT Talents</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
</head>
<body>
<form class="form-horizontal">
<fieldset>

<!-- Form Name -->
<legend>UPDATING VILLAGE BUDGET</legend>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="pname">PROJECT NAME</label>  
  <div class="col-md-4">
  <input id="pname" name="pname" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Select Basic -->
<div class="form-group">
  <label class="col-md-4 control-label" for="PTYPE">PROJECT TYPE</label>
  <div class="col-md-4">
    <select id="PTYPE" name="PTYPE" class="form-control">
      <option value="1">ROADS AND BUILDINGS</option>
      <option value="2">ELECTRICITY</option>
      <option value="3">MEDICAL</option>
      <option value="4">SAFETY AND CLEANING</option>
    </select>
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="DVFCZ">ORGANIZER/CONTRACTOR</label>  
  <div class="col-md-4">
  <input id="DVFCZ" name="DVFCZ" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="KKKO">BUDJET REQUIRED</label>  
  <div class="col-md-4">
  <input id="KKKO" name="KKKO" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="CCC">AMOUNT SANCTIONED</label>  
  <div class="col-md-4">
  <input id="CCC" name="CCC" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="YIHK">AMOUNT SPEND</label>  
  <div class="col-md-4">
  <input id="YIHK" name="YIHK" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="BJKSS">BALANCE AMOUNT</label>  
  <div class="col-md-4">
  <input id="BJKSS" name="BJKSS" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Button (Double) -->
<div class="form-group">
  <label class="col-md-4 control-label" for="button1idFFF"></label>
  <div class="col-md-8">
    <button id="button1idFFF" name="button1idFFF" class="btn btn-success">UPDATE</button>
    <button id="button2id" name="button2id" class="btn btn-danger">CANCEL</button>
  </div>
</div>

</fieldset>
</form>

</body>
</html>