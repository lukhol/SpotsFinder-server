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

<!-- Popup with filters: -->
<div class="sf-submenu-left">
	<!-- Open/close button -->
	<div class="sf-submenu-left-close">
		Filter
	</div>
	<div class="sf-submenu-left-content">
		<div class="sf-wrapper">
				<fieldset>
					<legend>Search criteria</legend>
					<div id="slider-value">
						Distance: 10km
					</div>
					<input type="range" id="distanceRange" min="1" max="30" value="10" class="sf-slider">
					<label>Latitude:</label> <br>
					<input type="number" id="latitudeInput" name="latitude" min="-90" max="90" step="0.00000001" value="51.75924850">
					<br>
					<label>Longitude: </label> <br>
					<input type="number" id="longitudeInput" name="longitude" min="-180" max="180" step="0.00000001" value="19.45598330">
					<br>
					<button class="sf-button" onClick="searchPlaces()" style="margin-top: 10px;">Search</button>
				</fieldset>
		</div>
	</div>
</div>

<!-- Main page contante: -->
<div id="container">
	<div id="mapWithPlaces" class="sf-wrapper sf-center" style="width: 100%; height: 500px; display: inline-block; color: blac;">
	</div>
	<div class=".sf-clearfi"></div>
</div>

<!-- Page scripts: -->
<script type="text/javascript" src="/js/placeMap.js"></script>
<script type="text/javascript" src="/js/leftSubmenu.js"></script>
<script type="text/javascript" src="/js/recentlyAddedPlaces.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBSBIeH17u88T7qlXU34l7dRZgnB47VuM4&callback=myGoogleMapsCallbackFunTwo"></script>