function showMenu() {
	document.getElementById("myMenuNav").style.width = "100%";
}

function hideMenu() {
	document.getElementById("myMenuNav").style.width = "0%";
}

$(document).keyup(function(e) {
    if (e.keyCode == 27) {
    	hideMenu();
    }
});