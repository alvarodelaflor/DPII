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

<body>
	<div>
		<security:authorize access="hasRole('PROVIDER')">
			<display:table name="sponsorships" id="row"
				requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column property="provider.name" titleKey="provider.name" />
				<display:column property="position.title" titleKey="position.title" />
				<display:column>
					<a href="sponsorship/provider/show.do?sponsorshipId=${row.id}">
						<spring:message code="show" />
					</a>
				</display:column>
			</display:table>
			<br />
			<br />
			<jstl:if test="${not notValid}">
				<acme:cancel url="sponsorship/provider/create.do" code="create" />
			</jstl:if>
		</security:authorize>
	</div>
	<div>
		<security:authorize access="hasRole('ADMIN')">
			<display:table name="sponsorships" id="row"
				requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column property="target" titleKey="sponsorship.target"></display:column>
				<display:column property="provider.name"
					titleKey="provider.name"></display:column>
				<display:column property="creditCard.number"
					titleKey="creditCard.number"></display:column>
				<display:column property="creditCard.expiration"
					titleKey="creditCard.expiration"></display:column>
				<display:column titleKey="charge">
					<jstl:out
						value="${(config.fair+((config.fair*config.VAT)/100.0))*row.bannerCount}"></jstl:out>
				</display:column>
				<display:column titleKey="collect">
					
					<a href="administrator/collect.do?sponsorshipId=${row.id}"><spring:message
							code="collect"></spring:message></a>
				</display:column>
			</display:table>
		</security:authorize>
	</div>
</body>