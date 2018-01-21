<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Forget password?</title>
	
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/recoverAccount.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script type="text/javascript" src="/js/recoverAccount.js"></script>
</head>
<body>
	<div id="header">
		<h2>Spots Finder - reset password.</h2>
	</div>
	
	<div id="container">
		<div id="password-container">
			<input type="hidden" id="base-url" value="${BASE_URL}" />
			
			<input id="email-input" type="text" placeholder="email">
			<br/>
			<button class="button" type="button" onclick="resetPasswordEmail()">Reset password</button>
			
			<div>
				<div id="loader-container">
					<div class="loader"></div>
				</div>
				<div id="success-message">
				
				</div>
				<div id="error-message">
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>