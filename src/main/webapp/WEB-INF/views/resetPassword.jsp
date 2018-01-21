<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Reset password</title>
	
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/resetPassword.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script type="text/javascript" src="/js/resetPassword.js"></script>
</head>
<body>
	<div id="header">
		<h2>Spots Finder - reset password.</h2>
	</div>
	
	<div id="container">
		<input type="hidden" id="email" value="${email}"/>
		<input type="hidden" id="code" value="${code}" />
		<div id="password-container">
			<h2> Change password for: ${email} </h2>
			<br/>
			<input id="password-input" placeholder="password" type="password"/>
			<br/>
			<input id="password-confirm-input" placeholder="confirm password" type="password"/>
			<br/>
			<button class="button" type="button" onClick="resetPassword()">Reset</button>
			
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