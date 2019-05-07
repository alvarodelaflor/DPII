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

<div class="content">

	<div>
		<img alt="" src="${sponsorship.banner}">
	</div>

	<table>
		<tr>
			<td><strong><spring:message code="position.ticker" />:
			</strong> <jstl:out value="${position.ticker}"></jstl:out></td>
		</tr>
		<tr>
			<td><strong><spring:message code="position.deadline" />:
			</strong> <jstl:out value="${position.deadline}"></jstl:out></td>
		</tr>
		<tr>
			<td><strong><spring:message code="position.profile" />:
			</strong> <jstl:out value="${position.profile}"></jstl:out></td>
		</tr>
		<tr>
			<td><strong><spring:message code="position.skills" />:
			</strong> <jstl:out value="${position.skills}"></jstl:out></td>
		</tr>
		<tr>
			<td><strong><spring:message code="position.techs" />:
			</strong> <jstl:out value="${position.techs}"></jstl:out></td>
		</tr>
		<tr>
			<td><strong><spring:message code="position.title" />:
			</strong> <jstl:out value="${position.title}"></jstl:out></td>
		</tr>

		<security:authorize access="hasRole('ROOKIE')">
			<c:choose>
				<c:when test="${hasProblem == true}">
					<input type="button"
						value="<spring:message code='application.position.create' />"
						onclick="window.location = 'application/rookie/create.do?id=${position.id}'" />
					<br>

				</c:when>
			</c:choose>

			<c:choose>
				<c:when test="${res == true}">
					<div>
						<form:form class="formularioEdicion" method="POST"
							modelAttribute="application" action="application/rookie/save.do">
							<form:hidden path="position" />

							<spring:message code='application.curricula' />
							<form:select path="curricula">
								<form:options items="${curriculas}" itemLabel="name"
									itemValue="id" />
							</form:select>

							<br>

							<acme:submit name="save" code="application.save" />
						</form:form>
					</div>

				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${res == false}">

							<spring:message code='application.sorry.create' />

						</c:when>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</security:authorize>
	</table>
	<jstl:if test="${audits != null}">
		<strong><spring:message code='position.showAudit' /></strong>
		<display:table name="audits" id="row01" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<display:column titleKey="curricula.show">
				<form method="get" action="audit/show.do">
					<button class="linea" name="auditId" value="${row01.id}"><spring:message code="audit.show"/></button>
				</form>	
			</display:column>
			<display:column property="auditor.email" titleKey="audit.auditor.emailContact"></display:column>
			<display:column property="score" titleKey="audit.score"></display:column>
		</display:table>
	</jstl:if>
</div>

<input type="button" value="<spring:message code="position.cancel" />" name="position.cancel" onclick="history.back()" />
