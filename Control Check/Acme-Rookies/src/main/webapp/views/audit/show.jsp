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
<%@ page import="java.util.Date,java.util.Calendar"%>

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
		<p><strong><spring:message code="audit.text" /> </strong><jstl:out value="${audit.text}"></jstl:out></p>
		<p><strong><spring:message code="audit.creationMoment" /> </strong><jstl:out value="${audit.creationMoment}"></jstl:out></p>
		<p><strong><spring:message code="audit.score" /> </strong><jstl:out value="${audit.score}"></jstl:out></p>
		<p><strong><spring:message code="audit.status" /></strong><spring:message code="audit.${audit.status}" /></p>
		<fieldset>
			<legend>
				<i><spring:message code="audit.auditor.data" /></i><img width="35" height="35" src="./images/phone.png" alt="${row1.id}" />	
			</legend>
			<c:choose>
	    		<c:when test="${audit.auditor.photo=='' or audit.auditor.photo==null}">
					<img width="95" src="./images/rookie.png" alt=" "/>
	    		</c:when>    
	    		<c:otherwise>
					<img width="95" src="${audit.auditor.photo}" alt=" "/>
	    		</c:otherwise>
			</c:choose>
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

<h3><spring:message code='quolets'/></h3>
<c:choose>
	<c:when test="${companyOwner}">
		<display:table name="quolets" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<display:column titleKey="quolet.ticker" property="ticker" />
			<display:column titleKey="quolet.publicationMoment">
				<c:choose>
					<c:when test="${row.publicationMoment gt oneMonth}">
						<span style="color: Indigo">
							<spring:message code='quolet.dateFormat' var="dateFormat" />
							<acme:customDate date="${row.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:when>
					<c:when test="${row.publicationMoment gt twoMonths}">
						<span style="color: DarkSlateGrey">
							<spring:message code='quolet.dateFormat' var="dateFormat" />
							<acme:customDate date="${row.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:when>
					<c:otherwise>
						<span style="color: PapayaWhip">
							<spring:message code='quolet.dateFormat' var="dateFormat" />
							<acme:customDate date="${row.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="quolet.body" property="body" />
			<display:column titleKey="quolet.draftMode">
				<spring:message code="quolet.draftMode.${row.draftMode}" />
			</display:column>
			<display:column titleKey="quolet.show">
				<a href="quolet/company/show.do?quoletId=${row.id}"><spring:message code="quolet.show" /></a>
			</display:column>
			<display:column titleKey="quolet.edit">
				<jstl:if test="${row.draftMode}">
					<a href="quolet/company/edit.do?quoletId=${row.id}"><spring:message code="quolet.edit" /></a>
				</jstl:if>
			</display:column>
			<display:column titleKey="quolet.delete">
				<jstl:if test="${row.draftMode}">
					<a href="quolet/company/delete.do?quoletId=${row.id}"><spring:message code="quolet.delete" /></a>
				</jstl:if>
			</display:column>
		</display:table>
	</c:when>
	<c:otherwise>
		<%
			Calendar oneMonth = Calendar.getInstance();
			oneMonth.add(Calendar.MONTH, -1);
			Calendar twoMonths = Calendar.getInstance();
			twoMonths.add(Calendar.MONTH, -2);
		%>
		<c:set var="oneMonth" value="<%=oneMonth.getTime()%>" />
		<c:set var="twoMonths" value="<%=twoMonths.getTime()%>" />
		
		
		<display:table name="quolets" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<display:column titleKey="quolet.ticker" property="ticker" />
			<display:column titleKey="quolet.publicationMoment">
				<c:choose>
					<c:when test="${row.publicationMoment gt oneMonth}">
						<span style="color: Indigo">
							<spring:message code='quolet.dateFormat' var="dateFormat" />
							<acme:customDate date="${row.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:when>
					<c:when test="${row.publicationMoment gt twoMonths}">
						<span style="color: DarkSlateGrey">
							<spring:message code='quolet.dateFormat' var="dateFormat" />
							<acme:customDate date="${row.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:when>
					<c:otherwise>
						<span style="color: PapayaWhip">
							<spring:message code='quolet.dateFormat' var="dateFormat" />
							<acme:customDate date="${row.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="quolet.body" property="body" />
		
			<display:column titleKey="quolet.show">
				<c:choose>
					<c:when test="${isCompany}">
						<a href="quolet/company/show.do?quoletId=${row.id}"><spring:message code="quolet.show" /></a>
					</c:when>
					<c:otherwise>
						<a href="quolet/auditor/show.do?quoletId=${row.id}"><spring:message code="quolet.show" /></a>
					</c:otherwise>
				</c:choose>
			</display:column>
		</display:table>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${noHistoryBack==true}">
		<acme:cancel url="audit/list.do?auditorId=${audit.auditor.id}" code="back"/>
	</c:when>
	<c:otherwise>
		<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
	</c:otherwise>
</c:choose> 				