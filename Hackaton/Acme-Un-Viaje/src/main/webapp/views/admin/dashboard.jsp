<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<hr>
<body>
	<div class="container-fluid">
		<security:authorize access="hasRole('ADMIN')">
			<div>
				<h3>
					<spring:message code="curriculaPerCleaner" />
				</h3>
				<br />
				<p>
					<spring:message code="min" />
					<jstl:out value="${minCurriculaPerCleaner}"></jstl:out>
				</p>
				<p>
					<spring:message code="max" />
					<jstl:out value="${maxCurriculaPerCleaner}"></jstl:out>
				</p>
				<p>
					<spring:message code="avg" />
					<jstl:out value="${avgCurriculaPerCleaner}"></jstl:out>
				</p>
				<p>
					<spring:message code="stddev" />
					<jstl:out value="${stddevCurriculaPerCleaner}"></jstl:out>
				</p>
				<br>
			</div>
			<div>
				<h3>
					<spring:message code="bestCustomer" />
				</h3>
				<br />
				<p>
					<c:if test="${not empty bestCustomer}"><jstl:out value="${bestCustomer}"></jstl:out></c:if>
						<c:if test="${empty bestCustomer}">---</c:if>
				</p>
			</div>
			<div>
				<h3>
					<spring:message code="bestHost" />
				</h3>
				<br />
				<p>
					<c:if test="${not empty bestHost}"><jstl:out value="${bestHost}"></jstl:out></c:if>
						<c:if test="${empty bestHost}">---</c:if>
				</p>
			</div>
			<div>
				<h3>
					<spring:message code="bestTransporter" />
				</h3>
				<br />
				<p>
					<c:if test="${not empty bestTransporter}"><jstl:out value="${bestTransporter}"></jstl:out></c:if>
						<c:if test="${empty bestTransporter}">---</c:if>
				</p>
			</div>
			<div>
				<h3>
					<spring:message code="bestTravelAgency" />
				</h3>
				<br />
				<p>
						<c:if test="${not empty bestTravelAgency}"><jstl:out value="${bestTravelAgency}"></jstl:out></c:if>
						<c:if test="${empty bestTravelAgency}">---</c:if>
				</p>
			</div>
			<div>
				<h3>
					<spring:message code="bestCleaner" />
				</h3>
				<br />
				<p>
					<c:if test="${not empty bestCleaner}"><jstl:out value="${bestCleaner}"></jstl:out></c:if>
						<c:if test="${empty bestCleaner}">---</c:if>
				</p>
			</div>
			<div>
				<acme:cancel url=" " code="Back"/>
			</div>
		</security:authorize>
	</div>
</body>