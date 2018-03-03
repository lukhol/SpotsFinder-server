<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> <!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>
		<tiles:insertAttribute name="title" />
	</title>
	
	<!-- Bootstrap: -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<!-- React: -->
	<script src="https://unpkg.com/react/umd/react.development.js"></script>
 	<script src="https://unpkg.com/react-dom/umd/react-dom.development.js"></script>
  	<script src="https://unpkg.com/babel-standalone/babel.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/header.css">
	<link rel="stylesheet" type="text/css" href="/css/overlapMenu.css">
	<link rel="stylesheet" type="text/css" href="/css/footer.css"/>
	
	<!-- Menu script: -->
	<script type="text/javascript" src="/js/menu.js"></script>
	
	<!-- Handlebars: -->
	<script type="text/javascript" src="/js/lib/handlebars.js"></script>
	<script type="text/javascript" src="/js/lib/handlebarsHelper.js"></script>
	
</head>
<body>
	<div class="container">
		<div id="testReact"></div>
	</div>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />
	
	<!-- Place details modal on each page: -->
	<div id="placeDetailsModalContainer"></div>
	<script type="text/babel" src="/js/placeDetails.js"></script>
	
	<!-- Scroll to top button: -->
	<div class="sf-scroll-to-top" style="display: none;">
		^
	</div>
	<!-- Universal message bottom popup -->
	<div class="sf-bottom-message"></div>
</body>
</html>