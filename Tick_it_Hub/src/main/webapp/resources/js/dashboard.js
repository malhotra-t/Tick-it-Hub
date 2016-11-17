$(document).ready(function() {
	
	var irForm = $("#ir-modal-form").find("form");
	
	var incidentModal = $("#ir-modal-form").dialog({
		autoOpen : false,
		height : 400,
		width : 350,
		modal : true,
		buttons : {
			"Create Incident" : function(){
				//save incident, load dashboard and close modal.				
				$.ajax({
				            type : "POST",
				            url : "createIr.htm",
				            data : $(irForm).serialize(),
				            
				            success : function(data) {
				                console.log("SUCCESS: ", data);
				                if(data.indexOf("success")>=0){
				                	incidentModal.dialog("close");
				                	window.location = "dashboard.htm";
				                }else{
				                	$("#irErr").html(data);
				                }
				            },
				            error : function(e) {
				                console.log("ERROR: ", e);
				                $("#irErr").html(e);
				            }
				        });				
			},
			Cancel : function(){
				incidentModal.dialog("close");
			}
		}
	});
	
	var prForm = $("#pr-modal-form").find("form");
	
	var problemModal = $("#pr-modal-form").dialog({
		autoOpen : false,
		height : 400,
		width : 350,
		modal : true,
		buttons : {
			"Create Problem" : function(){
				//save pr, load dashboard and close modal.
				$.ajax({
					type: "POST",
					url: "createPr.htm",
					data: $(prForm).serialize(),
					success: function(prData){
						console.log("SUCCESS: ", prData);
						if(prData.indexOf("success")>=0){
							problemModal.dialog("close");
							window.location = "prdashboard.htm";
						}else{
							$("#prErr").html(prData);
						}
					}
				});
			},
			Cancel : function(){
				problemModal.dialog("close");
			}
		}
	});
	
	$("#newIrDrop").click(function(){
		$("#irErr").html("");
		incidentModal.dialog("open");
		return false;
	});
	
	$("#newPrDrop").click(function(){
		$("#prErr").html("");
		problemModal.dialog("open");
		return false;
	});
	
	// Assign IR to another team
	var selectedIr;
	var asgnForm = $("#asgn-modal-form").find("form");
	var asgTeamModal = $("#asgn-modal-form").dialog({
		autoOpen : false,
		height : 300,
		width : 300,
		modal : true,
		buttons : {
			"Assign" : function(){
				//detach from existing pr
				//attach to default pr of selected team
				//load dashboard and close modal.
				
				
				var selectedTeam = asgnForm.find("select").val();
								
				$.ajax({
					type: "POST",
					url: "asgnIrToTeam.htm",
					data: {irId:selectedIr,asgnToTeamId:selectedTeam},
					success: function(asgndIrData){
						console.log("SUCCESS: ", asgndIrData);
						if(asgndIrData.indexOf("success")>=0){
							asgTeamModal.dialog("close");
							window.location = "dashboard.htm";
						}else{
							$("#asgnErr").html(asgndIrData);
						}
					}
				});
			},
			Cancel : function(){
				asgTeamModal.dialog("close");
			}
		}
	});	
	
	$("#asgnToTeam").click(function(){
		selectedIr =  $( "input:checked" ).val();
		$.ajax({
			url: "getFuncTeams.htm"
			}).done(function(teamStr){
				var funcTeamArr = teamStr.split(",");
				$.each(funcTeamArr, function(index,indTeam){
					$("#allTeams").append("<option>" +indTeam+ "</option>");
				});				
				$(asgTeamModal).dialog("open");
				
			});
	})
	
	
	//close incident
	
	var selIrVal;
	var closeIrModal = $("#close-ir-modal").dialog({
		autoOpen : false,
		height : 200,
		width : 300,
		modal : true,
		buttons : {
			Yes : function(){
				//reset incident, load dashboard and close modal.				
				$.ajax({
				            type : "POST",
				            url : "closeIr.htm",
				            data : {selIr:selIrVal},
				            
				            success : function(data) {
				                console.log("SUCCESS: ", data);
				                if(data.indexOf("success")>=0){
				                	closeIrModal.dialog("close");
				                	window.location = "dashboard.htm";
				                }else{
				                	$("#closeErr").html(data);
				                }
				            },
				            error : function(e) {
				                console.log("ERROR: ", e);
				                $("#closeErr").html(e);
				            }
				        });				
			},
			No : function(){
				closeIrModal.dialog("close");
			}
		}
	});
	
	$("#closeIr").click(function(){
		$("#closeErr").html("");
		selIrVal = $("input:checked").val();
		closeIrModal.dialog("open");
		return false;
	})
	
	//close pr
	var selPrVal;
	var closePrModal = $("#close-pr-modal").dialog({
		autoOpen : false,
		height : 200,
		width : 300,
		modal : true,
		buttons : {
			Yes : function(){
				//reset incident, load dashboard and close modal.				
				$.ajax({
				            type : "POST",
				            url : "closePr.htm",
				            data : {selPr:selPrVal},
				            
				            success : function(data) {
				                console.log("SUCCESS: ", data);
				                if(data.indexOf("success")>=0){
				                	closePrModal.dialog("close");
				                	window.location = "prdashboard.htm";
				                }else{
				                	$("#closePrErr").html(data);
				                }
				            },
				            error : function(e) {
				                console.log("ERROR: ", e);
				                $("#closePrErr").html(e);
				            }
				        });				
			},
			No : function(){
				closePrModal.dialog("close");
			}
		}
	});
	
	$("#closePr").click(function(){
		$("#closePrErr").html("");
		selPrVal = $("input:checked").val();
		closePrModal.dialog("open");
		return false;
	})

	//assign ir to another pr within same team
	var selectedIrToAsgn;
	$("#tagToPr").click(function(){
		selectedIrToAsgn = $("input:checked").val();
		$.ajax({
			method: "POST",
			url: "assignIr.htm",
			data: {selectedIrToAsgnVar:selectedIrToAsgn,prTagVar:$("#prTag").val()}
		}).done(function(reData){
			if(reData.indexOf("success")>=0){
				window.location = "dashboard.htm";
			}else{
				alert("reData");
			}
		});
	});
	
});
