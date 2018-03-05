$(document).ready(function() {
	$(".my-modal-test").click(function() {
		let ahrefAttribute = $(this).attr("href");
		
		loadHandlebarsFromFile("placeDetailsModal.hbs", "changePasswordContainer", {hrefAttribute: ahrefAttribute}, function() {
			$.get(ahrefAttribute, function(data) {
				let myModal = $('#myModalTest');
				
				let startBodyIndex = data.indexOf('startpointer');
				let endBodyIndex = data.indexOf('endpointer')
				let bodyOnly = data.substring(startBodyIndex + 16, endBodyIndex);
				
				document.querySelector('#myModalTest .modal-content .modal-body').innerHTML = bodyOnly;
				
				myModal.modal();
			});
		});
		
		return false;
	});
});