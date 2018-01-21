function resetPassword() {
    var password = document.getElementById("password-input").value;
    var confirmPassword = document.getElementById("password-confirm-input").value;

    var loaderContainer = document.getElementById("loader-container");
	
	var successMessageDiv = document.getElementById("success-message");
	var errorMessageDiv = document.getElementById("error-message");

    if(password != confirmPassword || password == '' || confirmPassword == ''){
        errorMessageDiv.style.display = "inline-block";
        errorMessageDiv.innerHTML = "Provided passwords are not equal."
        return;
    } else if(password.length < 4 || confirmPassword.length < 4) {
        errorMessageDiv.style.display = "inline-block";
        errorMessageDiv.innerHTML = "Password is to short. Must be more or equal than 5 character.";
        return;
    } else {
        errorMessageDiv.style.display = "none";
    }
    
    var code = $('#code').val();
    var email = $('#email').val();

    $.ajax({
        url: 'http://localhost:8080/user/resetPassword?code=' + code + '&email=' + email + "&newPassword=" + password,
        type: "GET",
        dataType: "text",
        success: function(data) {
        	var container = document.getElementById("container");
        	container.style.textAlign = "center";
        	container.innerHTML = "Password succesfully changed.";
        	
        	successMessageDiv.innerHTML = "Password succesfully changed.";
			successMessageDiv.style.display = "inline-block";
			errorMessageDiv.style.display = "none";
			loaderContainer.style.display = "none";
        },
        error: function(error) {
            errorMessageDiv.innerHTML = "Something went wrong. " + JSON.parse(error.responseText).message;
			errorMessageDiv.style.display = "inline-block";
			successMessageDiv.style.display = "none";
			loaderContainer.style.display = "none";
        }
    });
}

$(document).ready(function() {
	
    $('#password-input').on('input', function() {
        var password = $(this).val();
        if(password.length > 4){
            $(this).removeClass("invalid").addClass("valid");
        } else {
            $(this).removeClass("valid").addClass("invalid");
        }
    });

    $('#password-confirm-input').on('input', function() {
        var passwordConfirm = $(this).val();
        if(passwordConfirm.length > 4){
            $(this).removeClass("invalid").addClass("valid");
        } else {
            $(this).removeClass("valid").addClass("invalid");
        }
    });
});