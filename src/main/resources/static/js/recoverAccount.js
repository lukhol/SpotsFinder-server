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
	
	$.ajax({
		url: 'http://localhost:8080/user/recover?emailAddress=' + email,
		type: "GET",
		dataType: "text",
		success: function(data) {
			successMessageDiv.innerHTML = "Message with instruction has been send to " + email;
			successMessageDiv.style.display = "inline-block";
			errorMessageDiv.style.display = "none";
			loaderContainer.style.display = "none";
		}, 
		error: function(error) {
			errorMessageDiv.innerHTML = "Not found email " + email;
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

$(document).ready(function() {
	$('#email-input').on('input', function() {
		var input = $(this);
		var isEmail = validateEmail(input.val());
		if(isEmail){
			input.removeClass("invalid").addClass("valid");
		} else {
			input.removeClass("valid").addClass("invalid");
		}
	});
});