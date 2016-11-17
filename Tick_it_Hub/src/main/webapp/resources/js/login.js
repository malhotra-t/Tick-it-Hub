$(document).ready(function(){

	$("#signin").click(function(){

		username="" + $("#userName").val();

		password="" + $("#inputPassword").val();

		if(username.length==0){

			$("#incorrect_uname_pswd").html("User name cannot be blank!");

		} else if(password.length==0){

			$("#incorrect_uname_pswd").html("Password cannot be blank!");

		} else{

			$.ajax({

				type: "POST",

				url: "login.jsp",

				data: "user="+username+"&password="+password,

				success: function(result){

					if(result.indexOf("true") > -1) {

						window.location="homepage.jsp";

					}

					else {

						$("#incorrect_uname_pswd").html("Incorrect username or password!");

					}

				}

			});

		}

		return false;

	});
});