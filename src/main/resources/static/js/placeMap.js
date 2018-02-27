var latitude = 0;
var longitude = 0;

function sliderFunction() {
	var slider = document.getElementById("distance-slider");
	slider.oninput = function() {
		let sliderValueDiv = document.getElementById("slider-value");
		sliderValueDiv.innerHTML = this.value;
	};
}

function geolocation() {
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showPosition);
	}
}

function showPosition(position) {
	latitude = position.coords.latitude;
	longitude = position.coords.longitude;
	myGoogleMapsCallbackFun();
	getSpotsFromServer();
}

function myGoogleMapsCallbackFun() {
	let mapOptions = {
		center: new google.maps.LatLng(latitude, longitude),
		zoom: 15
	};
	
	let map = new google.maps.Map(document.getElementById("mapWithPlaces"), mapOptions);
}

function getSpotsFromServer() {	
	if(latitude == 0 && longitude == 0) {
		alert("Location problem.");
		return;
	}
	
	let baseUrl = $("#base-url").val();
	let queryUrl = baseUrl + "/places/searches";
	
	let criteria = {
		"bank": false,
		"bowl": false,
		"corners": false,
		"curb": false,
		"distance": 10,
		"downhill": false,
		"gap": false,
		"handrail": false,
		"ledge": false,
		"location": {
			"city": null,
			"latitude": latitude,
			"longitude": longitude
		},
		"manualpad": false,
		"openYourMind": false,
		"pyramid": false,
		"rail": false,
		"stairs": false,
		"type": [
			0,1,2
		],
		"wallride": false
	};
		
	$.ajax({
		url: queryUrl,
		type: 'POST',
		data: JSON.stringify(criteria),
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		headers: {
		    'Authorization': 'Basic c3BvdGZpbmRlcjpzcG90ZmluZGVyU2VjcmV0'
		}, 
		success: function(data) {
			locatePlacesOnMap(data);
		},
		error: function(error) {
			alert("error");
		}
	});
}

function locatePlacesOnMap(placesArray) {
	let mapOptions = {
		center: new google.maps.LatLng(latitude, longitude),
		zoom: 15
	};
	let map = new google.maps.Map(document.getElementById("mapWithPlaces"), mapOptions);
	
	for(let i = 0 ; i < placesArray.length ; i++) {
		let placePosition =  new google.maps.LatLng(placesArray[i].location.latitude, placesArray[i].location.longitude);
		let placeMarker = new google.maps.Marker({position: placePosition});
		placeMarker.setMap(map);
		
		let placeHtmlContent = '<p style="color: black;">' + placesArray[i].name + '</p>' + '<img src="data:image/png;base64,' + placesArray[i].mainPhoto + '" style="width: 200px; height: 200px;"/>';
		placeHtmlContent += '<a href="#" style="display: block;">More details</a>'
		
		google.maps.event.addListener(placeMarker, 'click', function() {
			var infoWindow = new google.maps.InfoWindow({
				content: placeHtmlContent
			});
			infoWindow.open(map, placeMarker);
		});
	}
}

$(document).ready(function() {
	sliderFunction();
	geolocation();
});