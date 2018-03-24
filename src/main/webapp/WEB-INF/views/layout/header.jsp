<div class="header">

	<section id="_menu-image" class="hidden-xs">
		<div class="display-container">
			<div class="hamburger" onclick="showMenu()">&#9776;</div>
			<img class="sf-image-resizeable" src="/images/skateboarding-background.jpg" alt="Skateboarding Background Image">
			<div class="display-middle">
				<h1>
					<!--
					<span class="opacity-min sf-black sf-padding sf-border-white"><b>SF</b></span>
					-->
					<kbd class="sf-padding sf-border-white">SF</kbd>
					Spots Finder
				</h1>
			</div>
		</div>
		
		<!-- Menu: -->
		<nav id="myMenuNav" class="overlay" >
			<a href="javascript:void(0)" class="closebtn" onclick="hideMenu()">&times;</a>
			<div class="overlay-content">
				<a href="/home">Home page</a>
				<hr/>
			    <a href="/views/user/recover">Reset password</a>
			    <hr/>
			    <a href="/views/places/map">Map</a>
			    <hr/>
			    <a href="/views/places/recentlyAdded?start=0&count=5">Recently added places</a>
			</div>
		</nav>
	</section>
	
	<!-- Alternative menu: -->
	<section id="_menu-navbar" class="">
		<nav class="navbar navbar-default" style="background-color: #232323;">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mynavbar-content">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/home">Spots Finder</a>
		        </div>
		        
		        <div class="collapse navbar-collapse" id="mynavbar-content">
		            <ul class="nav navbar-nav">
		                <li><a href="/views/user/recover" class="my-modal-test">Zmien haslo</a></li>
		                <li><a href="/views/places/map">Mapa</a></li>
		                <li> <a href="/views/places/recentlyAdded?start=0&count=5">Ostatnio dodane</a></li>
		                <li class="dropdown">
		                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">O nas <b class="caret"></b></a>
		                    <ul class="dropdown-menu" style="background-color: #565656">
		                        <li><a href="#">Item 1</a></li>
		                        <li><a href="#">Item 2</a></li>
		                        <li><a href="#">Item 3</a></li>
		                        <li class="divider"></li>
		                        <li><a href="#">Item 4</a></li>
		                    </ul>
		                </li>
		            </ul>
		        </div>
			</div>
		</nav>
	</section>
	
</div>