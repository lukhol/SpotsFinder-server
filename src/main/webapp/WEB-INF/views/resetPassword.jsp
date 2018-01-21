<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>
		<spring:message code="label.title.resetPassword"/>
	</title>
	
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/resetPassword.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script type="text/javascript" src="/js/resetPassword.js"></script>
</head>
<body>
	<div id="header">
		<h2>
			<spring:message code="label.header.resetPassword"/>
		</h2>
	</div>
	
	<div id="container">
		<input type="hidden" id="email" value="${email}"/>
		<input type="hidden" id="code" value="${code}" />
		<input type="hidden" id="base-url" value="${BASE_URL}" />
		
		<div id="password-container">
			<h2> <spring:message code="label.message.changePassword" /> ${email} </h2>
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