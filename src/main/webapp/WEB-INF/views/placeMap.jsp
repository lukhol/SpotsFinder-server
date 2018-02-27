<input type="hidden" id="base-url" value="${BASE_URL}" />

<!-- Modal popup with place details. -->
<div id="placeOverlayContainer" class="overlay-container" style="width: 0%;">
	<a href="javascript:void(0)" class="closebtn" onclick="closePlaceOverlayContainer()">&times;</a>
	<div id="place-loader-container" class="display-middle" style="display: none;">
		<div class="sf-loader-big"></div>
	</div>
	<div id="place-container">
		
	</div>
	<div class="sf-clearfix"></div>
</div>

<div id="container">
	<div id="mapWithPlaces" class="sf-wrapper sf-center" style="width: 100%; height: 500px; display: inline-block; color: blac;">
	</div>
	<div class=".sf-clearfi"></div>
	<div class="sf-slider-container">
  		<input type="range" min="1" max="30" value="10" id="distance-slider" class="sf-slider">
	</div>
	<div id="slider-value">
		10
	</div>
</div>

<!-- Page scripts: -->
<script type="text/javascript" src="/js/placeMap.js"></script>
<script type="text/javascript" src="/js/recentlyAddedPlaces.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBSBIeH17u88T7qlXU34l7dRZgnB47VuM4&callback=myGoogleMapsCallbackFunTwo"></script>