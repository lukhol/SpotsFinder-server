<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>
		<spring:message code="label.title.recoverAccount"/>
	</title>
	
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/recoverAccount.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script type="text/javascript" src="/js/recoverAccount.js"></script>
</head>
<body>
	<div id="header">
		<h2>
			<spring:message code="label.header.recoverAccount"/>
		</h2>
	</div>
	
	<div id="container">
		<div id="password-container">
			<input type="hidden" id="base-url" value="${BASE_URL}" />
			
			<input id="email-input" type="text" placeholder="email">
			<br/>
			<button class="button" type="button" onclick="resetPasswordEmail()"><spring:message code="label.button.resetPassword"/></button>
			
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