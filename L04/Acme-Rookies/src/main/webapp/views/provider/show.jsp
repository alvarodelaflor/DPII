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
    		<c:when test="${provider.photo==''}">
				<tr><td><spring:message code="provider.photo" /><br>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="provider.photo" /><br>
				<img width="95" src="${provider.photo}" alt="ERROR"/></td></tr>
    		</c:otherwise>
		</c:choose>
		<tr><td><strong><spring:message code="provider.name" /></strong> <jstl:out	value="${provider.name}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="provider.surname" /></strong> <jstl:out value="${provider.surname}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="provider.address" /></strong> <jstl:out value="${provider.address}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="provider.email" /></strong> <jstl:out value="${provider.email}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="provider.phone" /></strong> <jstl:out value="${provider.phone}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="provider.comercialName" /></strong> <jstl:out value="${provider.commercialName}"></jstl:out></td></tr>
	</table>
</div>
<acme:cancel url=" " code="rookie.cancel"/>
<jstl:if test = "${owner}">
<acme:cancel url="/provider/export.do?id=${provider.id}" code="export"/><br>
<spring:message code="delete.actor"></spring:message><br>
<acme:cancel url="/provider/delete.do?id=${provider.id}" code="delete"/>
</jstl:if>
