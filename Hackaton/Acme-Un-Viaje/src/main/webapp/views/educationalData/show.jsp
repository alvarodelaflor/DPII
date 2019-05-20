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

<security:authorize access="hasRole('CLEANER')">
	<style>
		.linea
		{
		    float: left;
		}
	</style>

	<div>
		<jstl:if test="${cleanerLogin==true}">
			<form method="get" action="educationalData/cleaner/delete.do">
				<button class="linea" name="educationalDataId" value="${educationalData.id}"><spring:message code="curricula.delete"/></button>
			</form>
			<form method="get" action="educationalData/cleaner/edit.do">
				<button name="educationalDataId" value="${educationalData.id}"><spring:message code="curricula.edit"/></button>
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
		<p><strong><spring:message code="educationalData.degree" /> </strong><jstl:out value="${educationalData.degree}"></jstl:out></p>
		<p><strong><spring:message code="educationalData.institution" /> </strong><jstl:out value="${educationalData.institution}"></jstl:out></p>
		<p><strong><spring:message code="educationalData.mark" /> </strong><jstl:out value="${educationalData.mark}"></jstl:out></p>
		<p><strong><spring:message code="educationalData.startDate" /> </strong><jstl:out value="${educationalData.startDate}"></jstl:out></p>
		<p><strong><spring:message code="educationalData.endDate" /> </strong><jstl:out value="${educationalData.endDate}"></jstl:out></p>
	</fieldset>
</div>

<br>
<acme:cancel url="curricula/show.do?curriculaId=${educationalData.curricula.id}" code="back"/>
