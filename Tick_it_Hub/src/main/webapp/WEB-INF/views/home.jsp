<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false"%>
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

</head>
<body>
	<div id="weHours" class="container-fluid jumbotron">
		<div>
			Tick-it Hub
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3" id="loginBox">
					<sf:form class="form-signin" method="POST" action="dashboard.htm" commandName="user">
						<h3 class="form-signin-heading">Please sign in</h3>
						<div class="form-group">
							<label for="inputUName" class="sr-only">User Name</label> 
							<sf:input
								type="text" id="userName" name="user" path="uname" class="form-control"
								placeholder="Username" required="required" autofocus="autofocus" />
						</div>
						<!-- /form-group -->
						<div class="form-group">
							<label for="inputPassword" class="sr-only">Password</label> 
							<sf:input
								type="password" id="inputPassword" name="password" path="password"
								class="form-control" placeholder="Password" required="required" />
						</div>
						<!-- /form-group -->
						<p id="incorrect_uname_pswd"></p>
						<button id="signin" class="btn btn-lg btn-warning btn-block"
							type="submit" >Sign in</button>
							<a href="signup.htm">New User?</a>
					</sf:form>
				</div>
				<!-- /col-md-6 col-md-offset-3 -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
</body>
</html>