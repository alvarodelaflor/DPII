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
					<spring:message code="travelPack.name" />
					:
					<jstl:out value="${travelPack.name}"></jstl:out>
				</div>
				<div class="row">
					<spring:message code="travelPack.draft" />
					:
					<jstl:choose>
						<jstl:when test="${travelPack.draft}"><spring:message code="travelPack.draft.true" /></jstl:when>
						<jstl:otherwise>
							<spring:message code="travelPack.draft.false" />
						</jstl:otherwise>
					</jstl:choose>
				</div>
				<div class="row">
					<spring:message code="transport.customer" />
					:
					<jstl:out value="${travelPack.customer.name}"></jstl:out>
				</div>
				
				<div class="row">
					<spring:message code="travelPack.accomodations" />
					:
				</div>
				<div class="col-md-4">
					<acme:cancel url="finder/travelAgency/show.do" code="travelPack.newAcc" />
				</div>
				<display:table name="travelPack.accomodations" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag table table-hover">
					<div class="row">
						<div class="col-md-12">
							<fieldset>
								<display:column titleKey="baccomodation.startDate" property="startDate" />
								<display:column titleKey="baccomodation.endDate" property="endDate" />
								<display:column titleKey="baccomodation.price">
									<jstl:out value="${row.accomodation.pricePerNight}"></jstl:out>
								</display:column>
							</fieldset>
						</div>
					</div>
				</display:table>
				<div class="row">
					<spring:message code="travelPack.transports" />
					:
				</div>
				<div class="col-md-4">
					<acme:cancel url="transport/travelAgency/list.do" code="travelPack.newTrans" />
				</div>
				
				<display:table name="travelPack.transports" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag table table-hover">
					<div class="row">
						<div class="col-md-12">
							<fieldset>
								<display:column titleKey="btransport.date" property="date" />
								<display:column titleKey="btransport.transport">
									<jstl:out value="${row.transport.vehicleType}"></jstl:out>
								</display:column>
								<display:column titleKey="transport.price">
									<jstl:out value="${row.transport.price}"></jstl:out>
								</display:column>
							</fieldset>
						</div>
					</div>
				</display:table>
				
				<div class="row">
					<spring:message code="travelPack.complaint" />
					:
					<jstl:out value="${travelPack.complaint.description}"></jstl:out>
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
