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
	<div class="row">
		<div class="col-md-4">
			<fieldset>
				<div class="row">
					<div class="col-md-5">
						<strong><spring:message code="transport.numberOfPlaces" /></strong>
					</div>
					<div class="col-md-7">
						<jstl:out value="${bookingTransport.transport.numberOfPlaces}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<strong><spring:message code="transport.reservedPlaces" /></strong>
					</div>
					<div class="col-md-7">
						<jstl:out value="${bookingTransport.transport.reservedPlaces}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<strong><spring:message code="transport.price" /></strong>
					</div>
					<div class="col-md-7">
						<jstl:out value="${bookingTransport.transport.price}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<strong><spring:message code="transport.date" /></strong>
					</div>
					<div class="col-md-7">
						<jstl:out value="${bookingTransport.transport.date}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<strong><spring:message code="transport.vehicleType" /></strong>
					</div>
					<div class="col-md-7">
						<jstl:out value="${bookingTransport.transport.vehicleType}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<strong><spring:message code="transport.origin" /></strong>
					</div>
					<div class="col-md-7">
						<jstl:out value="${bookingTransport.transport.origin}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<strong><spring:message code="transport.destination" /></strong>
					</div>
					<div class="col-md-7">
						<jstl:out value="${bookingTransport.transport.destination}" />
					</div>
				</div>
			</fieldset>
		</div>
	</div>

	<br>
	<div class="row">
		<div class="col-md-12">
			<acme:historyBack />
		</div>
	</div>
</div>
