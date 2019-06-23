<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.Date,java.util.Calendar"%>

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
		<a href="quolet/company/show.do?quoletId=${row.id}"><spring:message code="quolet.show" /></a>
	</display:column>
</display:table>



<input type="button" value="<spring:message code='problem.back' />" onclick="window.location = 'application/company/list.do'" />

