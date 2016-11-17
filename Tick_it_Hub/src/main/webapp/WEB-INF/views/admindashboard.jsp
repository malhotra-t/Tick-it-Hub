<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang = "en">
<head>
<title>Tick-it Hub</title> <meta charset="utf-8">
<meta name="viewport" content="width=device- width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="resources/css/dashboard.css">
<script src="resources/js/admindashboard.js"></script> </head>
<body>
<nav class="navbar navbar-default" id="tickitdashboard">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand whitefont" href="#">Tick-it Hub</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a class="whitefont" href="#">Hi ${loggedUser.uname}</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a class="whitefont" href="logout.htm">Logout</a></li>
        
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="container"> 
<div class="row">
	<ul class="nav nav-tabs nav-justified">
	  <li role="presentation" class="active"><a href="#">Requests</a></li>
	  <li></li>
		</ul>
	
	<div class="panel panel-default">
  <!-- Default panel contents -->
	  
	  <!-- Table -->
	  <table class="table table-striped table-bordered">
		<thead>
			<td>RequestId</td>
			<td>FirstName</td>
			<td>LastName</td>
			<td>Role</td>
			<td>UserEmail</td>
			<td>ManagerEmail</td>
			<td>Status</td>
			<td>RaisedOn</td>
			<td>Accept/Reject</td>
		</thead>
		<tbody>
			<c:forEach var="reqItem" items="${requestScope.signUps}">
				<tr>
				<td>${reqItem.reqId}</td>
				<td>${reqItem.fname}</td>
				<td>${reqItem.lname}</td>
				<td>${reqItem.role}</td>
				<td>${reqItem.userEmail}</td>
				<td>${reqItem.managerEmail}</td>
				<td>${reqItem.signUpStatus}</td>
				<td>${reqItem.raisedOn}</td>
				<c:choose>
					<c:when test = "${reqItem.signUpStatus=='Pending Acceptance'}">
						<td><button type="button" class="btn btn-primary accept-button" >Accept</button></td>
						<td><button type="button" class="btn btn-primary reject-button" >Reject</button></td>
					</c:when>
					<c:otherwise>
						<td><button type="button" class="btn btn-primary accept-button disabled" >Accept</button></td>
						<td><button type="button" class="btn btn-primary reject-button disabled" >Reject</button></td>
					</c:otherwise>				
				</c:choose>
				
			</tr>
			</c:forEach>
		</tbody>
	  </table>
	</div>
</div> <!-- /row -->
</div> <!-- /container --> </body>
</html>