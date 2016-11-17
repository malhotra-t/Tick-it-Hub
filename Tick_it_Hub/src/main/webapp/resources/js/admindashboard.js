$(document).ready(function() {
		
	$(".accept-button").click(function() {

		// get request id from table row's 1st <td>
		var reqIdNum = $(this).closest("tr").find("td").first().html();
		var acceptBtn = $(this);

		// invoke server through ajax
		$.ajax({
			method : "POST",
			url : "acceptUser.htm",
			data : {
				reqId : reqIdNum
			}
		}).done(function(msg) {
			// if success then change status to Accepted and disable buttons
			if(msg.indexOf("success")>=0){
				var reqStat = $(acceptBtn).closest("tr").find("td")[6];
				$(reqStat).html("Accepted");
				$(acceptBtn).prop("disabled",true);
				var rejBtn = $(acceptBtn).closest("tr").find("button")[1];
				$(rejBtn).prop("disabled",true);
			}else{
				alert(msg);  // if failure then show error msg
			}			
		});

	});
	
	$(".reject-button").click(function(){
		// get request id from table row's 1st <td>
		var reqIdNum = $(this).closest("tr").find("td").first().html();
		var rejectBtn = $(this);
		$.ajax({
			method : "POST",
			url : "rejectUser.htm",
			data : {reqId : reqIdNum}
		}).done(function(msg){
			// if success then change status to Rejected and disable buttons
			if(msg.indexOf("success")>=0){
				var reqStat = $(rejectBtn).closest("tr").find("td")[6];
				$(reqStat).html("Rejected");
				$(rejectBtn).prop("disabled",true);
				var accBtn = $(rejectBtn).closest("tr").find("button")[0];
				$(accBtn).prop("disabled",true);
			}else{
				alert(msg);  // if failure then show error msg
			}
		})
	})
});
