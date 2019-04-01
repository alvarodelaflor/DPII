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

<security:authorize access="hasRole('HACKER')">
	<style>
		.linea
		{
		    float: left;
		}
	</style>

	<div>
		<jstl:if test="${hackerLogin==true}">
			<form method="get" action="/Acme-Hacker-Rank/positionData/hacker/delete.do">
				<button class="linea" name="positionDataId" value="${positionData.id}"><spring:message code="curricula.delete"/></button>
			</form>
			<form method="get" action="/Acme-Hacker-Rank/positionData/hacker/edit.do">
				<button name="positionDataId" value="${positionData.id}"><spring:message code="curricula.edit"/></button>
			</form>
			<br>	
		</jstl:if>
	</div>
</security:authorize> 
<div class="content">
	<fieldset>
		<legend>
			<spring:message code="curricula.data" />
		</legend>
		<p><strong><spring:message code="positionData.title" /></strong><jstl:out value="${positionData.title}"></jstl:out></p>
		<p><strong><spring:message code="positionData.description" /></strong><jstl:out value="${positionData.description}"></jstl:out></p>
		<p><strong><spring:message code="positionData.startDate" /></strong><jstl:out value="${positionData.startDate}"></jstl:out></p>
		<p><strong><spring:message code="positionData.endDate" /></strong><jstl:out value="${positionData.endDate}"></jstl:out></p>
	</fieldset>
	<fieldset>
		<legend>
			<spring:message code="positionData.position" />
		</legend>
		<p><strong><spring:message code="position.ticker" /></strong> <jstl:out value="${positionData.position.ticker}"></jstl:out></p>
		<p><strong><spring:message code="position.title" /></strong> <jstl:out value="${positionData.position.title}"></jstl:out></p>
		<form method="get" action="/Acme-Hacker-Rank/position/show.do">
			<button name="id" value="${positionData.position.id}"><spring:message code="positionData.goLink"/></button>
		</form>
	</fieldset>
</div>

<br>
<acme:cancel url="curricula/show.do?curriculaId=${positionData.curricula.id}" code="back"/>
