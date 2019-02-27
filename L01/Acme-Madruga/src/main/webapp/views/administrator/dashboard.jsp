<%--
 * action-1.jsp
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

<security:authorize access="hasRole('ADMIN')"> 

		<p><spring:message code="admin.TheLargestBrotherhoods" /> <jstl:out	value="${largestBrotherhood}"></jstl:out><spring:message code="admin.row" /></p>
		<p><spring:message code="admin.TheSmallestBrotherhoods" /> <jstl:out value="${smallestBrotherhood}"></jstl:out><spring:message code="admin.row" /></p>		
		<p><spring:message code="admin.TheRatioRequestsTrue" /> <jstl:out value="${getRatioRequestStatusTrue}"></jstl:out></p>
		<p><spring:message code="admin.TheRatioRequestsFalse" /> <jstl:out value="${getRatioRequestStatusFalse}"></jstl:out></p>
		<p><spring:message code="admin.TheRatioRequestsNull" /> <jstl:out value="${getRatioRequestStatusNull}"></jstl:out></p>
		<p><spring:message code="admin.getRatioRequestProcessionStatusTrue" /> <jstl:out value="${getRatioRequestProcessionStatusTrue}"></jstl:out></p>
		<p><spring:message code="admin.getRatioRequestProcessionStatusFalse" /> <jstl:out value="${getRatioRequestProcessionStatusFalse}"></jstl:out></p>
		<p><spring:message code="admin.getRatioRequestProcessionStatusNull" /> <jstl:out value="${getRatioRequestProcessionStatusNull}"></jstl:out></p>
		
		
<p><spring:message code="admin.lisMemberAccept" /> </p>	
<display:table pagesize="5" name="${lisMemberAccept}" id="lisMemberAccept"
	requestURI="${requestURI}">
		<display:column property="name" titleKey="admin.member.name"></display:column>
		<display:column property="middleName" titleKey="admin.member.middleName"></display:column>
		<display:column property="surname" titleKey="admin.member.surname"></display:column>
</display:table>

<p><spring:message code="admin.processionOrganised" /> </p>	
<display:table pagesize="5" name="${processionOrganised}" id="processionOrganised"
	requestURI="${requestURI}">
		<display:column property="title" titleKey="procession.title"></display:column>
	<display:column property="moment" titleKey="procession.moment"></display:column>
	<display:column property="description" titleKey="procession.description"></display:column>
</display:table>

</security:authorize>
<br>
<br>
<acme:cancel url=" " code="back"/> 