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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="content">
	<table>
		<tr><td><spring:message code="chapter.name" /> <jstl:out value="${chapter.name}"></jstl:out></td></tr>
		<tr><td><spring:message code="chapter.middleName" /> <jstl:out value="${chapter.middleName}"></jstl:out></td></tr>
		<tr><td><spring:message code="chapter.surname" /> <jstl:out value="${chapter.surname}"></jstl:out></td></tr>
		<tr><td><spring:message code="chapter.address" /> <jstl:out value="${chapter.address}"></jstl:out></td></tr>
		<tr><td><spring:message code="chapter.phone" /> <jstl:out value="${chapter.phone}"></jstl:out></td></tr>
		<tr><td><spring:message code="chapter.email" /> <jstl:out value="${chapter.email}"></jstl:out></td></tr>
		<tr><td><spring:message code="chapter.username" /> <jstl:out value="${chapter.userAccount.username}"></jstl:out></td></tr>
		<tr><td><spring:message code="chapter.title" /> <jstl:out value="${chapter.title}"></jstl:out></td></tr>
	</table>
	
	<h3><spring:message code="chapter.proclaims"/></h3>
<display:table name="proclaims" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="text" titleKey="proclaim.text"></display:column>
	<display:column property="moment" titleKey="proclaim.moment"></display:column>
</display:table>
<!-- ALVARO 15/03/2019 -->
<jstl:if test="${validChapter==true}">
	<form method="get" action="/Acme-Madruga/proclaim/chapter/create.do">
		<button><spring:message code="createProclaim"/></button>
	</form>
</jstl:if>

</div>

<br>

<acme:cancel url="chapter/list.do" code="back"/>
<jstl:if test="${checkChapter}">
<acme:cancel url="/chapter/export.do?id=${chapter.id}" code="export"/><br>
<spring:message code="delete.actor"></spring:message><br>
<acme:cancel url="/chapter/delete.do?id=${chapter.id}" code="delete"/>
</jstl:if>


