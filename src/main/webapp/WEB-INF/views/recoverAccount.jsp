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
	<link rel="stylesheet" type="text/css" href="/css/overlapMenu.css">
</head>
<body>
	<div id="header">
	
		<!-- Menu: -->
		<div id="myMenuNav" class="overlay" >
			<a href="javascript:void(0)" class="closebtn" onclick="hideMenu()">&times;</a>
			<div class="overlay-content">
				<a href="/home">About</a>
			    <a href="#">Help</a>
			</div>
		</div>
	
		<h2>
			<span style="font-size:30px;cursor:pointer; float:left; margin: 10px;" onclick="showMenu()">&#9776;</span>
			<spring:message code="label.header.recoverAccount"/>
		</h2>
	</div>
	<div id="container">
		<div id="password-container">
			<div>
				<input type="hidden" id="base-url" value="${BASE_URL}" />
				
				<c:set var="msg1"><spring:message code="label.message.emailSend"/></c:set>
				<input id="label-message-emailSend" type="hidden" value="${msg1} "/>
				
				<c:set var="msg2"><spring:message code="label.message.emailNotFound"/></c:set>
				<input id="label-message-emailNotFound" type="hidden" value="${msg2} "/>
			</div>
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
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script type="text/javascript" src="/js/recoverAccount.js"></script>
	<script type="text/javascript" src="/js/menu.js"></script>
</body>
</html>