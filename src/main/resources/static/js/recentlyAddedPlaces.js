var currentPlace = document.getElementById("place-loader-container");
var placeContainer = document.getElementById("place-container");
var downloadedPlaces = [];

function openPlaceOverlayContainer(placeId) {
	fetchPlace(placeId);
	
	var viewPortWidth = document.documentElement.clientWidth;
	var placeOverlayContainer = document.getElementById("placeOverlayContainer");
	
	if(viewPortWidth > 700) {
		placeOverlayContainer.style.width = "100%";
	} else {
		placeOverlayContainer.style.width = "100%";
	}
}

function closePlaceOverlayContainer() {
	document.getElementById("placeOverlayContainer").style.width = "0%";
}

$(document).keyup(function(e) {
    if (e.keyCode == 27) {
    	closePlaceOverlayContainer();
    }
});

function fetchPlace(placeId) {
	let baseUrl = $("#base-url").val();
	let queryUrl = baseUrl + '/places/' + placeId;
	let loaderContainer = document.getElementById("place-loader-container");
	
	let doesArrayContainsPlace = false;
	for(let i = 0 ; i < downloadedPlaces.length ; i++) {
		if(downloadedPlaces[i].id == placeId){
			doesArrayContainsPlace = true;
			currentPlace = downloadedPlaces[i];
			break;
		}
	}
	
	if(doesArrayContainsPlace) {
		displayPlace(currentPlace);
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
			currentPlace = data;
			downloadedPlaces.push(currentPlace);
			displayPlace(currentPlace);
		}, 
		error: function(error) {
			console.log(error);
		}
	});
}

function displayPlace(placeToShow) {
	let placeContainerInnerHTML = '';
	placeContainerInnerHTML = '<h1 class="sf-center">' + placeToShow.name + '</h1> <hr/>';
	placeContainerInnerHTML += '<div class="col-container" style="background-color: yelow;">';
	placeContainerInnerHTML += '<div class="col"> Description: <br>' + placeToShow.description + '</div>';
	placeContainerInnerHTML+= '<div class="col" style="background-color: yelow;"> Obstacles: <ul class="obstacles">' + generateBooleanSection(placeToShow) + '</ul> </div>';
	placeContainerInnerHTML += '</div>';
	//placeContainerInnerHTML += '<div class="sf-clearfix"></div>';
	
	placeContainerInnerHTML += '<div class="sf-wrapper" style="text-align: center overflow: auto;">';
	for(let i = 0 ; i < placeToShow.images.length ; i++){
		placeContainerInnerHTML += '<div class="sf-responsive-five sf-big-on-hover" style="padding: 10px; padding-top: 0px; display: inline-block;"> <img class="sf-image-resizeable" onclick="selectImage(this);" src="data:image/png;base64, ' + currentPlace.images[i].image + '"/> </div>';
	}
	placeContainerInnerHTML += '<div class="sf-clearfix"></div>';
	placeContainerInnerHTML += '</div>';
	//placeContainerInnerHTML += '<div class="sf-clearfix"></div>';
	
	let viewPortWidth = document.documentElement.clientWidth;
	if(viewPortWidth > 700) {
		placeContainerInnerHTML += '<div style="width: 100%; text-align: center;"><img id="selectedImage" class="sf-image-resizeable" src="data:image/png;base64, ' + placeToShow.images[0].image + '"/> </div>';
	}
	placeContainerInnerHTML += '<div style="width: 100%; height: 400px; padding: 10px; padding-top: 0px;"><div id="googleMap" style="width:100%; height: 100%;"></div></div>';
	placeContainer.innerHTML = placeContainerInnerHTML;
	myGoogleMapsCallbackFun();
}

function selectImage(imageToDisplay) {
	let bigImage = document.getElementById("selectedImage");
	bigImage.src = imageToDisplay.src;
}

function myGoogleMapsCallbackFun() {
	let placePosition =  new google.maps.LatLng(currentPlace.location.latitude, currentPlace.location.longitude);
	let mapProp = {
		center: placePosition,
		zoom: 15,
	}
	let marker = new google.maps.Marker({position: placePosition});
	let map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	marker.setMap(map);
}

function generateBooleanSection(placeToShow) {
	let htmlCode = "";
	htmlCode += generateBooleanListItemHtml(placeToShow.gap, "gap");
	htmlCode += generateBooleanListItemHtml(placeToShow.stairs, "stairs");
	htmlCode += generateBooleanListItemHtml(placeToShow.rail, "rail");
	htmlCode += generateBooleanListItemHtml(placeToShow.ledge, "ledge");
	htmlCode += generateBooleanListItemHtml(placeToShow.handrail, "handrail");
	htmlCode += generateBooleanListItemHtml(placeToShow.corners, "corners");
	htmlCode += generateBooleanListItemHtml(placeToShow.manualpad, "manualpad");
	htmlCode += generateBooleanListItemHtml(placeToShow.wallride, "wallride");
	htmlCode += generateBooleanListItemHtml(placeToShow.downhill, "downhill");
	htmlCode += generateBooleanListItemHtml(placeToShow.openYourMind, "open your mind");
	htmlCode += generateBooleanListItemHtml(placeToShow.pyramid, "pyramid");
	htmlCode += generateBooleanListItemHtml(placeToShow.curb, "curb");
	htmlCode += generateBooleanListItemHtml(placeToShow.bowl, "bowl");
	htmlCode += generateBooleanListItemHtml(placeToShow.bank, "bank");
	return htmlCode;
		
}

function generateBooleanListItemHtml(fieldValue, fieldName) {
	if(fieldValue)
		return '<li>' + fieldName + '</li>';
	else
		return '';
}