<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="sf-container">
	<h1>${place.name}</h1>
	<c:forEach items="${place.images}" var="image">
		<img class="sf-image-resizeable" src="data:image/png;base64, ${image.image}"/>
	</c:forEach>
</div>