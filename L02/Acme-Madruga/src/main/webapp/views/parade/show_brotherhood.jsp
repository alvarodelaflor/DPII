<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<body>
	<security:authorize access="hasRole('MEMBER')">
		<jstl:if test="${validMember}">
			<form method="get" action="/Acme-Madruga/request/member/create.do">
				<button name="paradeId" value="${parade.id}">
					<spring:message code="createRequest" />
				</button>
			</form>
		</jstl:if>
	</security:authorize>
	<div></div>
	<div>
		<table>
			<tr>
				<td><strong><spring:message code="parade.title" />: </strong>
					<jstl:out value="${parade.title}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.description" />: </strong>
					<jstl:out value="${parade.description}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.moment" />: </strong>
					<jstl:out value="${parade.moment}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.brotherhood" />: </strong>
					<jstl:out
						value="${parade.brotherhood.name} ${parade.brotherhood.surname}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.ticker" />: </strong>
					<jstl:out value="${parade.ticker}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.maxRow" />: </strong>
					<jstl:out value="${parade.maxRow}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.maxColum" />: </strong>
					<jstl:out value="${parade.maxColum}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.float" />: </strong>
					<jstl:out value="${parade.floatt.title}"></jstl:out></td>
			</tr>
			<tr>
				<td><strong><spring:message code="parade.status" />: </strong><span
					class="parade-${fn:toLowerCase(parade.status)}"><jstl:out
							value="${parade.status}"></jstl:out></span></td>
			</tr>

		</table>
	</div>
	<div>
		<form>
			<input type="button" value=<spring:message code="back" /> name="back"
				onclick="history.back()" />
		</form>
	</div>
</body>