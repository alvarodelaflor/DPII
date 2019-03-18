
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

<!-- Both not owner and owner -->
<spring:message code='path.origin' var="pathOrigin"/>
<spring:message code='path.destination' var="pathDestination"/>


<!-- When we are not the parade owner and have segments-->
<jstl:if test="${not owner and fn:length(segments) > 0}">
<ol>
<jstl:forEach var="segment" items="${segments}">
	<li> <jstl:out value=" ${pathOrigin}: (${segment.latitude}, ${segment.longitude}) =>${pathDestination}: (${segment.destination.latitude}, ${segment.destination.longitude})"/> </li>
</jstl:forEach>
</ol>
</jstl:if>


<!-- When we are the parade owner and have 1 segment-->
<jstl:if test="${owner and fn:length(segments) <= 1}">
	<li>
	<form id="segment" action="path/brotherhood/edit.do" method="post">
		<input id="paradeId" name="paradeId" value="${paradeId}" type="hidden">
		<input id="id" name="id" value="${not empty segments[0].id ? segments[0].id : 0}" type="hidden">
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
		<button type="button" name="delete" class="btn btn-primary" onclick="window.location='path/brotherhood/delete.do?paradeId=${paradeId}&segmentId=${not empty segments[0].id ? segments[0].id : 0}'">
			<spring:message code="path.delete"/>
		</button>
	</form>
	</li>
</jstl:if>

<!-- When we are the parade owner and have more than 1 segment-->
<jstl:if test="${owner and fn:length(segments) > 1}">
<ol>
<li>
<form id="segment" action="segment/brotherhood/edit.do" method="post">
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
	<button type="button" name="delete" class="btn btn-primary" onclick="window.location='segment/brotherhood/delete.do?paradeId=${paradeId}&segmentId=${segments[0].id}'">
		<spring:message code="path.delete"/>
	</button>
</form>
</li>

<jstl:forEach var="segment" items="${segments}" varStatus="loop">
	<form id="segment" action="segment/brotherhood/edit.do" method="post">
		
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
			<button type="button" name="delete" class="btn btn-primary" onclick="window.location='segment/brotherhood/delete.do?paradeId=${paradeId}&segmentId=${segment.id}'">
				<spring:message code="path.delete"/>
			</button>
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
