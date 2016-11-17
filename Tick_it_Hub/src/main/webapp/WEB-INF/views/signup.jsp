<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<!doctype html>
<html lang="en">
<head>
<title>Tick-it Hub</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device- width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="resources/css/login.css">
<script src="resources/js/login.js"></script>
</head>
<body>
	<div id="weHours" class="container-fluid jumbotron">
		<div>
			Sign Up
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3" id="loginBox">
					<sf:form class="form-signin" method='POST' action='signup.htm' commandName="signUpRequest">
					
						<div class="form-group">
							<label for="inputFName">First Name</label> 
							<sf:input
								type="text" id="firstName" name="fname" class="form-control"
								path="fname" placeholder="First name" required="required" autofocus="autofocus" />
							<label for="inputLName">Last Name</label> 
							<sf:input
								type="text" id="lastName" name="lname" class="form-control"
								path="lname" placeholder="Last name" required="required" autofocus="autofocus" />
							<label for="userRole">Role</label> 
							
							<sf:select id="userRole" class="form-control" path="role"
							placeholder="Role" required="required" autofocus="autofocus">
								<option>Manager</option>
								<option>Member</option>
							</sf:select>
							<label for="inputEmail">Email ID</label> 
							<sf:input
								type="text" id="email" name="mailid" class="form-control"
								path="userEmail" placeholder="Email ID" required="required" autofocus="autofocus" />
							<label for="inputMngName">Manager EmailID</label> 
							<sf:input
								type="text" id="managerEmail" name="memail" class="form-control"
								path="managerEmail" placeholder="Manager email" required="required" autofocus="autofocus" />
							<label for="inputUName">User Name</label> 
							<sf:input
								type="text" id="userName" name="user" class="form-control"
								path="uname" placeholder="Username" required="required" autofocus="autofocus" />
							<label for="inputPassword">Password</label> 
							<sf:input
								type="password" id="inputPassword" name="password"
								class="form-control" path="password" placeholder="Password" required="required" autofocus="autofocus" />
						</div>
						<!-- /form-group -->
						<p id="incorrect_uname_pswd"></p>
						<button id="signup" class="btn btn-lg btn-primary btn-block"
							type="submit" >Register</button>
					</sf:form>
				</div>
				<!-- /col-md-6 col-md-offset-3 -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
</body>
</html>