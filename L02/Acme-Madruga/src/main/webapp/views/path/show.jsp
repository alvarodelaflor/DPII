
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${not owner}">
<ol>
<jstl:forEach var="segment" items="${segments}">
	<li> <jstl:out value=" Origen: (${segment.latitude}, ${segment.longitude}); Destino: (${segment.destination.latitude}, ${segment.destination.longitude})"/> </li>
</jstl:forEach>
</ol>
</jstl:if>

<jstl:if test="${owner}">

<spring:message code='path.origin' var="pathOrigin"/>
<spring:message code='path.destination' var="pathDestination"/>

<ol>
<li>
<form id="segment" action="path/brotherhood/edit.do" method="post">
	<input id="paradeId" name="paradeId" value="${paradeId}" type="hidden">
	<input id="id" name="id" value="${segments[0].id}" type="hidden">

	<label>${pathOrigin}: (</label>
	<input id="latitude" name="latitude" value="${segments[0].latitude}" type="text">
	<label>, </label>
	<input id="longitude" name="longitude" value="${segments[0].longitude}" type="text">
	<label>) =></label>
		
	<label>${pathDestination}: (</label>
	<input id="destination.latitude" name="destination.latitude" value="${segments[0].destination.latitude}" type="text">
	<label>, </label>
	<input id="destination.longitude" name="destination.longitude" value="${segments[0].destination.longitude}" type="text">
	<label>)</label>

	<button type="submit" name="edit" class="btn btn-primary"><spring:message code="path.edit"/></button>
	
</form>
</li>

<jstl:forEach var="segment" items="${segments}" varStatus="loop">
	<form id="segment" action="path/brotherhood/edit.do" method="post">
		
		<jstl:if test="${not loop.first}">
		<li>
			<input id="paradeId" name="paradeId" value="${paradeId}" type="hidden">
			<input id="id" name="id" value="${segment.id}" type="hidden">
			<input id="latitude" name="latitude" value="${segment.latitude}" type="hidden">
			<input id="longitude" name="longitude" value="${segment.longitude}" type="hidden">
			
			<jstl:out value="${pathOrigin}: (${segment.latitude}, ${segment.longitude}) =>"/>
			<label>${pathDestination}: (</label>
			<input id="destination.latitude" name="destination.latitude" value="${segment.destination.latitude}" type="text">
			<label>, </label>
			<input id="destination.longitude" name="destination.longitude" value="${segment.destination.longitude}" type="text">
			<label>)</label>
			
			<button type="submit" name="edit" class="btn btn-primary"><spring:message code="path.edit"/></button>
		</li>
		</jstl:if>
	</form>
</jstl:forEach>
</ol>
</jstl:if>
<div>
	<form method="get" action="#">
		<button type="submit">
			<spring:message code="back" />
		</button>
	</form>
</div>
