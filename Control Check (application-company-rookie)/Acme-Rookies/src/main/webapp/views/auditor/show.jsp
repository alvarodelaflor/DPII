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

<div class="content">
	<table>
		<c:choose>
    		<c:when test="${auditor.photo==''}">
				<tr><td><spring:message code="auditor.photo" /><br>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="auditor.photo" /><br>
				<img width="95" src="${auditor.photo}" alt="ERROR"/></td></tr>
    		</c:otherwise>
		</c:choose>
		<tr><td><strong><spring:message code="auditor.name" /></strong> <jstl:out	value="${auditor.name}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="auditor.surname" /></strong> <jstl:out value="${auditor.surname}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="auditor.address" /></strong> <jstl:out value="${auditor.address}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="auditor.email" /></strong> <jstl:out value="${auditor.email}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="auditor.phone" /></strong> <jstl:out value="${auditor.phone}"></jstl:out></td></tr>
	</table>
</div>
<acme:cancel url=" " code="rookie.cancel"/>
<acme:cancel url="/auditor/export.do?id=${auditor.id}" code="export"/><br>
<spring:message code="delete.actor"></spring:message><br>
<acme:cancel url="/auditor/delete.do?id=${auditor.id}" code="delete"/>

