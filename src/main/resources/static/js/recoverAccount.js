function resetPasswordEmail(){
	var email = document.getElementById("email-input").value;
	
	if(!validateEmail(email)) {
		return;
	}

	var loaderContainer = document.getElementById("loader-container");
	
	var successMessageDiv = document.getElementById("success-message");
	var errorMessageDiv = document.getElementById("error-message");
	
	successMessageDiv.style.display = "none";
	errorMessageDiv.style.display = "none";
	loaderContainer.style.display = "inline-block";
	
	var baseUrl = $("#base-url").val();
	var queryUrl = baseUrl + '/user/recover?emailAddress=' + email;
		
	$.ajax({
		url: queryUrl,
		type: "GET",
		dataType: "text",
		success: function(data) {
			successMessageDiv.innerHTML = $('#label-message-emailSend').val() + email;
			successMessageDiv.style.display = "inline-block";
			errorMessageDiv.style.display = "none";
			loaderContainer.style.display = "none";
		}, 
		error: function(error) {
			errorMessageDiv.innerHTML = $('#label-message-emailNotFound').val() + email;
			errorMessageDiv.style.display = "inline-block";
			successMessageDiv.style.display = "none";
			loaderContainer.style.display = "none";
		}
	});
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email.toLowerCase());
}

function showMenu() {
	document.getElementById("myMenuNav").style.width = "100%";
}

function hideMenu() {
	document.getElementById("myMenuNav").style.width = "0%";
}

$(document).ready(function() {
//	//With jQuery:
//	$('#email-input').on('input', function() {
//		var input = $(this);
//		var isEmail = validateEmail(input.val());
//		if(isEmail){
//			input.removeClass("invalid").addClass("valid");
//		} else {
//			input.removeClass("valid").addClass("invalid");
//		}
//	});
	
	var emailInput = document.getElementById('email-input');
	emailInput.addEventListener('input', function() {
		var input = $(this);
		var isEmail = validateEmail(input.val());
		if(isEmail){
			input.removeClass("invalid").addClass("valid");
		} else {
			input.removeClass("valid").addClass("invalid");
		}
	});
	
});