<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<br>

<h2><spring:message code="companies" /></h2>
	<h3><spring:message code="positionsPerCompany" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgPositionPerCompany}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minPositionPerCompany}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxPositionPerCompany}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${stddevPositionPerCompany}"></jstl:out></p>
	<br>
	
	<h3><spring:message code="salariesPerPosition" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgSalaryPerPosition}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minSalaryPerPosition}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxSalaryPerPosition}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${stddevSalaryPerPosition}"></jstl:out></p>
		<p><spring:message code="bestPosition" /> <jstl:out value="${bestPosition}"></jstl:out></p>
		<p><spring:message code="worstPosition" /> <jstl:out value="${worstPosition}"></jstl:out></p>
	<br>
	
	<h3><spring:message code="companiesMorePositions" /></h3>
	<p><spring:message code="findCompanyWithMorePositions" /> <jstl:out value="${findCompanyWithMorePositions}"></jstl:out></p>
	<br>

<h2><spring:message code="rookies" /></h2>	
	<h3><spring:message code="applicationsPerRookie" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgApplicationsPerRookie}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minApplicationsPerRookie}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxApplicationsPerRookie}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${stddevApplicationsPerRookie}"></jstl:out></p>
	<br>
	
	<h3><spring:message code="rookieMoreApplications" /></h3>
		<p><spring:message code="findRookieMoreApplications" /> <jstl:out value="${findRookieMoreApplications}"></jstl:out></p>
	<br>
	
	<h2><spring:message code="finder" /></h2>
	<h3><spring:message code="numberOfresult" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgNumberOfResultFinder}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minNumberOfResultFinder}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxNumberOfResultFinder}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${stddevNumberOfResultFinder}"></jstl:out></p>
		<p><spring:message code="ratio" /> <jstl:out value="${ratioResultFinder}"></jstl:out></p>
	<br>
	
	<h2><spring:message code="curriculas" /></h2>
	<h3><spring:message code="numberOfHistory" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgNumberOfHistory}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minNumberOfHistory}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxNumberOfHistory}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${stddevNumberOfHistory}"></jstl:out></p>
	<br>
	
	<h2><spring:message code="audits" /></h2>

	<h3><spring:message code="positionScore" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgPositionScore}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minPositionScore}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxPositionScore}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${stddevPositionScore}"></jstl:out></p>
	<br>

	<h3><spring:message code="companyScore" /></h3>
		<div>
			<b><spring:message code="admin.calculateCompaniesScores"/>: </b>
			<input type="button" value="<spring:message code='admin.calculate' />" onclick="window.location = 'administrator/calculateCompaniesScores.do'" />
		</div>
		<p><spring:message code="avg" /> <jstl:out value="${avgCompanyScore}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minCompanyScore}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxCompanyScore}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${stddevCompanyScore}"></jstl:out></p>
	<br>
	
	<h3><spring:message code="companiesHighestAuditScore" /></h3>
		<p><spring:message code="avgSalaryCompanyHighestScore" /> <jstl:out value="${avgSalaryCompanyHighestScore}"></jstl:out></p>
		<display:table name="companiesHighestScore" id="row" class="displaytag">
			<display:column titleKey="company.comercialName">
				<a href="company/show.do?id=${row.id}"><jstl:out value="${row.commercialName}"/></a></display:column>
			<display:column titleKey="company.position" ><a href="position/listCompany.do?id=${row.id}"><spring:message code="company.position.show" /></a></display:column>
			<display:column titleKey="company.auditScore" property="auditScore"/>
			
		</display:table>
	<br>


	<h2><spring:message code="items" /></h2>
	<h3><spring:message code="itemsPerProvider" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgItemPerProvider}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${minItemPerProvider}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${maxItemPerProvider}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${sttdevItemPerProvider}"></jstl:out></p>
	<br>
	
	<h2><spring:message code="sponsorships" /></h2>
	<h3><spring:message code="sponsorshipsPerProvider" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgSponsorshipPerProvider}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${maxSponsorshipPerProvider}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${sttdevSponsorshipPerProvider}"></jstl:out></p>
		<p><spring:message code="max" /> <jstl:out value="${minSponsorshipPerProvider}"></jstl:out></p>		
	<br>
	<h3><spring:message code="sponsorshipsPerPosition" /></h3>
		<p><spring:message code="avg" /> <jstl:out value="${avgSponsorshipPerPosition}"></jstl:out></p>		
		<p><spring:message code="min" /> <jstl:out value="${maxSponsorshipPerPosition}"></jstl:out></p>		
		<p><spring:message code="max" /> <jstl:out value="${minSponsorshipPerPosition}"></jstl:out></p>		
		<p><spring:message code="stddev" /> <jstl:out value="${sttdevSponsorshipPerPosition}"></jstl:out></p>
	<br>
	
	<h2><spring:message code="providers" /></h2>
	<h3><spring:message code="providersMoreSponsorshipTanAverage" /></h3>
		<p><spring:message code="list" /> <jstl:out value="${sponsorshipProvider}"></jstl:out></p>		
	<br>
	
		<acme:cancel url=" " code="Back" />
	
	