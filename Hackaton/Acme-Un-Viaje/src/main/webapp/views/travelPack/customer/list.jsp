<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<display:table name="travelPacks" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag table table-hover">
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<display:column titleKey="travelPack.name" property="name" />
					<display:column titleKey="travelPack.price" property="price" />
					<display:column titleKey="travelPack.accomodations">
						<jstl:forEach var="booking" items="${row.accomodations}">
							[<a href="bookingAccomodation/customer/show.do?bookingId=${booking.id}">
								<jstl:out value="${booking.accomodation.place}" />
							</a>]
						</jstl:forEach>
					</display:column>
					<display:column titleKey="travelPack.transport">
						<jstl:forEach var="booking" items="${row.transports}">
							[<a href="bookingTransport/customer/show.do?bookingId=${booking.id}">
								<jstl:out value="${booking.transport.vehicleType}" />
							</a>]
						</jstl:forEach>
					</display:column>
					<display:column titleKey="travelPack.travelAgency">
						<jstl:out value="${row.travelAgency.name} ${row.travelAgency.surname}" />
					</display:column>

					<jstl:if test="${offered eq true}">
						<display:column titleKey="travelPack.accept">
							<a href="travelPack/customer/accept.do?travelPackId=${row.id}">
								<spring:message code="travelPack.accept" />
							</a>
						</display:column>
						<display:column titleKey="travelPack.reject">
							<a href="travelPack/customer/reject.do?travelPackId=${row.id}">
								<spring:message code="travelPack.reject" />
							</a>
						</display:column>
					</jstl:if>
					<jstl:if test="${empty offered}">
						<display:column titleKey="complaint.create">
							<a href="complaint/customer/create.do?travelPackId=${row.id}">
								<spring:message code="complaint.create" />
							</a>
						</display:column>
					</jstl:if>
				</fieldset>
			</div>
		</div>
	</display:table>

	<br>

	<div class="row">
		<div class="col-md-12">
			<span>
				<acme:cancel url="" code="actor.back" />
			</span>
		</div>
	</div>
</div>

