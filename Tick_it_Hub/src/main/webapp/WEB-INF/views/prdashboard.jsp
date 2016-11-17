<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang = "en">
<head>
<title>Tick-it Hub</title> <meta charset="utf-8">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="resources/css/dashboard.css">
<script src="resources/js/dashboard.js"></script> </head>
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
        <li class="dropdown">
          <a href="#" class="dropdown-toggle whitefont" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Create <span class="caret"></span></a>
          <ul class="dropdown-menu">
          <c:choose>
          	<c:when test="${loggedUser.role.equalsIgnoreCase('manager')}">
          		<li><a id="newPrDrop" href="#">New Problem</a></li>            	
          	</c:when>          	
          </c:choose>
          <li><a id="newIrDrop" href="#">New Incident</a></li>                        
          </ul>
        </li>
        <li><a class="whitefont" href="#">Team ${loggedUser.team.teamName}</a></li>
		<li><a class="whitefont" href="logout.htm">Logout</a></li>
        
      </ul>
    </div><!-- /.navbar-collapse -->
    <div id="ir-modal-form" title="Create new Incident">
    	<form>
    		<fieldset>						
				<label for="irPriority">Priority</label> 
				<select id="irPriority" class="form-control" name="irPriority">
					<option>P1</option>
					<option>P2</option>
					<option>P3</option>
				</select><br/>
				
				<label for="irStatus">Status</label> 
				<input type="text" id="irStatus" name="irStatus" class="form-control" required/>
					
				<label for="irDesc">Description</label> 
				<textarea rows='3' cols='12' id="irDesc" name="irDesc" class="form-control" required></textarea>
				
				<p id="irErr"></p>
			</fieldset>
		</form>
	</div>
	<div id="pr-modal-form" title="Create new Problem">
    	<form>
    		<fieldset>						
				<label for="prPriority">Priority</label> 
				<select id="prPriority" class="form-control" name="prPriority">
					<option>P1</option>
					<option>P2</option>
					<option>P3</option>
				</select><br/>
				
				<label for="prStatus">Status</label> 
				<input type="text" id="prStatus" name="prStatus" class="form-control" required/>
					
				<label for="prDesc">Description</label> 
				<textarea rows='3' cols='12' id="prDesc" name="prDesc" class="form-control" required></textarea>
				
				<p id="prErr"></p>

			</fieldset>
		</form>
	</div>
    
  </div><!-- /.container-fluid -->
</nav>

<div class="container"> 
<div class="row">
	<ul class="nav nav-tabs nav-justified">
	  <li role="presentation"><a href="dashboard.htm">Incidents</a></li>
	  <li role="presentation" class="active"><a href="#">Problems</a></li>
		</ul>
	
	<div class="panel panel-default">
  <!-- Default panel contents -->
	  

	  <!-- Table -->
	  <table class="table table-striped table-bordered">
		<thead>
			<tr>
			<td>Select</td>
			<td>PR</td>
			<td>Priority</td>
			<td>Status</td>
			<td>Description</td>
			<td>LogDate</td>
			<td>SLADate</td>
			<td>LoggedBy</td>
			<td>ResolvedBy</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="pr" items="${sessionScope.probList}">
			<tr>
			<c:choose>
				<c:when test="${pr.prStatus.equalsIgnoreCase('Closed')}">
					<td><input type="radio" name="selectPr" value="${pr.prNum}" disabled></td>
				</c:when>
				<c:otherwise>
					<td><input type="radio" name="selectPr" value="${pr.prNum}"></td>
				</c:otherwise>
			</c:choose>
				<td>${pr.prNum}</td>
				<td>${pr.prPriority}</td>
				<td>${pr.prStatus}</td>
				<td>${pr.prDesc}</td>
				<td>${pr.prLogDate}</td>
				<td>${pr.prSlaDate}</td>
				<td>${pr.prLoggedBy.uname}</td>
				<td>${pr.prResolvedBy.uname}</td>
			</tr>
		</c:forEach>
		</tbody>
	  </table>
	</div> <!-- /panel -->
</div> <!-- /row -->
<div class="row">
	<button id="closePr" type="button" class="btn btn-danger accept-button pull-right" >Close PR</button>
</div> <!-- /row -->
</div> <!-- /container -->

<div id="close-pr-modal">
		<p>Are you sure you want to close this incident?</p>
		<p id="closePrErr"></p>
	</div>
 
</body>
</html>