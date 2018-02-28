var latitude = 0;
var longitude = 0;

$(document).ready(function() {
	sliderFunction();
	geolocation();
});

function sliderFunction() {
	var slider = document.getElementById("distanceRange");
	slider.oninput = function() {
		let sliderValueDiv = document.getElementById("slider-value");
		sliderValueDiv.innerHTML = "Distance: " + this.value + "km";
	};
}

function searchPlaces() {
	let distanceRange = document.getElementById("distanceRange");
	let latitudeInput = document.getElementById("latitudeInput");
	let longitudeInput = document.getElementById("longitudeInput");
	
	//alert(latitudeInput.value + longitudeInput.value + distanceRange.value);
	getSpotsFromServer(latitudeInput.value, longitudeInput.value, distanceRange.value);
}

function geolocation() {
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showPosition);
	} else {
		
	}
}

function showPosition(position) {
	latitude = position.coords.latitude;
	longitude = position.coords.longitude;
	myGoogleMapsCallbackFunTwo();
	getSpotsFromServer(latitude, longitude, 10);
}

function myGoogleMapsCallbackFunTwo() {
	let mapOptions = {
		center: new google.maps.LatLng(latitude, longitude),
		zoom: 15
	};
	
	let map = new google.maps.Map(document.getElementById("mapWithPlaces"), mapOptions);
}

function getSpotsFromServer(lat, long, distance) {	
	if(lat == 0 && long == 0) {
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
		"distance": Number(distance),
		"downhill": false,
		"gap": false,
		"handrail": false,
		"ledge": false,
		"location": {
			"city": null,
			"latitude": Number(lat),
			"longitude": Number(long)
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
			locatePlacesOnMap(data, lat, long);
		},
		error: function(error) {
			if(error.status == "404") {
				alert("Not found places with provided search criteria!");
			} else {
				alert("error");
			}
		}
	});
}

function locatePlacesOnMap(placesArray, lat, long) {
	let mapOptions = {
		center: new google.maps.LatLng(lat, long),
		zoom: 15
	};
	let map = new google.maps.Map(document.getElementById("mapWithPlaces"), mapOptions);
	
	for(let i = 0 ; i < placesArray.length ; i++) {
		let placePosition =  new google.maps.LatLng(placesArray[i].location.latitude, placesArray[i].location.longitude);
		let placeMarker = new google.maps.Marker({position: placePosition});
		placeMarker.setMap(map);
		
		let placeHtmlContent = '<p style="color: black;">' + placesArray[i].name + '</p>' + '<img src="data:image/png;base64,' + placesArray[i].mainPhoto + '" style="width: 200px; height: 200px;"/>';
		placeHtmlContent += '<a href="#" onClick="openPlaceOverlayContainer(' + placesArray[i].id + ');" style="display: block;">More details</a>'
		
		google.maps.event.addListener(placeMarker, 'click', function() {
			var infoWindow = new google.maps.InfoWindow({
				content: placeHtmlContent
			});
			infoWindow.open(map, placeMarker);
		});
	}
}