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
					<div class="col-md-3">
						<spring:message code="bookingAccomodation.startDate" />
					</div>
					<div class="col-md-5">
						<acme:displayDateTable date="${bookingAccomodation.startDate}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="bookingAccomodation.endDate" />
					</div>
					<div class="col-md-5">
						<acme:displayDateTable date="${bookingAccomodation.endDate}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="accomodation.pricePerNight" />
					</div>
					<div class="col-md-5">
						<jstl:out value="${bookingAccomodation.accomodation.pricePerNight}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="accomodation.rating" />
					</div>
					<div class="col-md-5">
						<jstl:out value="${bookingAccomodation.accomodation.rating}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="accomodation.address" />
					</div>
					<div class="col-md-5">
						<jstl:out value="${bookingAccomodation.accomodation.address}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="accomodation.place" />
					</div>
					<div class="col-md-5">
						<jstl:out value="${bookingAccomodation.accomodation.place}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="accomodation.description" />
					</div>
					<div class="col-md-5">
						<jstl:out value="${bookingAccomodation.accomodation.place}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="accomodation.maxPeople" />
					</div>
					<div class="col-md-5">
						<jstl:out value="${bookingAccomodation.accomodation.maxPeople}" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="accomodation.host" />
					</div>
					<div class="col-md-5">
						<jstl:out value="${bookingAccomodation.accomodation.host.name} ${bookingAccomodation.accomodation.host.surname}" />
					</div>
				</div>
			</fieldset>
		</div>
	</div>

	<br>

	<div class="row">
		<div class="col-md-4">
			<acme:cancel url="travelPack/travelAgency/list.do" code="actor.back" />
		</div>
		<div class="col-md-4">
			<acme:cancel url="travelPack/travelAgency/delete.do?travelPackId=${travelPack.id}" code="curricula.delete" />
		</div>
	</div>
</div>
