<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ROOKIE')">
	<div class="content">

		<table>
			<tr>
				<td><strong><spring:message
							code="application.creationMoment" /></strong>: <jstl:out
						value="${application.creationMoment}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message
							code="application.applyMoment" /></strong>: <jstl:out
						value="${application.applyMoment}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message
							code="application.response" /></strong>: <jstl:out
						value="${application.response}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="application.link" /></strong>:
					<jstl:out value="${application.link}"></jstl:out></td>
			</tr>

			<c:choose>
				<c:when test="${application.status == 'SUBMITTED'}">
					<tr>
						<td><strong><spring:message code="application.status" /></strong>:
							<spring:message code="status.SUBMITTED"/></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${application.status == 'PENDING'}">
					<tr>
						<td><strong><spring:message code="application.status" /></strong>:
							<spring:message code="status.PENDING"/></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${application.status == 'ACCEPTED'}">
					<tr>
						<td><strong><spring:message code="application.status" /></strong>:
							<spring:message code="status.ACCEPTED"/></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${application.status == 'REJECTED'}">
					<tr>
						<td><strong><spring:message code="application.status" /></strong>:
							<spring:message code="status.REJECTED"/></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
</security:authorize>
<input type="button" value="<spring:message code="back" />" name="back"
	onclick="history.back()" />
