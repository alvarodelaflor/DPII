<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="col-md-12">
		<div class="row">
			<strong><spring:message code="transport.numberOfPlaces" />:&nbsp;</strong>
			<jstl:out value="${transport.numberOfPlaces}"></jstl:out>
		</div>
		<div class="row">
			<strong><spring:message code="transport.date" />:&nbsp;</strong>
			<jstl:out value="${transport.date}"></jstl:out>
		</div>
		<div class="row">
			<strong><spring:message code="transport.vehicleType" />:&nbsp;</strong>
			<jstl:out value="${transport.vehicleType}"></jstl:out>
		</div>
		<div class="row">
			<strong><spring:message code="transport.origin" />:&nbsp;</strong>
			<jstl:out value="${transport.origin}"></jstl:out>
		</div>
		<div class="row">
			<strong><spring:message code="transport.destination" />:&nbsp;</strong>
			<jstl:out value="${transport.destination}"></jstl:out>
		</div>
	</div>
	<div class="col-md-12">
		<br>
		<div class="row">

			<span> <acme:historyBack />
			</span>
		</div>
	</div>
</div>
