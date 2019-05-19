<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">
		<div class="col-md-4">
			<fieldset>
				<div class="row">
					<spring:message code="transport.numberOfPlaces" />
					:
					<jstl:out value="${transport.numberOfPlaces}"></jstl:out>
				</div>
				<div class="row">
					<spring:message code="transport.date" />
					:
					<jstl:out value="${transport.date}"></jstl:out>
				</div>
				<div class="row">
					<spring:message code="transport.vehicleType" />
					:
					<jstl:out value="${transport.vehicleType}"></jstl:out>
				</div>
				<div class="row">
					<spring:message code="transport.origin" />
					:
					<jstl:out value="${transport.origin}"></jstl:out>
				</div>
				<div class="row">
					<spring:message code="transport.destination" />
					:
					<jstl:out value="${transport.destination}"></jstl:out>
				</div>
			</fieldset>
		</div>
	</div>

	<br>

	<div class="row">
		<div class="col-md-4">
			<acme:cancel url="" code="actor.back" />
		</div>
	</div>
</div>
