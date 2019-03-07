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
	<security:authorize access="hasRole('MEMBER')">
		<c:choose>
			<c:when test="${validMember==true}">
				<form method="get" action="/Acme-Madruga/enroll/member/create.do">
					<button name="brotherhoodId" value="${brotherhood.id}"><spring:message code="createEnrolled"/></button>
				</form>				
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${activeMember==true}">
						<form method="get" action="/Acme-Madruga/brotherhood/member/drop.do">
							<button name="brotherhoodId" value="${brotherhood.id}"><spring:message code="dropEnrolled"/></button>
						</form>				
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${checkAreaNull==true}">
								<spring:message code="area.null"/>
							</c:when>
							<c:otherwise>
								<spring:message code="enrolled.alreadySend"/>
							</c:otherwise>						
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</security:authorize>
	<table>
		<tr><td><spring:message code="brotherhood.photo" /><br>
		<img width="95" src="${brotherhood.photo}" alt=<jstl:out value="${brotherhood.photo}"></jstl:out> /></td></tr>
		<tr><td><spring:message code="brotherhood.name" /> <jstl:out value="${brotherhood.name}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.middleName" /> <jstl:out value="${brotherhood.middleName}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.surname" /> <jstl:out value="${brotherhood.surname}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.address" /> <jstl:out value="${brotherhood.address}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.phone" /> <jstl:out value="${brotherhood.phone}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.email" /> <jstl:out value="${brotherhood.email}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.username" /> <jstl:out value="${brotherhood.userAccount.username}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.title" /> <jstl:out value="${brotherhood.title}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.establishmentDate" /> <jstl:out value="${brotherhood.establishmentDate}"></jstl:out></td></tr>
	</table>
</div>

<br>

<p>
<display:table pagesize="5" name="${pictures}" id="pictures"
	requestURI="${requestURI}">
	<display:column titleKey="brotherhood.pictures">
		<img width="300" src="${picture.trim()}" alt="Error" >
	</display:column>
</display:table>

<br>	
<br>
<acme:cancel url="brotherhood/list.do" code="back"/>


