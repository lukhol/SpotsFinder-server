function resetPassword() {
    var password = document.getElementById("password-input").value;
    var confirmPassword = document.getElementById("password-confirm-input").value;

    var loaderContainer = document.getElementById("loader-container");
	
	var successMessageDiv = document.getElementById("success-message");
	var errorMessageDiv = document.getElementById("error-message");

    if(password != confirmPassword || password == '' || confirmPassword == ''){
        errorMessageDiv.style.display = "inline-block";
        errorMessageDiv.innerHTML = $('#label-message-notEqualPasswords').val();
        return;
    } else if(password.length < 5 || confirmPassword.length < 5) {
        errorMessageDiv.style.display = "inline-block";
        errorMessageDiv.innerHTML = $('#label-message-wrongPassword').val();
        return;
    } else {
        errorMessageDiv.style.display = "none";
    }
    
    var code = $('#code').val();
    var email = $('#email').val();
    var baseUrl = $("#base-url").val();

    $.ajax({
        url: baseUrl + '/user/resetPassword?code=' + code + '&email=' + email + '&newPassword=' + password,
        type: "GET",
        dataType: "text",
        success: function(data) {
        	var container = document.getElementById("container");
        	container.style.textAlign = "center";
        	container.innerHTML = $('#label-message-passwordChanged').val();
        	
        	successMessageDiv.innerHTML = $('#label-message-passwordChanged').val();
			successMessageDiv.style.display = "inline-block";
			errorMessageDiv.style.display = "none";
			loaderContainer.style.display = "none";
        },
        error: function(error) {
            errorMessageDiv.innerHTML = $('#label-message-somethingWrong').val() + JSON.parse(error.responseText).message;
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