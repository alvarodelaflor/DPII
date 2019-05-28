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
					<spring:message code="travelPack.price" />
					:
					<jstl:out value="${price}"></jstl:out> EUR
				</div>
				
				<div class="row">
					<spring:message code="travelPack.accomodations" />
					:
				</div>
				<jstl:if test="${travelPack.draft}">
				<div class="col-md-4">
					<acme:cancel url="finder/travelAgency/show.do" code="travelPack.newAcc" />
				</div>
				</jstl:if>
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
				<jstl:if test="${travelPack.draft}">
				<div class="col-md-4">
					<acme:cancel url="transport/travelAgency/list.do" code="travelPack.newTrans" />
				</div>
				</jstl:if>
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
					<spring:message code="travelPack.warranty" />
					:
					<jstl:out value="${travelPack.warranty.title}"></jstl:out>
				</div>
				
				<div class="row">
					<spring:message code="travelPack.complaint" />
					:
				</div>
				<display:table name="travelPack.complaints" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag table table-hover">
					<div class="row">
						<div class="col-md-12">
							<fieldset>
								<display:column titleKey="complaint.moment" property="moment" />
								<display:column titleKey="complaint.customer">
									<jstl:out value="${row.customer.name}"></jstl:out>
								</display:column>
								<display:column titleKey="complaint.description" property="description"/>
								<display:column titleKey="complaint.host">
									<jstl:out value="${row.host.name}"></jstl:out>
								</display:column>
								<display:column titleKey="complaint.transporter">
									<jstl:out value="${row.transporter.name}"></jstl:out>
								</display:column>
							</fieldset>
						</div>
					</div>
				</display:table>
				
			</fieldset>
		</div>
	</div>

	<br>

	<div class="row">
		<div class="col-md-4">
			<acme:cancel url="travelPack/travelAgency/list.do" code="actor.back" />
		</div>
		<jstl:choose>
			<jstl:when test="${travelPack.draft}">
				<div class="col-md-4">
				<acme:cancel url="travelPack/travelAgency/delete.do?travelPackId=${travelPack.id}" code="curricula.delete" />
				</div>
			</jstl:when>
			<jstl:otherwise>
				<div class="col-md-4">
				<acme:cancel url="travelPack/travelAgency/delete.do?travelPackId=${travelPack.id}" code="travelPack.cancel" />
				</div>
			</jstl:otherwise>
		
		</jstl:choose>
	</div>
</div>
