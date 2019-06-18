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

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
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

<div class="content">
	<table>
		<tr>
			<td><strong><spring:message code="xxxx.ticker" />: </strong> <jstl:out value="${xxxx.ticker}"></jstl:out></td>
		</tr>
		<tr>
			<td><strong><spring:message code="xxxx.publicationMoment" />: </strong> <c:choose>
					<c:when test="${xxxx.publicationMoment gt oneMonth}">
						<span style="color: Indigo">
							<spring:message code='xxxx.dateFormat' var="dateFormat" />
							<acme:customDate date="${xxxx.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:when>
					<c:when test="${xxxx.publicationMoment gt twoMonths}">
						<span style="color: DarkSlateGrey">
							<spring:message code='xxxx.dateFormat' var="dateFormat" />
							<acme:customDate date="${xxxx.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:when>
					<c:otherwise>
						<span style="color: PapayaWhip">
							<spring:message code='xxxx.dateFormat' var="dateFormat" />
							<acme:customDate date="${xxxx.publicationMoment}" pattern="${dateFormat}" />
						</span>
					</c:otherwise>
				</c:choose></td>
		</tr>
		<tr>
			<td><strong><spring:message code="xxxx.body" />: </strong> <jstl:out value="${xxxx.body}"></jstl:out></td>
		</tr>
		<tr>
			<td><strong><spring:message code="xxxx.picture" />: </strong> <img style="width: 200px;" alt="Picture" src="${xxxx.picture}"></img></td>
		</tr>
		<tr>
			<td><strong><spring:message code="xxxx.draftMode" />: </strong> <spring:message code="xxxx.draftMode.${xxxx.draftMode}" /></td>
		</tr>
		<tr>
			<td><strong><spring:message code="xxxx.problem" />: </strong> <a href="problem/company/show.do?problemId=${xxxx.problem.id}"><spring:message code="problem.show" /></a></td>
		</tr>
	</table>
</div>
<input type="button" value="back" name="problem.back" onclick="history.back()" />
