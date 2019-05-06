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


<c:choose>
	<c:when test="${not empty positions}">
		<form method="get" action="audit/auditor/create.do">
			<button>
				<spring:message code="createAudit" />
			</button>
		</form>
		<br>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${auditorLogger==true}">
				<spring:message code="audit.emptyPositions" />
				<br>
				<br>			
			</c:when>
			<c:otherwise>

			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<jstl:if test="${auditorLogger==true}">
	<strong><spring:message code="audit.finalModeList" /></strong>
</jstl:if>
<br>
<display:table name="finalAudits" id="row01" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column titleKey="curricula.show">
		<a href="audit/show.do?auditId=${row01.id}"><img width="35" height="35" src="./images/show.png" alt="${row01.id}" /></a>	
	</display:column>
	<display:column property="text" titleKey="audit.text"></display:column>
</display:table>
<br>
<br>

<jstl:if test="${auditorLogger==true}">
	
	<strong><spring:message code="audit.draftModeList" /></strong>
	<br>
	
	<display:table name="draftAudits" id="row02" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<jstl:if test="${auditorLogger==true}">
			<display:column titleKey="audit.edit">
				<a href="audit/auditor/edit.do?auditId=${row02.id}"><img width="35" height="35" src="./images/edit.png" alt="${row02.id}" /></a>	
			</display:column>
			<display:column titleKey="audit.delete">
				<a href="audit/auditor/delete.do?auditId=${row02.id}"><img width="35" height="35" src="./images/delete.png" alt="${row02.id}" /></a>	
			</display:column>
		</jstl:if>
		<display:column titleKey="audit.show">
			<a href="audit/show.do?auditId=${row02.id}"><img width="35" height="35" src="./images/show.png" alt="${row02.id}" /></a>	
		</display:column>
		<display:column property="text" titleKey="audit.text"></display:column>
	</display:table>
	<br>
	</jstl:if>
<br>
<acme:cancel url="#" code="back"/>