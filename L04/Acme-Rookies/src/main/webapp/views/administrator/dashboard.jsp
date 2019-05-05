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
<div>
	<h2><spring:message code="admin.calculateCompaniesScores"/></h2>
	<input type="button" value="<spring:message code='admin.calculate' />" onclick="window.location = 'administrator/calculateCompaniesScores.do'" />
</div>
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
	
		<acme:cancel url=" " code="Back" />
	
	
