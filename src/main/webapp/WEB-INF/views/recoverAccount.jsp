<!-- startpointer -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet" type="text/css" href="/css/recoverAccount.css">

<div class="sf-container">	
	<h2>
		<spring:message code="label.header.recoverAccount"/>
	</h2>
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

<script type="text/javascript" src="/js/recoverAccount.js"></script>
<!-- endpointer -->