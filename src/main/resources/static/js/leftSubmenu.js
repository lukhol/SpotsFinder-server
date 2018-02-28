$(document).ready(function () {
	let leftSubmenu = document.querySelector(".sf-submenu-left");
	let leftSubmenuOpenCloseButton = document.querySelector(".sf-submenu-left-close");
	
//	$(leftSubmenuOpenCloseButton).mouseenter(function() {	
//		$(leftSubmenu).animate({
//			left: "0px"
//		}, 400);
//	});
//	
//	$(leftSubmenu).mouseout(function() {
//		$(leftSubmenu).animate({
//			left: "-300px"
//		}, 400);
//	});
	
//	$(leftSubmenu).hover(
//	function() {
//		if($(leftSubmenu).position().left < "-150"){
//			$(leftSubmenu).animate({
//				left: "0px"
//			}, 200);
//		}
//	}, 
//	function() {
//		if($(leftSubmenu).position().left > "-150"){
//			$(leftSubmenu).animate({
//				left: "-300px"
//			}, 200);
//		}
//	});
});

function showLeftSubmenu() {
	let leftSubmenu = document.querySelector(".sf-submenu-left");
	let leftSubmenuOpenCloseButton = document.querySelector(".sf-submenu-left-close");
	let submenuWidth = leftSubmenu.style.width;
	
	if(submenuWidth == "0px"){
		leftSubmenu.style.width = "-300px";
		leftSubmenuOpenCloseButton.innerHTML = ">";
	}
	else {
		leftSubmenu.style.width = "0px";
		leftSubmenuOpenCloseButton.innerHTML = "<";
	}
}