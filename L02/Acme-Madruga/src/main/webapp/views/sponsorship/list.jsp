<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<security:authorize access="hasRole('SPONSOR')">
		<c:choose>
			<c:when test="${validSponsor}">
				<p class="create"><input type="button" value=<spring:message code="createSponsorship" /> id="buttonSponsorship" name="buttonSponsorship"  onclick="location.href='sponsorship/create.do';"/></p>			
			</c:when>
			<c:otherwise>
				<p><spring:message code="emptyParade" /></p>
			</c:otherwise>
		</c:choose>
		<display:table name="sponsorships" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<display:column titleKey="sponsorship.target">
						<a href="sponsorship/show.do?sponsorshipId=${row.id}"><jstl:out
								value="${row.target}"></jstl:out> </a>
					</display:column>
					
			<c:choose>
				<c:when test="${row.active == true}">
					<display:column titleKey="sponsorship.delete"><a href="sponsorship/delete.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.deactivate" /></a></display:column>
				</c:when>
				<c:otherwise>
					<display:column titleKey="sponsorship.delete"><a href="sponsorship/delete.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.activate" /></a></display:column>

				</c:otherwise>
			</c:choose>		
						
			<display:column titleKey="sponsorship.edit"><a href="sponsorship/edit.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.edit" /></a></display:column>
		</display:table>

	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:table name="sponsorships" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
					<display:column property="target" titleKey="sponsorship.target"></display:column>
					<display:column property="sponsor.name" titleKey="sponsorship.sponsor"></display:column>
					<display:column property="active" titleKey="sponsorship.active"></display:column>
					<display:column property="creditCard.number" titleKey="sponsorship.creditCard.number"></display:column>
					<display:column property="creditCard.expiration" titleKey="sponsorship.creditCard.expiration"></display:column>
					<display:column titleKey="charge">
						<jstl:out value="${(config.fair+((config.fair*config.VAT)/100.0))*row.bannerCount}"></jstl:out>
					</display:column>
					<display:column titleKey="collect">
						<a href="sponsorship/administrator/collect.do?sponsorshipId=${row.id}"><spring:message code="collect"></spring:message></a>
					</display:column>
		</display:table>
			<p class="checkCreditCard"><input type="button" value=<spring:message code="checkCreditCard" /> id="buttoncheckCreditCard" name="buttoncheckCreditCard"  onclick="location.href='sponsorship/administrator/checkCreditCard.do';"/></p>
	</security:authorize>
	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>