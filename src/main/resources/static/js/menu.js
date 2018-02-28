$(document).keyup(function(e) {
    if (e.keyCode == 27) {
    	hideMenu();
    }
});

function showMenu() {
	document.getElementById("myMenuNav").style.width = "100%";
}

function hideMenu() {
	document.getElementById("myMenuNav").style.width = "0%";
}

/**
  * ====== Scroll up button ======
  */

$(document).ready(function() {
	$(window).scroll(function() {
		if($(this).scrollTop() > 100) {
			$(".sf-scroll-to-top").fadeIn();
		} else {
			$(".sf-scroll-to-top").fadeOut();
		}
	})
	
	$('.sf-scroll-to-top').click(function(){
		$('html, body').animate({scrollTop : 0},300);
		return false;
	});
});

/**
  * ====== Bottom message ======
  */
function showBottomMessage(message) {
	document.querySelector(".sf-bottom-message").innerHTML = message;
	$(".sf-bottom-message").animate({
		bottom: 0
	}, 500, function() {
		setTimeout(hideBottomMessage, 4000);
	});
}

function hideBottomMessage() {
	$(".sf-bottom-message").animate({
		bottom: -50
	}, 500);
}