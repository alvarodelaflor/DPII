<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid">
	<display:table name="transports" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<display:column titleKey="transport.numberOfPlaces"
						property="numberOfPlaces" />
					<display:column titleKey="transport.price" property="price" />
					<display:column titleKey="transport.date" property="date" />
					<display:column titleKey="transport.vehicleType"
						property="vehicleType" />
					<display:column titleKey="transport.origin" property="origin" />
					<display:column titleKey="transport.destination"
						property="destination" />
					<display:column titleKey="transport.show">
						<a href="transport/travelAgency/show.do?transportId=${row.id}">
							<spring:message code="transport.show" />
						</a>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
	<br>

	<div class="row">
		<div class="col-md-3">
			<span> <acme:cancel url="" code="actor.back" />
			</span>
		</div>
	</div>
</div>

