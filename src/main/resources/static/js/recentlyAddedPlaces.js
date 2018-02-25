var actuallyDownloadedPlace = document.getElementById("place-loader-container");
var placeContainer = document.getElementById("place-container");
var downloadedPlaces = [];

function fetchPlace(placeId) {
	let baseUrl = $("#base-url").val();
	let queryUrl = baseUrl + '/places/' + placeId;
	let loaderContainer = document.getElementById("place-loader-container");
	
	let doesArrayContainsPlace = false;
	for(let i = 0 ; i < downloadedPlaces.length ; i++) {
		if(downloadedPlaces[i].id == placeId){
			doesArrayContainsPlace = true;
			actuallyDownloadedPlace = downloadedPlaces[i];
			break;
		}
	}
	
	if(doesArrayContainsPlace) {
		displayPlace();
		return;
	} else {
		loaderContainer.style.display = "block";
		placeContainer.innerHTML = "";
	}
	
	$.ajax({
		url: queryUrl,
		type: 'GET',
		dataType: 'json',
		headers: {
		    'Authorization': 'Basic c3BvdGZpbmRlcjpzcG90ZmluZGVyU2VjcmV0'
		},
		success: function(data) {
			loaderContainer.style.display = "none";
			actuallyDownloadedPlace = data;
			downloadedPlaces.push(actuallyDownloadedPlace);
			
			displayPlace();
		}, 
		error: function(error) {
			console.log(error);
		}
	});
}

function displayPlace() {
	placeContainer.innerHTML = '<div class="container>';
	placeContainer.innerHTML += '<h1 class="sf-center">' + actuallyDownloadedPlace.name + '</h1>';
	placeContainer.innerHTML += '<div class="sf-center"> ' + actuallyDownloadedPlace.description + '</div>';
	//placeContainer.innerHTML += '<div class="sf-border-white sf-card-white sf-responsive">';
	for(let i = 0 ; i < actuallyDownloadedPlace.images.length ; i++){
		placeContainer.innerHTML += '<div class="sf-border-white sf-card-white sf-responsive-two"> <img class="sf-image-resizeable" src="data:image/png;base64, ' + actuallyDownloadedPlace.images[i].image + '"/> </div>';
	}
	//placeContainer.innerHTML += '</div>';
	placeContainer.innerHTML += '</div>';
}

function showPlace(placeId) {
	fetchPlace(placeId);
	
	var viewPortWidth = document.documentElement.clientWidth;
	var placeOverlayContainer = document.getElementById("placeOverlayContainer");
	
	if(viewPortWidth>700) {
		placeOverlayContainer.style.width = "80%";
	} else {
		placeOverlayContainer.style.width = "100%";
	}
}

function closePlaceOverlayContainer() {
	document.getElementById("placeOverlayContainer").style.width = "0%";
}