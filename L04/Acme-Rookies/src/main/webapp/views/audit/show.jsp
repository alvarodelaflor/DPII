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

<security:authorize access="hasRole('AUDITOR')">
	<style>
		.linea
		{
		    float: left;
		}
	</style>

	<div>
		<jstl:if test="${auditLogin==true}">
			<form method="get" action="audit/auditor/delete.do">
				<button class="linea" name="auditId" value="${audit.id}"><spring:message code="curricula.delete"/></button>
			</form>
			<form method="get" action="audit/auditor/edit.do">
				<button name="auditId" value="${audit.id}"><spring:message code="curricula.edit"/></button>
			</form>
			<br>	
		</jstl:if>
	</div>
</security:authorize> 
<div class="content">
	<fieldset>
		<legend>
			<spring:message code="audit.data" />
		</legend>
		<c:choose>
    		<c:when test="${audit.auditor.photo=='' or audit.auditor.photo==null}">
				<img width="95" src="./images/rookie.png" alt="ERROR"/>
    		</c:when>    
    		<c:otherwise>
				<img width="95" src="${audit.auditor.photo}" alt="ERROR"/>
    		</c:otherwise>
		</c:choose>
		<p><strong><spring:message code="audit.text" /> </strong><jstl:out value="${audit.text}"></jstl:out></p>
		<p><strong><spring:message code="audit.creationMoment" /> </strong><jstl:out value="${audit.creationMoment}"></jstl:out></p>
		<p><strong><spring:message code="audit.score" /> </strong><jstl:out value="${audit.score}"></jstl:out></p>
		<p><strong><spring:message code="audit.status" /></strong><jstl:out value="${audit.status}"></jstl:out></p>
		<fieldset>
			<legend>
				<i><spring:message code="audit.auditor.data" /></i><img width="35" height="35" src="./images/phone.png" alt="${row1.id}" />	
			</legend>
			<p><strong><spring:message code="audit.auditor.name" /> </strong><jstl:out value="${audit.auditor.name}">></jstl:out> <jstl:out value="${audit.auditor.surname}"></jstl:out></p>
			<p><strong><spring:message code="audit.auditor.email" /> </strong><jstl:out value="${audit.auditor.email}">></jstl:out></p>
			<p><strong><spring:message code="audit.auditor.phone" /> </strong><jstl:out value="${audit.auditor.phone}">></jstl:out></p>
		</fieldset>
		<fieldset>
			<legend>
				<i><spring:message code="audit.position.data" /></i>	
			</legend>
			<p><strong><spring:message code="audit.position.ticker" /> </strong><jstl:out value="${audit.position.ticker}">></jstl:out></p>
			<p><strong><spring:message code="audit.position.company" /> </strong><jstl:out value="${audit.position.company.commercialName}">></jstl:out></p>
			<form method="get" action="position/show.do">
				<button name="id" value="${audit.position.id}"><spring:message code="positionData.goLink"/></button>
			</form>
		</fieldset>
	</fieldset>
</div>

<br>

<acme:cancel url="audit/list.do?auditorId=${audit.auditor.id}" code="back"/>  			
    			