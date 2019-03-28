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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('SPONSOR')"> 
<div class="content">
	<table>
		<c:choose>
    		<c:when test="${sponsor.photo==''}">
				<tr><td><spring:message code="sponsor.photo" /><br>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="sponsor.photo" /><br>
				<img width="95" src="${sponsor.photo}" alt="ERROR"/></td></tr>
    		</c:otherwise>
		</c:choose>
		<tr><td><spring:message code="sponsor.name" /> <jstl:out	value="${sponsor.name}"></jstl:out></td></tr>
		<tr><td><spring:message code="sponsor.middleName" /> <jstl:out	value="${sponsor.middleName}"></jstl:out></td></tr>
		<tr><td><spring:message code="sponsor.surname" /> <jstl:out value="${sponsor.surname}"></jstl:out></td></tr>
		<tr><td><spring:message code="sponsor.address" /> <jstl:out value="${sponsor.address}"></jstl:out></td></tr>
		<tr><td><spring:message code="sponsor.phone" /> <jstl:out value="${sponsor.phone}"></jstl:out></td></tr>
		<tr><td><spring:message code="sponsor.email" /> <jstl:out value="${sponsor.email}"></jstl:out></td></tr>
		<tr><td><spring:message code="sponsor.username" /> <jstl:out value="${sponsor.userAccount.username}"></jstl:out></td></tr>
	</table>
</div>
</security:authorize>
<acme:cancel url=" " code="cancel"/>
<acme:cancel url="/sponsor/export.do?id=${sponsor.id}" code="export"/><br>
<spring:message code="delete.actor"></spring:message><br>
<acme:cancel url="/sponsor/delete.do?id=${sponsor.id}" code="delete"/>


